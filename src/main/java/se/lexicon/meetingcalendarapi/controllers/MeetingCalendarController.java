package se.lexicon.meetingcalendarapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOView;
import se.lexicon.meetingcalendarapi.service.MeetingCalendarService;

import java.util.List;

@RestController
@RequestMapping("/api/meetingCalendar")
@CrossOrigin("*")
public class MeetingCalendarController {

    private final MeetingCalendarService meetingCalendarService;

    @Autowired
    public MeetingCalendarController(MeetingCalendarService meetingCalendarService) {
        this.meetingCalendarService = meetingCalendarService;
    }

    @GetMapping
    public ResponseEntity<List<MeetingCalendarDTOView>> doGetAllMeetings(){
        List<MeetingCalendarDTOView> meetings=meetingCalendarService.getAllMeetings();
        return ResponseEntity.status(HttpStatus.OK).body(meetings);
   }


    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingCalendarDTOView> doGetByIdMeeting(@PathVariable("meetingId") Integer meetingId){
        MeetingCalendarDTOView meeting=meetingCalendarService.getMeetingById(meetingId);
        return ResponseEntity.status(HttpStatus.OK).body(meeting);
    }

   @PostMapping
    public ResponseEntity<MeetingCalendarDTOView> doCreateMeeting(@RequestBody MeetingCalendarDTOForm meeting){
        MeetingCalendarDTOView meetingCalendar = meetingCalendarService.createMeeting(meeting);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(meetingCalendar);
   }

   @PutMapping
   public  ResponseEntity<Void> doUpdateMeeting(@RequestBody MeetingCalendarDTOForm meeting){
       System.out.println("MEETING CALENDAR:::"+meeting.meetingId());
        meetingCalendarService.updateMeeting(meeting);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping(("/{meetingId}"))
   public  ResponseEntity<Void> doDeleteMeeting(@PathVariable("meetingId") Integer meetingId){
       meetingCalendarService.deleteMeeting(meetingId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}
