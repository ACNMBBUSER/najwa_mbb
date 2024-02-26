package com.userprofile.profile;

import com.userprofile.profile.model.entity.Profile;
import com.userprofile.profile.model.response.ResponseHandler;
import com.userprofile.profile.repository.ProfileRepository;
import com.userprofile.profile.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProfileServiceCreateTest {

    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final ResponseHandler<Profile> responseHandler = new ResponseHandler<>();
    private final ProfileService profileService = new ProfileService(profileRepository, responseHandler);

    private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile();
        profile.setEmail("anggi@gmail.com");
        profile.setFirstName("Anggi");
        profile.setLastName("Marito");
        profile.setDateOfBirth(LocalDate.parse("1995-08-25"));
    }

    @Test
    public void testCreateProfileSuccess() {

        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        ResponseEntity<?> responseEntity = profileService.createProfile(profile);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Extract the response body and check if it's an instance of ResponseHandler
        Object responseBody = responseEntity.getBody();
        assertTrue(responseBody instanceof ResponseHandler);

        // Cast the response body to ResponseHandler
        ResponseHandler<?> responseHandler = (ResponseHandler<?>) responseBody;
        assertEquals("OK", responseHandler.getStatus().getType());

        // Check if the response message matches
        assertEquals("Profile Creation Success", responseHandler.getResponse());
    }

    @Test
    public void testCalculateAge() {
        LocalDate dateOfBirth = LocalDate.parse("1995-08-25");

        // Call the calculateAge method
        int calculatedAge = profileService.calculateAge(dateOfBirth);
        assertEquals(28, calculatedAge);
    }

    @Test
    public void testCreateProfileFailureEmail() {

        profile.setEmail(null); // Simulate a null email, triggering failure

        ResponseEntity<?> responseEntity = profileService.createProfile(profile);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Object responseBody = responseEntity.getBody();
        assertTrue(responseBody instanceof ResponseHandler);

        ResponseHandler<?> responseHandler = (ResponseHandler<?>) responseBody;

        assertEquals("FAILED", responseHandler.getStatus().getType());

        String expectedErrorMessage = "Email must not be null";
        assertTrue(responseHandler.getStatus().getErrors().contains(expectedErrorMessage));
    }

    @Test
    public void testCreateProfileFailedFName() {

        profile.setFirstName(null); // Simulate a null First Name, triggering failure

        ResponseEntity<?> responseEntity = profileService.createProfile(profile);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Object responseBody = responseEntity.getBody();
        assertTrue(responseBody instanceof ResponseHandler);

        ResponseHandler<?> responseHandler = (ResponseHandler<?>) responseBody;
        assertEquals("FAILED", responseHandler.getStatus().getType());

        String expectedErrorMessage = "First Name must not be null";
        assertTrue(responseHandler.getStatus().getErrors().contains(expectedErrorMessage));
    }

    @Test
    public void testCreateProfileFailedLName() {

        profile.setLastName(null); // Simulate a null Last Name, triggering failure

        ResponseEntity<?> responseEntity = profileService.createProfile(profile);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Object responseBody = responseEntity.getBody();
        assertTrue(responseBody instanceof ResponseHandler);

        ResponseHandler<?> responseHandler = (ResponseHandler<?>) responseBody;
        assertEquals("FAILED", responseHandler.getStatus().getType());

        String expectedErrorMessage = "Last Name must not be null";
        assertTrue(responseHandler.getStatus().getErrors().contains(expectedErrorMessage));
    }

    @Test
    public void testCreateProfileFailedDOB() {

        profile.setDateOfBirth(null); // Simulate a null DOB, triggering failure

        ResponseEntity<?> responseEntity = profileService.createProfile(profile);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Object responseBody = responseEntity.getBody();
        assertTrue(responseBody instanceof ResponseHandler);

        ResponseHandler<?> responseHandler = (ResponseHandler<?>) responseBody;
        assertEquals("FAILED", responseHandler.getStatus().getType());

        String expectedErrorMessage = "DOB must not be null";
        assertTrue(responseHandler.getStatus().getErrors().contains(expectedErrorMessage));
    }

}

