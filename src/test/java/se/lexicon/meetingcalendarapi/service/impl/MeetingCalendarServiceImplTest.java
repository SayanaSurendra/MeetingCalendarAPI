package se.lexicon.meetingcalendarapi.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOView;
import se.lexicon.meetingcalendarapi.domain.dto.ParticipantDTOForm;
import se.lexicon.meetingcalendarapi.domain.entity.MeetingCalendar;
import se.lexicon.meetingcalendarapi.domain.entity.Participant;
import se.lexicon.meetingcalendarapi.repository.MeetingCalendarRepository;
import se.lexicon.meetingcalendarapi.repository.PartcipantRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeetingCalendarServiceImplTest {

    @Mock
    private MeetingCalendarRepository meetingCalendarRepository;

    @Mock
    private PartcipantRepository partcipantRepository;

    @InjectMocks
    private MeetingCalendarServiceImpl meetingCalendarService;


    MeetingCalendar meetingCalendar;
    Set<Participant> participants = new HashSet<>();

    @BeforeEach
    void setUp() {
        Participant participant1=new Participant("test@gmail.com");
        Participant participant2=new Participant("testuser@gmail.com");
        participants.add(participant1);
        participants.add(participant2);
        partcipantRepository.saveAll(participants);

       meetingCalendar=MeetingCalendar.builder().meetingId(1).
               meetingTitle("Java Meeting").meetingDescription("Meeting to discuss about currnet uissues")
               .meetingDate(LocalDate.of(2025,04,25)).meetingLevel("Team")
        .meetingTime(LocalTime.of(10, 30)).participants(participants).build();
    }


    @Test
    void getAllMeetings() {

        when(meetingCalendarRepository.findAll()).thenReturn(List.of(meetingCalendar));

        List<MeetingCalendarDTOView> meetingCalendarDTOViewList=meetingCalendarService.getAllMeetings();
        assertNotNull(meetingCalendarDTOViewList);
        assertEquals(1, meetingCalendarDTOViewList.size());
        assertEquals(meetingCalendar.getMeetingTitle(), meetingCalendarDTOViewList.get(0).meetingTitle());
    }



    @Test
    void updateMeeting() {
        ParticipantDTOForm existingParticipantDTOForm=new ParticipantDTOForm("existigUser@gmail.com");
        ParticipantDTOForm newParticipantDTOForm=new ParticipantDTOForm("newUser@gmail.com");
        MeetingCalendarDTOForm meetingCalendarDTOForm=new MeetingCalendarDTOForm(1,"Java Meeting","Meeting to discuss about currnet uissues",
                "Team",LocalDate.of(2025,04,25),(LocalTime.of(10, 30)),Set.of(existingParticipantDTOForm,newParticipantDTOForm));

        Participant existingParticipant = new Participant("existigUser@gmail.com");
        Participant newParticipant = new Participant("newUser@gmail.com");
        when(meetingCalendarRepository.findById(1)).thenReturn(Optional.of(meetingCalendar));
        when(partcipantRepository.findById("existigUser@gmail.com")).thenReturn(Optional.of(existingParticipant));
        when(partcipantRepository.findById("newUser@gmail.com")).thenReturn(Optional.empty());
        meetingCalendarService.updateMeeting(meetingCalendarDTOForm);

        assertEquals(2,meetingCalendar.getParticipants().size());


    }




    @Test
    void getMeetingById() {
        when(meetingCalendarRepository.findById(1)).thenReturn(Optional.of(meetingCalendar));
        MeetingCalendarDTOView meetingCalendarDTOView=meetingCalendarService.getMeetingById(1);
        assertNotNull(meetingCalendarDTOView);
        assertEquals(meetingCalendar.getMeetingTitle(), meetingCalendarDTOView.meetingTitle());
        assertEquals(meetingCalendar.getMeetingDescription(), meetingCalendarDTOView.meetingDescription());
        assertEquals(meetingCalendar.getMeetingDate(), meetingCalendarDTOView.meetingDate());


    }

    @Test
    void deleteMeeting() {
        when(meetingCalendarRepository.findById(1)).thenReturn(Optional.of(meetingCalendar));
        meetingCalendarService.deleteMeeting(1);
        verify(meetingCalendarRepository,times(1)).deleteById(1);
    }
}