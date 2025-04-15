package se.lexicon.meetingcalendarapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.meetingcalendarapi.domain.entity.MeetingCalendar;

@Repository
public interface MeetingCalendarRepository extends JpaRepository<MeetingCalendar,Integer> {
}
