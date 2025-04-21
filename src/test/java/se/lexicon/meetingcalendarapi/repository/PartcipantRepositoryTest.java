package se.lexicon.meetingcalendarapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.meetingcalendarapi.domain.entity.Participant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PartcipantRepositoryTest {

    @Autowired
    private PartcipantRepository partcipantRepository;

    Set<Participant> participants  = new HashSet<>();
    Set<String> emails = new HashSet<>();


    @BeforeEach
    void setUp() {
        Participant participant1=new Participant("test@gmail.com");
        Participant participant2=new Participant("testuser@gmail.com");
        participants.add(participant1);
        participants.add(participant2);
        participants.stream()
                .map(p -> p.getEmail())
                .collect(Collectors.toSet());
        partcipantRepository.saveAll(participants);

    }

    @Test
    void findAllByEmailIn() {
        Set<String> emails = Set.of("test@gmail.com", "testuser@gmail.com");
     List<Participant> result= partcipantRepository.findAllByEmailIn(emails);
    assertNotNull(result);
    assertEquals(emails.size(),result.size());
    }
}