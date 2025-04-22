package se.lexicon.meetingcalendarapi.domain.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ParticipantDTOView(String email) {

}
