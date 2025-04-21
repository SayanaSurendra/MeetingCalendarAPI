package se.lexicon.meetingcalendarapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOView;
import se.lexicon.meetingcalendarapi.domain.dto.ParticipantDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.ParticipantDTOView;
import se.lexicon.meetingcalendarapi.domain.entity.MeetingCalendar;
import se.lexicon.meetingcalendarapi.domain.entity.Participant;
import se.lexicon.meetingcalendarapi.exception.DataNotFoundException;
import se.lexicon.meetingcalendarapi.repository.MeetingCalendarRepository;
import se.lexicon.meetingcalendarapi.repository.PartcipantRepository;
import se.lexicon.meetingcalendarapi.service.MeetingCalendarService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeetingCalendarServiceImpl implements MeetingCalendarService {

    private final MeetingCalendarRepository meetingCalendarRepository;
    private final PartcipantRepository partcipantRepository;

    @Autowired
    public MeetingCalendarServiceImpl(MeetingCalendarRepository meetingCalendarRepository, PartcipantRepository partcipantRepository) {
        this.meetingCalendarRepository = meetingCalendarRepository;
        this.partcipantRepository = partcipantRepository;
    }


    /**
     * Retrieves a list of all meeting calendar.
     * @return a list of MeetingCalendarDTOView objects representing all meeting calendar entries
     */

    @Override
    public List<MeetingCalendarDTOView> getAllMeetings() {
      List<MeetingCalendar> meetings=meetingCalendarRepository.findAll();
      return  meetings.stream()
              .map(this::convertToMeetingDTOView)
              .collect(Collectors.toList());

    }



    /**
     * Creates a new meeting
     *
     * This method creates a new meeting calendar.
     * It checks if the participants already exist in the database
     * and creates new participants for any emails that are not already present.
     * The meeting is then created and saved, with all participants (both existing and new) associated with the meeting.
     *
     *
     * @param meetingForm contains meeting detils
     * @return MeetingCalendarDTOView object which contains a new meetingcalendar with the participants.
     *
     */

    @Override
    public MeetingCalendarDTOView createMeeting(MeetingCalendarDTOForm meetingForm) {
        System.out.println("meeting form>>>>>>>>>>>>>>>>>>>>>>>"+meetingForm);
         /* Set<Participant> participants=meetingForm.participants().stream()
                .map((participantDTO)->
                        new Participant(participantDTO.email())).collect(Collectors.toUnmodifiableSet());*/


        Set<String> emails = meetingForm.participants().stream()
                .map(participantDTO -> participantDTO.email())
                .collect(Collectors.toSet());


        List<Participant> existingParticipants = partcipantRepository.findAllByEmailIn(emails);
        Map<String, Participant> existingMap = existingParticipants.stream()
                .collect(Collectors.toMap(p -> p.getEmail(), p -> p));


        Set<String> newEmails = emails.stream()
                .filter(email -> !existingMap.containsKey(email))
                .collect(Collectors.toSet());
        Set<Participant> newParticipants = newEmails.stream()
                .map(email -> new Participant(email))
                .collect(Collectors.toSet());


        partcipantRepository.saveAll(newParticipants);


        Set<Participant> allParticipants = Stream.concat(
                existingParticipants.stream(),
                newParticipants.stream()
        ).collect(Collectors.toSet());

       MeetingCalendar meeting = MeetingCalendar.builder().meetingTitle(meetingForm.meetingTitle())
               .meetingDate(meetingForm.meetingDate()).meetingTime(meetingForm.meetingTime())
               .meetingLevel(meetingForm.meetingLevel())
               .meetingDescription(meetingForm.meetingDescription()).participants(allParticipants).build();

       meeting= meetingCalendarRepository.save(meeting);
        return convertToMeetingDTOView(meeting);
    }



    /**
     * Updates an existing meeting with the given provided details.
     *
     * The method retrieves the meeting by its unique ID, updates the meeting details
     * and checks the list of participants by checking if each participant already exists.
     * If a participant doesn't exist,
     * it  is  created and added to the meeting.
     *
     *
     * @param meetingForm contains updated meeting details.
     * @throws DataNotFoundException if the meeting ID not exist.
     */
    @Override
    public void updateMeeting(MeetingCalendarDTOForm meetingForm) {
       MeetingCalendar meetingCalendar=meetingCalendarRepository.findById(meetingForm.meetingId()).orElseThrow(()-> new DataNotFoundException("Meeting not found"));

        Set<Participant> participants=new HashSet<>();

        for(ParticipantDTOForm participantDTOForm:meetingForm.participants()) {
           Optional<Participant> existingParticipant= partcipantRepository.findById(participantDTOForm.email());
            if(!existingParticipant.isPresent()){
                Participant newPartcipant=partcipantRepository.save(new Participant(participantDTOForm.email()));
                participants.add(newPartcipant);
            }else{
                participants.add(existingParticipant.get());
            }

        }
        meetingCalendar.setMeetingTitle(meetingForm.meetingTitle());
        meetingCalendar.setMeetingDate(meetingForm.meetingDate());
        meetingCalendar.setMeetingTime(meetingForm.meetingTime());
        meetingCalendar.setMeetingLevel(meetingForm.meetingLevel());
        meetingCalendar.setParticipants(participants);
        meetingCalendar.setMeetingDescription(meetingForm.meetingDescription());

        meetingCalendarRepository.save(meetingCalendar);


    }


    /**
     * Retrieves a meeting calendar by its ID.
     *
     * This method retrieves meeting details using the provided meeting ID. If the meeting exists,
     * it returnsmeetingCalendar dto containing the details of the meeting. If the meeting is not found,
     * an exception thrown that the requested meeting does not exist.
     *
     * @param meetingId  Id of the meeting to retrieve.
     * @return A  MeetingCalendarDTOVie object containing the details of the requested meeting.
     * @throws DataNotFoundException if no meeting with the given ID is found.
     */

    @Override
    public MeetingCalendarDTOView getMeetingById(Integer meetingId) {
      MeetingCalendar meetingCalendar =  meetingCalendarRepository.findById(meetingId).orElseThrow(()-> new DataNotFoundException("Meeting not found"));
      return convertToMeetingDTOView(meetingCalendar);
    }


    /**
     * Deletes a meetingfor given ID.
     *
     * This method find the meeting by its ID. If the meeting is found,
     * it is deleted from the database. If the meeting does not exist, an exception is thrown.
     *
     *
     * @param meetingId meeting id to be deleted.
     * @throws DataNotFoundException If no meeting with the given ID is found.
     */

    @Override
    public void deleteMeeting(Integer meetingId) {
         meetingCalendarRepository.findById(meetingId).orElseThrow(()-> new DataNotFoundException("Meeting not found"));
        meetingCalendarRepository.deleteById(meetingId);
    }



    /**
     * Converts MeetingCalendar entity to a {@link MeetingCalendarDTOView} data transfer object.
     *
     *
     * @param meeting  MeetingCalendar  entity to be converted.
     * @return  MeetingCalendarDTOView object containing the details of the meeting.
     */

    private MeetingCalendarDTOView convertToMeetingDTOView(MeetingCalendar meeting) {
        Set<ParticipantDTOView> participantDTOViews = meeting.getParticipants().stream()
                .map(participant -> new ParticipantDTOView(
                        participant.getEmail(),
                        null
                ))
                .collect(Collectors.toSet());
        return MeetingCalendarDTOView.builder().meetingId(meeting.getMeetingId()).meetingTitle(meeting.getMeetingTitle())
                .meetingDate(meeting.getMeetingDate()).meetingTime(meeting.getMeetingTime())
                .meetingLevel(meeting.getMeetingLevel()).meetingDescription(meeting.getMeetingDescription())
                .participants(participantDTOViews)
                .build();
    }
}
