package se.lexicon.meetingcalendarapi.service;

import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOView;

import java.util.List;

public interface MeetingCalendarService {

   List<MeetingCalendarDTOView> getAllMeetings();

   MeetingCalendarDTOView createMeeting(MeetingCalendarDTOForm meeting);

   void updateMeeting(MeetingCalendarDTOForm meeting);

   MeetingCalendarDTOView getMeetingById(Integer id);

   void deleteMeeting(Integer meetingId);
}
