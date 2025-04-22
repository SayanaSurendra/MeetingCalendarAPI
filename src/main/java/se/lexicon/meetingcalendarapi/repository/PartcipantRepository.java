package se.lexicon.meetingcalendarapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.meetingcalendarapi.domain.entity.Participant;

import java.util.List;
import java.util.Set;

@Repository
public interface PartcipantRepository extends JpaRepository<Participant,String> {


    List<Participant> findAllByEmailIn(Set<String> emails);

}
