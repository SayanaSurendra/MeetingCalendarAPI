package se.lexicon.meetingcalendarapi.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ParticipantDTOForm(
        @NotBlank(message = "Email is mandatory")
        @Email(message="Invalid email format")
        String email


) {
}
