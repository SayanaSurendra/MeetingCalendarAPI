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
@Builder
public class MeetingCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer meetingId;

    @Column(nullable = false)
    String meetingTitle;

    @Column(nullable = false)
    String meetingLevel;

    @Column(nullable = false)
    String meetingDescription;

    @Column(nullable = false)
    LocalDate meetingDate;

    @Column(nullable = false)
    LocalTime meetingTime;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "meeting_participants",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "email")
    )
    Set<Participant> participants = new HashSet<>();


    public MeetingCalendar(String meetingTitle,String meetingDescription,String meetingLevel, LocalDate meetingDate, LocalTime meetingTime, Set<Participant> participants) {
        this.meetingTitle = meetingTitle;
        this.meetingDescription = meetingDescription;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.participants = participants;
        this.meetingLevel = meetingLevel;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MeetingCalendar that = (MeetingCalendar) o;
        return Objects.equals(meetingId, that.meetingId) && Objects.equals(meetingTitle, that.meetingTitle) && Objects.equals(meetingLevel, that.meetingLevel) && Objects.equals(meetingDescription, that.meetingDescription) && Objects.equals(meetingDate, that.meetingDate) && Objects.equals(meetingTime, that.meetingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, meetingTitle, meetingLevel, meetingDescription, meetingDate, meetingTime);
    }
}
