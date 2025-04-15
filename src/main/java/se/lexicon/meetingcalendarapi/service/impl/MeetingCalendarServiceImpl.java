package se.lexicon.meetingcalendarapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.meetingcalendarapi.MeetingCalendarApiApplication;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOView;
import se.lexicon.meetingcalendarapi.domain.dto.ParticipantDTOForm;
import se.lexicon.meetingcalendarapi.domain.entity.MeetingCalendar;
import se.lexicon.meetingcalendarapi.domain.entity.Participant;
import se.lexicon.meetingcalendarapi.repository.MeetingCalendarRepository;
import se.lexicon.meetingcalendarapi.repository.PartcipantRepository;
import se.lexicon.meetingcalendarapi.service.MeetingCalendarService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeetingCalendarServiceImpl implements MeetingCalendarService {

    private final MeetingCalendarRepository meetingCalendarRepository;
    private final PartcipantRepository partcipantRepository;

    @Autowired
    public MeetingCalendarServiceImpl(MeetingCalendarRepository meetingCalendarRepository, PartcipantRepository partcipantRepository) {
        this.meetingCalendarRepository = meetingCalendarRepository;
        this.partcipantRepository = partcipantRepository;
    }


    @Override
    public List<MeetingCalendarDTOView> getAllMeetings() {
      List<MeetingCalendar> meetings=meetingCalendarRepository.findAll();
      return  meetings.stream()
              .map(this::convertToMeetingDTOView)
              .collect(Collectors.toList());

    }

    @Override
    public MeetingCalendarDTOView createMeeting(MeetingCalendarDTOForm meetingForm) {
        System.out.println("meeting form>>>>>>>>>>>>>>>>>>>>>>>"+meetingForm);
          Set<Participant> participants=meetingForm.participants().stream()
                .map((participantDTO)->
                        new Participant(participantDTO.email())).collect(Collectors.toUnmodifiableSet());
         partcipantRepository.saveAll(participants);
       MeetingCalendar meeting = MeetingCalendar.builder().meetingTitle(meetingForm.meetingTitle())
               .meetingDate(meetingForm.meetingDate()).meetingTime(meetingForm.meetingTime())
               .meetingLevel(meetingForm.meetingLevel())
               .meetingDescription(meetingForm.meetingDescription()).participants(participants).build();

       meeting= meetingCalendarRepository.save(meeting);
        return convertToMeetingDTOView(meeting);
    }

    @Override
    public void updateMeeting(MeetingCalendarDTOForm meetingForm) {
       MeetingCalendar meetingCalendar=meetingCalendarRepository.findById(meetingForm.meetingId()).orElseThrow(()-> new RuntimeException("Meeting not found"));

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

    @Override
    public MeetingCalendarDTOView getMeetingById(Integer meetingId) {
      MeetingCalendar meetingCalendar =  meetingCalendarRepository.findById(meetingId).orElseThrow(()-> new RuntimeException("Meeting not found"));
      return convertToMeetingDTOView(meetingCalendar);
    }

    @Override
    public void deleteMeeting(Integer meetingId) {
        MeetingCalendar meetingCalendar =  meetingCalendarRepository.findById(meetingId).orElseThrow(()-> new RuntimeException("Meeting not found"));
        meetingCalendarRepository.delete(meetingCalendar);
    }

    private MeetingCalendarDTOView convertToMeetingDTOView(MeetingCalendar meeting) {

        return MeetingCalendarDTOView.builder().meetingId(meeting.getMeetingId()).meetingTitle(meeting.getMeetingTitle())
                .meetingDate(meeting.getMeetingDate()).meetingTime(meeting.getMeetingTime())
                .meetingLevel(meeting.getMeetingLevel()).meetingDescription(meeting.getMeetingDescription())
                .participants(meeting.getParticipants())
                .build();
    }
}
