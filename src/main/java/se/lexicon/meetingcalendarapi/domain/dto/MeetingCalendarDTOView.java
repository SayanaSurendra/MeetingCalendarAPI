package se.lexicon.meetingcalendarapi.domain.dto;

import jakarta.persistence.SecondaryTable;
import lombok.Builder;
import se.lexicon.meetingcalendarapi.domain.entity.Participant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Builder

public record MeetingCalendarDTOView(
        Integer meetingId,
        String meetingTitle,
        String meetingLevel,

        LocalDate meetingDate,

        LocalTime meetingTime,
        String meetingDescription,
        Set<Participant> participants
) {


}
