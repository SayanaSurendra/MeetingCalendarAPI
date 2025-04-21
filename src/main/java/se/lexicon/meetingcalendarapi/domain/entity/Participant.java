package se.lexicon.meetingcalendarapi.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class Participant {

    @NonNull
    @Id
    @Column( unique = true)
    String  email;

   @ToString.Exclude
    @ManyToMany(mappedBy = "participants")
    private Set<MeetingCalendar> meetings = new HashSet<>();


    public Participant(@NotBlank(message = "Email is mandatory") @Email(message="Invalid email format") String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
