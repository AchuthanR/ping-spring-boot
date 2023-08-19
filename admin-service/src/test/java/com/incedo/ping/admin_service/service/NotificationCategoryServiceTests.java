package com.incedo.ping.admin_service.service;

import com.incedo.ping.admin_service.AbstractTests;
import com.incedo.ping.admin_service.exception.ResourceNotFoundException;
import com.incedo.ping.admin_service.model.NotificationCategory;
import com.incedo.ping.admin_service.repository.NotificationCategoryRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationCategoryServiceTests extends AbstractTests {

	@Mock
	private NotificationCategoryRepository notificationCategoryRepository;

	@InjectMocks
	private NotificationCategoryService notificationCategoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAll() {
		List<NotificationCategory> categories = new ArrayList<>();
		categories.add(new NotificationCategory(1, "Category 1"));
		categories.add(new NotificationCategory(2, "Category 2"));

		when(notificationCategoryRepository.findAll()).thenReturn(categories);

		List<NotificationCategory> result = notificationCategoryService.getAll();

		assertEquals(2, result.size());
	}

	@Test
	public void testGetOneExistingCategory() throws ResourceNotFoundException {
		NotificationCategory category = new NotificationCategory(1, "Category 1");

		when(notificationCategoryRepository.findById(1)).thenReturn(Optional.of(category));

		NotificationCategory result = notificationCategoryService.getOne(1);

		assertEquals(category, result);
	}

	@Test
	public void testGetOneNonExistingCategory() {
		when(notificationCategoryRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> notificationCategoryService.getOne(1));
	}

	@Test
	public void testInsert() {
		NotificationCategory category = new NotificationCategory(1, "Category 1");

		when(notificationCategoryRepository.save(category)).thenReturn(category);

		NotificationCategory result = notificationCategoryService.insert(category);

		assertEquals(category, result);
	}

	@Test
	public void testUpdateExistingCategory() throws ResourceNotFoundException {
		NotificationCategory existingCategory = new NotificationCategory(1, "Category 1");
		NotificationCategory updatedCategory = new NotificationCategory(1, "Updated Category 1");

		when(notificationCategoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));
		when(notificationCategoryRepository.save(updatedCategory)).thenReturn(updatedCategory);

		NotificationCategory result = notificationCategoryService.update(1, updatedCategory);

		assertEquals(updatedCategory, result);
	}

	@Test
	public void testUpdateNonExistingCategory() {
		NotificationCategory updatedCategory = new NotificationCategory(1, "Updated Category 1");

		when(notificationCategoryRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> notificationCategoryService.update(1, updatedCategory));
	}

	@Test
	public void testDeleteExistingCategory() throws ResourceNotFoundException {
		NotificationCategory existingCategory = new NotificationCategory(1, "Category 1");

		when(notificationCategoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));

		assertDoesNotThrow(() -> notificationCategoryService.delete(1));
		verify(notificationCategoryRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDeleteNonExistingCategory() {
		when(notificationCategoryRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> notificationCategoryService.delete(1));
	}

}
