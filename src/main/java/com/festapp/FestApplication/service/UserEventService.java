package com.festapp.FestApplication.service;

import java.util.List;

import com.festapp.FestApplication.dto.UserEventApplicantDTO;
import com.festapp.FestApplication.dto.UserEventDTO;
import com.festapp.FestApplication.dto.UserEventJoinRequest;
import com.festapp.FestApplication.dto.UserEventRequest;

public interface UserEventService {
    UserEventDTO createEvent(UserEventRequest request);

    List<UserEventDTO> getAllEvents(Long currentUserId);

    List<UserEventDTO> getMyEvents(Long hostUserId);

    UserEventDTO getEventDetails(Long eventId, Long currentUserId);

    UserEventDTO requestToJoin(Long eventId, UserEventJoinRequest request);

    List<UserEventApplicantDTO> getEventRequests(Long eventId, Long hostUserId);

    List<UserEventApplicantDTO> getEventAttendees(Long eventId, Long currentUserId);

    void acceptRequest(Long requestId, Long hostUserId);

    void declineRequest(Long requestId, Long hostUserId);
}
