package se.lexicon.meetingcalendarapi.domain.dto;


import se.lexicon.meetingcalendarapi.domain.entity.Participant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record MeetingCalendarDTOForm(
        Integer meetingId,
        String meetingTitle,
        String meetingDescription,
        String meetingLevel,
        LocalDate meetingDate,
        LocalTime meetingTime,
        Set<ParticipantDTOForm> participants
) {


}
