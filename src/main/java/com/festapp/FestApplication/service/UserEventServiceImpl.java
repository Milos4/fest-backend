package com.festapp.FestApplication.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festapp.FestApplication.dto.UserEventApplicantDTO;
import com.festapp.FestApplication.dto.UserEventDTO;
import com.festapp.FestApplication.dto.UserEventJoinRequest;
import com.festapp.FestApplication.dto.UserEventRequest;
import com.festapp.FestApplication.models.Bio;
import com.festapp.FestApplication.models.Notification;
import com.festapp.FestApplication.models.Notification.NotificationType;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.models.UserEvent;
import com.festapp.FestApplication.models.UserEventSwipe;
import com.festapp.FestApplication.models.UserEventSwipe.SwipeStatus;
import com.festapp.FestApplication.repository.NotificationRepository;
import com.festapp.FestApplication.repository.UserEventRepository;
import com.festapp.FestApplication.repository.UserEventSwipeRepository;
import com.festapp.FestApplication.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserEventServiceImpl implements UserEventService {
    private final UserEventRepository userEventRepository;
    private final UserEventSwipeRepository userEventSwipeRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public UserEventServiceImpl(UserEventRepository userEventRepository,
            UserEventSwipeRepository userEventSwipeRepository, UserRepository userRepository,
            NotificationRepository notificationRepository) {
        this.userEventRepository = userEventRepository;
        this.userEventSwipeRepository = userEventSwipeRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public UserEventDTO createEvent(UserEventRequest request) {
        User host = userRepository.findById(request.getHostUserId())
                .orElseThrow(() -> new IllegalArgumentException("Host user not found"));

        UserEvent event = new UserEvent();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setCity(request.getCity());
        event.setLocation(request.getLocation());
        event.setLatitude(request.getLatitude());
        event.setLongitude(request.getLongitude());
        event.setMaxAttendees(request.getMaxAttendees());
        event.setType(request.getType());
        event.setAttendeesCountM(0);
        event.setAttendeesCountW(0);
        event.setHostUser(host);

        return toDto(userEventRepository.save(event), host.getId());
    }

    @Override
    public List<UserEventDTO> getAllEvents(Long currentUserId) {
        return userEventRepository.findAll().stream()
                .map(event -> toDto(event, currentUserId))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEventDTO> getMyEvents(Long hostUserId) {
        return userEventRepository.findByHostUserId(hostUserId).stream()
                .map(event -> toDto(event, hostUserId))
                .collect(Collectors.toList());
    }

    @Override
    public UserEventDTO getEventDetails(Long eventId, Long currentUserId) {
        UserEvent event = getEvent(eventId);
        return toDto(event, currentUserId);
    }

    @Override
    @Transactional
    public UserEventDTO requestToJoin(Long eventId, UserEventJoinRequest request) {
        UserEvent event = getEvent(eventId);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (event.getHostUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Host is already owner of this event");
        }

        Optional<UserEventSwipe> existingRequest = userEventSwipeRepository.findByUserAndUserEvent(user, event);
        if (existingRequest.isPresent()) {
            return toDto(event, user.getId());
        }

        UserEventSwipe eventRequest = new UserEventSwipe();
        eventRequest.setUser(user);
        eventRequest.setUserEvent(event);
        eventRequest.setMessage(request.getMessage());
        eventRequest.setNumberOfMen(0);
        eventRequest.setNumberOfWomen(0);
        eventRequest.setStatus(SwipeStatus.PENDING);
        userEventSwipeRepository.save(eventRequest);

        Notification notification = new Notification(
                null,
                event.getHostUser(),
                user.getUsername() + " requested to join your event " + event.getName(),
                NotificationType.EVENT_REQUEST,
                LocalDateTime.now(),
                false);
        notificationRepository.save(notification);

        return toDto(event, user.getId());
    }

    @Override
    public List<UserEventApplicantDTO> getEventRequests(Long eventId, Long hostUserId) {
        UserEvent event = getEvent(eventId);
        checkHost(event, hostUserId);

        return userEventSwipeRepository.findByUserEventIdAndStatus(eventId, SwipeStatus.PENDING).stream()
                .map(this::toApplicantDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEventApplicantDTO> getEventAttendees(Long eventId, Long currentUserId) {
        UserEvent event = getEvent(eventId);
        if (!canViewDetails(event, currentUserId)) {
            throw new IllegalArgumentException("You are not allowed to view this event");
        }

        return userEventSwipeRepository.findByUserEventIdAndStatus(eventId, SwipeStatus.ACCEPTED).stream()
                .map(this::toApplicantDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void acceptRequest(Long requestId, Long hostUserId) {
        UserEventSwipe request = getEventRequest(requestId);
        UserEvent event = request.getUserEvent();
        checkHost(event, hostUserId);

        request.setStatus(SwipeStatus.ACCEPTED);
        userEventSwipeRepository.save(request);

        Notification notification = new Notification(
                null,
                request.getUser(),
                event.getHostUser().getUsername() + " accepted you on event " + event.getName(),
                NotificationType.ACCEPTED_ON_EVENT,
                LocalDateTime.now(),
                false);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void declineRequest(Long requestId, Long hostUserId) {
        UserEventSwipe request = getEventRequest(requestId);
        checkHost(request.getUserEvent(), hostUserId);
        request.setStatus(SwipeStatus.DECLINED);
        userEventSwipeRepository.save(request);
    }

    private UserEvent getEvent(Long eventId) {
        return userEventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    private UserEventSwipe getEventRequest(Long requestId) {
        return userEventSwipeRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Event request not found"));
    }

    private void checkHost(UserEvent event, Long hostUserId) {
        if (!event.getHostUser().getId().equals(hostUserId)) {
            throw new IllegalArgumentException("Only event host can do this");
        }
    }

    private boolean canViewDetails(UserEvent event, Long currentUserId) {
        if (currentUserId == null) {
            return false;
        }
        if (event.getHostUser().getId().equals(currentUserId)) {
            return true;
        }
        User user = userRepository.findById(currentUserId).orElse(null);
        if (user == null) {
            return false;
        }
        return userEventSwipeRepository.findByUserAndUserEventAndStatus(user, event, SwipeStatus.ACCEPTED).isPresent();
    }

    private String getRequestStatus(UserEvent event, Long currentUserId) {
        if (currentUserId == null) {
            return null;
        }
        User user = userRepository.findById(currentUserId).orElse(null);
        if (user == null) {
            return null;
        }
        return userEventSwipeRepository.findByUserAndUserEvent(user, event)
                .map(request -> request.getStatus().name())
                .orElse(null);
    }

    private UserEventDTO toDto(UserEvent event, Long currentUserId) {
        boolean canViewDetails = canViewDetails(event, currentUserId);
        UserEventDTO dto = new UserEventDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setCity(event.getCity());
        dto.setMaxAttendees(event.getMaxAttendees());
        dto.setType(event.getType());
        dto.setHostUserId(event.getHostUser().getId());
        dto.setHostUsername(event.getHostUser().getUsername());
        dto.setRequestStatus(getRequestStatus(event, currentUserId));
        dto.setCanViewDetails(canViewDetails);

        if (canViewDetails) {
            dto.setLocation(event.getLocation());
            dto.setLatitude(event.getLatitude());
            dto.setLongitude(event.getLongitude());
        }

        return dto;
    }

    private UserEventApplicantDTO toApplicantDto(UserEventSwipe request) {
        User user = request.getUser();
        Bio bio = user.getBio();

        UserEventApplicantDTO dto = new UserEventApplicantDTO();
        dto.setRequestId(request.getId());
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setMessage(request.getMessage());
        dto.setStatus(request.getStatus().name());

        if (bio != null) {
            dto.setFirstName(bio.getFirstName());
            dto.setLastName(bio.getLastName());
            dto.setProfilePictureUrl(bio.getProfilePictureUrl());
            dto.setLocation(bio.getLocation());
        }

        return dto;
    }
}
