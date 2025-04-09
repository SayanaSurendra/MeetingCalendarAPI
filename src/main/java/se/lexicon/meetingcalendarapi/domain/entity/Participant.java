package se.lexicon.meetingcalendarapi.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Builder
public class Participant {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer id;

    @NonNull
    String name;

    public Participant(String name) {

        this.name = name;
    }
}
