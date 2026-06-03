package com.festapp.FestApplication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.festapp.FestApplication.dto.UserEventApplicantDTO;
import com.festapp.FestApplication.dto.UserEventDTO;
import com.festapp.FestApplication.dto.UserEventJoinRequest;
import com.festapp.FestApplication.dto.UserEventRequest;
import com.festapp.FestApplication.service.UserEventService;

@RestController
@RequestMapping("/api/user-events")
public class UserEventController {
    private final UserEventService userEventService;

    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping
    public ResponseEntity<List<UserEventDTO>> getAllEvents(@RequestParam(required = false) Long currentUserId) {
        return ResponseEntity.ok(userEventService.getAllEvents(currentUserId));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody UserEventRequest request) {
        try {
            return new ResponseEntity<>(userEventService.createEvent(request), HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<UserEventDTO>> getMyEvents(@RequestParam Long hostUserId) {
        return ResponseEntity.ok(userEventService.getMyEvents(hostUserId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventDetails(@PathVariable Long eventId, @RequestParam(required = false) Long currentUserId) {
        try {
            return ResponseEntity.ok(userEventService.getEventDetails(eventId, currentUserId));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{eventId}/requests")
    public ResponseEntity<?> requestToJoin(@PathVariable Long eventId, @RequestBody UserEventJoinRequest request) {
        try {
            return ResponseEntity.ok(userEventService.requestToJoin(eventId, request));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<?> getEventRequests(@PathVariable Long eventId, @RequestParam Long hostUserId) {
        try {
            return ResponseEntity.ok(userEventService.getEventRequests(eventId, hostUserId));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<?> getEventAttendees(@PathVariable Long eventId, @RequestParam Long currentUserId) {
        try {
            return ResponseEntity.ok(userEventService.getEventAttendees(eventId, currentUserId));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/requests/{requestId}/accept")
    public ResponseEntity<?> acceptRequest(@PathVariable Long requestId, @RequestParam Long hostUserId) {
        try {
            userEventService.acceptRequest(requestId, hostUserId);
            return ResponseEntity.ok(Map.of("accepted", true));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/requests/{requestId}/decline")
    public ResponseEntity<?> declineRequest(@PathVariable Long requestId, @RequestParam Long hostUserId) {
        try {
            userEventService.declineRequest(requestId, hostUserId);
            return ResponseEntity.ok(Map.of("declined", true));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
