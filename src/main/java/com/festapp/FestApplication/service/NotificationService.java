package com.festapp.FestApplication.service;

import java.util.List;

import com.festapp.FestApplication.models.Notification;

public interface NotificationService {
    List<Notification> getAllNotifications(Long userId);
    void markAllAsRead(Long userId);


    long getUnreadNotificationCount(Long userId) ;
}