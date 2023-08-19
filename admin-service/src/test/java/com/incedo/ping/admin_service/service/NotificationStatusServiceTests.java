package com.incedo.ping.admin_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.incedo.ping.admin_service.AbstractTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.NotificationStatus;
import com.incedo.ping.admin_service.repository.NotificationStatusRepository;

public class NotificationStatusServiceTests extends AbstractTests {

    @Mock
    private NotificationStatusRepository statusRepository;

    @InjectMocks
    private NotificationStatusService statusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        List<NotificationStatus> statusList = new ArrayList<>();
        statusList.add(new NotificationStatus(1, "Sent"));
        statusList.add(new NotificationStatus(2, "Delivered"));

        when(statusRepository.findAll()).thenReturn(statusList);

        List<NotificationStatus> result = statusService.getAll();

        assertEquals(2, result.size());
        assertEquals("Sent", result.get(0).getStatus());
        assertEquals("Delivered", result.get(1).getStatus());

        verify(statusRepository, times(1)).findAll();
    }

    @Test
    public void testGetOneExistingId() throws ResourceNotFoundException {
        NotificationStatus status = new NotificationStatus(1, "Sent");
        Optional<NotificationStatus> optionalStatus = Optional.of(status);

        when(statusRepository.findById(1)).thenReturn(optionalStatus);

        NotificationStatus result = statusService.getOne(1);

        assertEquals("Sent", result.getStatus());

        verify(statusRepository, times(1)).findById(1);
    }

    @Test
    public void testGetOneNonExistingId() {
        Optional<NotificationStatus> optionalStatus = Optional.empty();

        when(statusRepository.findById(1)).thenReturn(optionalStatus);

        assertThrows(ResourceNotFoundException.class, () -> statusService.getOne(1));

        verify(statusRepository, times(1)).findById(1);
    }

    @Test
    public void testInsert() {
        NotificationStatus status = new NotificationStatus(1, "Sent");

        when(statusRepository.save(status)).thenReturn(status);

        NotificationStatus result = statusService.insert(status);

        assertEquals("Sent", result.getStatus());

        verify(statusRepository, times(1)).save(status);
    }

    @Test
    public void testUpdateNonExistingId() {
        Optional<NotificationStatus> optionalStatus = Optional.empty();

        when(statusRepository.findById(1)).thenReturn(optionalStatus);

        NotificationStatus updatedStatus = new NotificationStatus(1, "Updated");

        assertThrows(ResourceNotFoundException.class, () -> statusService.update(1, updatedStatus));

        verify(statusRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteExistingId() throws ResourceNotFoundException {
        NotificationStatus existingStatus = new NotificationStatus(1, "Sent");
        Optional<NotificationStatus> optionalStatus = Optional.of(existingStatus);

        when(statusRepository.findById(1)).thenReturn(optionalStatus);

        statusService.delete(1);

        verify(statusRepository, times(1)).findById(1);
        verify(statusRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNonExistingId() {
        Optional<NotificationStatus> optionalStatus = Optional.empty();

        when(statusRepository.findById(1)).thenReturn(optionalStatus);

        assertThrows(ResourceNotFoundException.class, () -> statusService.delete(1));

        verify(statusRepository, times(1)).findById(1);
    }
}