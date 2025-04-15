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
    String  email;




}
