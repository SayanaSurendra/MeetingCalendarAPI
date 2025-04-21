package se.lexicon.meetingcalendarapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOForm;
import se.lexicon.meetingcalendarapi.domain.dto.MeetingCalendarDTOView;
import se.lexicon.meetingcalendarapi.exception.ErrorResponseDto;
import se.lexicon.meetingcalendarapi.service.MeetingCalendarService;
import java.util.List;

@Tag(name = "Meeting Calendar Management APIs")
@RestController
@Validated
@RequestMapping("/api/meetingCalendar")
@CrossOrigin("*")
public class MeetingCalendarController {

    private final MeetingCalendarService meetingCalendarService;

    @Autowired
    public MeetingCalendarController(MeetingCalendarService meetingCalendarService) {
        this.meetingCalendarService = meetingCalendarService;
    }

    /**
     * Retrieves a list of all meetings
     * @return a response entity containing a list of MeetingCalendarDTOView objects, representing each meeting's
     *  calendar view.
     */
    @Operation(summary = "Retrieves list of meeting calendars",
            description = "Retrieves list of meeting calendars. The response includes a list of meeting calendars.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A list of meeting calendar retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))})
    })

    @GetMapping
    public ResponseEntity<List<MeetingCalendarDTOView>> doGetAllMeetings(){
        List<MeetingCalendarDTOView> meetings=meetingCalendarService.getAllMeetings();
        return ResponseEntity.status(HttpStatus.OK).body(meetings);
   }


    /**
     *  Retrieves a meeting by its unique ID
     * @param meetingId the unique identifier of the meeting to be retrieved
     * @return a response entity containing a MeetingCalendarDTOView object of the requested meeting
     */

    @Operation(summary = "Retrieves a meeting calendar by its unique ID.",
            description = "Retrieves a meeting calendar by its unique ID. The response is a meeting calendar object of the requested ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description ="Meeting Calendar of requested ID retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))})
    })

    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingCalendarDTOView> doGetByIdMeeting(@PathVariable("meetingId") Integer meetingId){
        MeetingCalendarDTOView meeting=meetingCalendarService.getMeetingById(meetingId);
        return ResponseEntity.status(HttpStatus.OK).body(meeting);
    }


    /**
     *  Creates a new meeting calendar
     * @param meeting the meeting form containing the meetng details
     * @return a response entity containing the created MeetingCalendarDTOView object
     */
    @Operation(summary = "Creates a new meeting calendar.",
            description = "Creates and saves a new meeting calendar entry using the provided meeting details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description ="Meeting Calendar created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))})
    })
   @PostMapping
    public ResponseEntity<MeetingCalendarDTOView> doCreateMeeting(@RequestBody MeetingCalendarDTOForm meeting){
        MeetingCalendarDTOView meetingCalendar = meetingCalendarService.createMeeting(meeting);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(meetingCalendar);
   }


    /**
     *  Updates an existing meeting calendar entry
     * @param meeting the meeting calendra form containing the updated meeting details
     * @return a response entity with no content if the update was successful
     */
    @Operation(summary = "Updates an existing meeting calendar.",
            description = "Updates an existing meeting calendar entry using the provided meeting details.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description ="Meeting Calendar updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))})
    })

   @PutMapping
   public  ResponseEntity<Void> doUpdateMeeting(@RequestBody MeetingCalendarDTOForm meeting){
       System.out.println("MEETING CALENDAR:::"+meeting.meetingId());
        meetingCalendarService.updateMeeting(meeting);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }


    /**
     *  Deletes a meeting calendar by its unique ID
     * @param meetingId the unique identifier of the meeting to be deleted
     * @return a response entity with no content if deletion is successful
     */

    @Operation(summary = "Deletes a meeting calendar by its unique ID.",
            description = "Deletes a meeting calendar entry based on  the provided meeting ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description ="Meeting Calendar deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))})
    })
   @DeleteMapping(("/{meetingId}"))
   public  ResponseEntity<Void> doDeleteMeeting(@PathVariable("meetingId") Integer meetingId){
       meetingCalendarService.deleteMeeting(meetingId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}
