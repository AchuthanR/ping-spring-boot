package com.incedo.ping.admin_service.service;

import com.incedo.ping.admin_service.AbstractTests;
import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.Notification;
import com.incedo.ping.admin_service.model.NotificationCategory;
import com.incedo.ping.admin_service.model.NotificationStatus;
import com.incedo.ping.admin_service.model.User;
import com.incedo.ping.admin_service.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTests extends AbstractTests {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationStatusRepository notificationStatusRepository;

    @Mock
    private NotificationCategoryRepository notificationCategoryRepository;

    @Mock
    private NotificationTemplateRepository notificationTemplateRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<Notification> notificationList = new ArrayList<>();
        NotificationCategory category = new NotificationCategory();
        category.setId(1);
        category.setCategory("Category 1");

        User user = new User();
        user.setId(1);
        user.setUsername("user1");

        NotificationStatus status = new NotificationStatus();
        status.setId(1);
        status.setStatus("Status 1");

        notificationList.add(new Notification(1, "Title 1", "Content 1", LocalDateTime.now(), status, user, category, null));
        notificationList.add(new Notification(2, "Title 2", "Content 2", LocalDateTime.now(), status, user, category, null));

        when(notificationRepository.findAll()).thenReturn(notificationList);

        List<Notification> result = notificationService.getAll();

        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Title 2", result.get(1).getTitle());

        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    public void testGetOneExistingId() throws ResourceNotFoundException {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");

        NotificationCategory category = new NotificationCategory();
        category.setId(1);
        category.setCategory("Category 1");

        NotificationStatus status = new NotificationStatus();
        status.setId(1);
        status.setStatus("Status 1");

        Notification notification = new Notification(1, "Title 1", "Content 1", LocalDateTime.now(), status, user, category, null);

        when(notificationRepository.findById(1)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getOne(1);

        assertNotNull(result);
        assertEquals("Title 1", result.getTitle());

        verify(notificationRepository, times(1)).findById(1);
    }

    @Test
    public void testGetOneNonExistingId() {
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> notificationService.getOne(1));

        verify(notificationRepository, times(1)).findById(1);
    }

    @Test
    public void testInsertValidData() throws ResourceNotFoundException {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");

        NotificationCategory category = new NotificationCategory();
        category.setId(1);
        category.setCategory("Category 1");

        NotificationStatus status = new NotificationStatus();
        status.setId(1);
        status.setStatus("Status 1");

        Notification newNotification = new Notification(1, "Title 1", "Content 1", LocalDateTime.now(), status, user, category, null);

        when(notificationStatusRepository.findById(1)).thenReturn(Optional.of(status));
        when(notificationCategoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenReturn(newNotification);

        Notification result = notificationService.insert(newNotification);

        assertNotNull(result);
        assertEquals("Title 1", result.getTitle());

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void testInsertInvalidStatus() {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");

        NotificationCategory category = new NotificationCategory();
        category.setId(1);
        category.setCategory("Category 1");

        NotificationStatus status = new NotificationStatus();
        status.setId(1);
        status.setStatus("Status 1");

        Notification newNotification = new Notification(1, "Title 1", "Content 1", LocalDateTime.now(), status, user, category, null);

        when(notificationStatusRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> notificationService.insert(newNotification));

        verify(notificationRepository, never()).save(any(Notification.class));
    }

}