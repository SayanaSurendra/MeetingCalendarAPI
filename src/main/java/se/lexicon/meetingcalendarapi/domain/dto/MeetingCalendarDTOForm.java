package se.lexicon.meetingcalendarapi.domain.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record MeetingCalendarDTOForm(
        Integer meetingId,

        @NotBlank(message = "Title should not be empty")
        String meetingTitle,

        @NotBlank(message = "Description should not be empty")
        String meetingDescription,

        @NotBlank(message = "Meeting Level should not be empty")
        String meetingLevel,

        @NotNull(message = "Meeting Date  should not be empty")
        @FutureOrPresent(message = "Meeting date cannot be in the past")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate meetingDate,

        @NotNull(message = "Meeting Time  should not be empty")
        @FutureOrPresent(message = "Meeting time cannot be  in the past")
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        LocalTime meetingTime,

        @NotNull(message = "Participants cannot be null")
        @Size(min = 1, message = "Meeting calendar  must have at least one participant")
        @Valid
        Set<ParticipantDTOForm> participants
) {


}
