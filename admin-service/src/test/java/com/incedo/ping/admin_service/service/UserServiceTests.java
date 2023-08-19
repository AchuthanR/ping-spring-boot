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

class UserServiceTests extends AbstractTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAll();

        assertEquals(userList.size(), result.size());
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetOneUserExists() throws ResourceNotFoundException {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getOne(1);

        assertEquals(user.getId(), result.getId());
        verify(userRepository, times(1)).findById(1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetOneUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getOne(1));
        verify(userRepository, times(1)).findById(1);
        verifyNoMoreInteractions(userRepository);
    }

}