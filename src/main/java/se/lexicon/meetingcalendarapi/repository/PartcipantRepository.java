package se.lexicon.meetingcalendarapi.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.meetingcalendarapi.domain.entity.Participant;

import java.util.List;

@Repository
public interface PartcipantRepository extends JpaRepository<Participant,String> {

}
