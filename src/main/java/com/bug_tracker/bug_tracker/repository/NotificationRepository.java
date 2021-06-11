package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Notification;
import com.bug_tracker.bug_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiver(User u);
}
