package se.lexicon.meetingcalendarapi.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Builder
public class MeetingCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer meetingId;
    String meetingTitle;
    String meetingLevel;
    String meetingDescription;
    LocalDate meetingDate;
    LocalTime meetingTime;

    @OneToMany
    @JoinColumn(name = "meeting_id")
    Set<Participant> participants = new HashSet<>();




    public MeetingCalendar(String meetingTitle,String meetingDescription,String meetingLevel, LocalDate meetingDate, LocalTime meetingTime, Set<Participant> participants) {
        this.meetingTitle = meetingTitle;
        this.meetingDescription = meetingDescription;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.participants = participants;
        this.meetingLevel = meetingLevel;

    }
}
