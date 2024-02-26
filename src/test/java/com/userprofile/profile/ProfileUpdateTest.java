package com.userprofile.profile;

import com.userprofile.profile.model.entity.Profile;
import com.userprofile.profile.model.response.ResponseHandler;
import com.userprofile.profile.repository.ProfileRepository;
import com.userprofile.profile.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileUpdateTest {
    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final ResponseHandler<Profile> responseHandler = new ResponseHandler<>();
    private final ProfileService profileService = new ProfileService(profileRepository, responseHandler);

    private Profile profile;

    @Test
    public void testUpdateProfileSuccess() {
        // Prepare test data
        long userId = 1;
        Profile existingProfile = new Profile();
        existingProfile.setUserId(userId);
        existingProfile.setEmail("oldemail@gmail.com");
        existingProfile.setFirstName("OldFirstName");
        existingProfile.setLastName("OldLastName");
        existingProfile.setDateOfBirth(LocalDate.parse("1990-01-01"));

        Profile updatedProfile = new Profile();
        updatedProfile.setUserId(userId);
        updatedProfile.setEmail("newemail@gmail.com");
        updatedProfile.setFirstName("NewFirstName");
        updatedProfile.setLastName("NewLastName");
        updatedProfile.setDateOfBirth(LocalDate.parse("1995-01-01"));

        // Mock profileRepository behavior
        when(profileRepository.findById(userId)).thenReturn(Optional.of(existingProfile));
        when(profileRepository.save(any(Profile.class))).thenReturn(updatedProfile);

        // Call the service method
        ResponseEntity<?> responseEntity = profileService.updateProfile(userId, updatedProfile);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Extract the response body and check if it's an instance of ResponseHandler
        Object responseBody = responseEntity.getBody();
        assertTrue(responseBody instanceof ResponseHandler);

        ResponseHandler<?> responseHandler = (ResponseHandler<?>) responseBody;

        assertEquals("OK", responseHandler.getStatus().getType());
        assertEquals("Profile Update Success", responseHandler.getResponse());
    }

//    @Test
//    public void testUpdateProfileNotFound() {
//        // Prepare test data
//        long userId = 1L;
//        Profile updatedProfile = new Profile();
//
//        // Mock profileRepository behavior
//        when(profileRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // Call the service method
//        ResponseEntity<?> responseEntity = profileService.updateProfile(userId, updatedProfile);
//
//        // Assertions
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        Object responseBody = responseEntity.getBody();
//        assertTrue(responseBody instanceof ResponseHandler);
//
//        ResponseHandler<?> responseHandler = (ResponseHandler<?>) responseBody;
//
//        assertEquals("FAILED", responseHandler.getStatus().getType());
//        assertEquals("Profile not found with ID: " + userId, responseHandler.getStatus().getMessage());
//
//        // Verify that profileRepository.save was not called
//        verify(profileRepository, never()).save(any(Profile.class));
//    }
}
