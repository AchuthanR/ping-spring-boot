package com.incedo.ping.admin_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.incedo.ping.admin_service.AbstractTests;
import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.NotificationCategory;
import com.incedo.ping.admin_service.model.NotificationTemplate;
import com.incedo.ping.admin_service.repository.NotificationTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class NotificationTemplateServiceTests extends AbstractTests {

    @Mock
    private NotificationTemplateRepository templateRepository;

    @InjectMocks
    private NotificationTemplateService templateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<NotificationTemplate> templateList = new ArrayList<>();
        NotificationCategory category = new NotificationCategory(1, "Category 1");

        templateList.add(new NotificationTemplate(1, "Template 1", "Content 1", category));
        templateList.add(new NotificationTemplate(2, "Template 2", "Content 2", category));

        when(templateRepository.findAll()).thenReturn(templateList);

        List<NotificationTemplate> result = templateService.getAll();

        assertEquals(2, result.size());
        assertEquals("Template 1", result.get(0).getTitle());
        assertEquals("Template 2", result.get(1).getTitle());

        verify(templateRepository, times(1)).findAll();
    }

    @Test
    public void testGetOneExistingId() throws ResourceNotFoundException {
        NotificationCategory category = new NotificationCategory(1, "Category 1");

        NotificationTemplate template = new NotificationTemplate(1, "Template 1", "Content 1", category);
        Optional<NotificationTemplate> optionalTemplate = Optional.of(template);

        when(templateRepository.findById(1)).thenReturn(optionalTemplate);

        NotificationTemplate result = templateService.getOne(1);

        assertEquals("Template 1", result.getTitle());

        verify(templateRepository, times(1)).findById(1);
    }

    @Test
    public void testGetOneNonExistingId() {
        Optional<NotificationTemplate> optionalTemplate = Optional.empty();

        when(templateRepository.findById(1)).thenReturn(optionalTemplate);

        assertThrows(ResourceNotFoundException.class, () -> templateService.getOne(1));

        verify(templateRepository, times(1)).findById(1);
    }

    @Test
    public void testInsert() {
        NotificationCategory category = new NotificationCategory(1, "Category 1");

        NotificationTemplate template = new NotificationTemplate(1, "Template 1", "Content 1", category);

        when(templateRepository.save(template)).thenReturn(template);

        NotificationTemplate result = templateService.insert(template);

        assertEquals("Template 1", result.getTitle());

        verify(templateRepository, times(1)).save(template);
    }

    @Test
    public void testUpdateNonExistingId() {
        Optional<NotificationTemplate> optionalTemplate = Optional.empty();

        when(templateRepository.findById(1)).thenReturn(optionalTemplate);

        NotificationCategory category = new NotificationCategory(1, "Category 1");

        NotificationTemplate updatedTemplate = new NotificationTemplate(1, "Updated Template", "Updated Content", category);

        assertThrows(ResourceNotFoundException.class, () -> templateService.update(1, updatedTemplate));

        verify(templateRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteExistingId() throws ResourceNotFoundException {
        NotificationCategory category = new NotificationCategory(1, "Category 1");
        category.setCategory("Category 1");

        NotificationTemplate existingTemplate = new NotificationTemplate(1, "Template 1", "Content 1", category);
        Optional<NotificationTemplate> optionalTemplate = Optional.of(existingTemplate);

        when(templateRepository.findById(1)).thenReturn(optionalTemplate);

        templateService.delete(1);

        verify(templateRepository, times(1)).findById(1);
        verify(templateRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNonExistingId() {
        Optional<NotificationTemplate> optionalTemplate = Optional.empty();

        when(templateRepository.findById(1)).thenReturn(optionalTemplate);

        assertThrows(ResourceNotFoundException.class, () -> templateService.delete(1));

        verify(templateRepository, times(1)).findById(1);
    }

}