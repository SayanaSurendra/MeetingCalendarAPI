package se.lexicon.meetingcalendarapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOView;
import se.lexicon.meetingcalendarapi.domain.dto.ParticipantDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.ParticipantDTOView;
import se.lexicon.meetingcalendarapi.service.MeetingCalendarService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = MeetingCalendarController.class)
@ExtendWith(MockitoExtension.class)
class MeetingCalendarControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    Set<ParticipantDTOView> participantDTOViews = new HashSet<>();
    @MockitoBean
    private MeetingCalendarService meetingCalendarService;
    private MeetingCalendarDTOView meetingCalendarDTOView;
    private ParticipantDTOForm participantDTOForm;
    private MeetingCalendarDTOForm meetingCalendarDTOForm;

    @BeforeEach
    void setUp() {
        ParticipantDTOForm newParticipantDTOForm = new ParticipantDTOForm("newUser@gmail.com");
        meetingCalendarDTOForm = new MeetingCalendarDTOForm(null, "Java Meeting", "Meeting to discuss about currnet issues",
                "Team", LocalDate.of(2025, 04, 25), (LocalTime.of(10, 30)), Set.of(newParticipantDTOForm));
        ParticipantDTOView participantDTOView = ParticipantDTOView.builder().email("newUser@gmail.com").build();
        participantDTOViews.add(participantDTOView);
        meetingCalendarDTOView = MeetingCalendarDTOView.builder().meetingId(1).meetingTitle("Java Meeting").meetingDescription("Meeting to discuss about currnet issues").meetingLevel("Team").
                meetingDate(LocalDate.of(2025, 04, 25)).meetingTime(LocalTime.of(10, 30)).participants(participantDTOViews).build();
    }

    @Test
    void doGetAllMeetings() throws Exception {
        when(meetingCalendarService.getAllMeetings()).thenReturn(List.of(meetingCalendarDTOView));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/meetingCalendar"))
                .andDo(print());
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)));
    }

    @Test
    void doGetByIdMeeting() throws Exception {
        when(meetingCalendarService.getMeetingById(1)).thenReturn(meetingCalendarDTOView);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/meetingCalendar/{id}", 1))
                .andDo(print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.meetingTitle").value(meetingCalendarDTOView.meetingTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meetingDescription").value(meetingCalendarDTOView.meetingDescription()));

    }

    @Test
    void doCreateMeeting() throws Exception {
        when(meetingCalendarService.createMeeting(meetingCalendarDTOForm)).thenReturn(meetingCalendarDTOView);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/meetingCalendar")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(meetingCalendarDTOForm))).andDo(print());

        response.andExpect(MockMvcResultMatchers.status().isCreated());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.meetingTitle").value(meetingCalendarDTOView.meetingTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meetingDescription").value(meetingCalendarDTOView.meetingDescription()));

    }

    @Test
    void doUpdateMeeting() throws Exception {
        ParticipantDTOForm newParticipantDTOForm = new ParticipantDTOForm("newUser@gmail.com");
        meetingCalendarDTOForm = new MeetingCalendarDTOForm(1, "Java Meeting", "Meeting to discuss about currnet issues",
                "Team", LocalDate.of(2025, 04, 25), (LocalTime.of(10, 30)), Set.of(newParticipantDTOForm));
        doNothing().when(meetingCalendarService).updateMeeting(meetingCalendarDTOForm);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/meetingCalendar")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(meetingCalendarDTOForm)));
        response.andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void doDeleteMeeting() throws Exception {
        doNothing().when(meetingCalendarService).deleteMeeting(1);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/meetingCalendar/{id}", 1));
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}