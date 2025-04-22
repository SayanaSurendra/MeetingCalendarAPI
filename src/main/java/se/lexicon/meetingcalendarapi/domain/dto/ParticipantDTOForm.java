package se.lexicon.meetingcalendarapi.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParticipantDTOForm(
        @NotBlank(message = "Email is mandatory")
        @Email(message="Invalid email format")
        String email


) {
}
