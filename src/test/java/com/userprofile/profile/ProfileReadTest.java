package com.userprofile.profile;

import com.userprofile.profile.model.entity.Profile;
import com.userprofile.profile.model.response.ResponseHandler;
import com.userprofile.profile.repository.ProfileRepository;
import com.userprofile.profile.service.ProfileService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileReadTest {

    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final ResponseHandler<Profile> responseHandler = new ResponseHandler<>();
    private final ProfileService profileService = new ProfileService(profileRepository, responseHandler);


    @Test
    public void testGetAllProfiles() {
        // Prepare test data
        Profile profile1 = new Profile();
        profile1.setUserId(1L);
        profile1.setEmail("njwasshi@gmail.com");
        profile1.setFirstName("Ain");
        profile1.setLastName("Najwa");
        profile1.setDateOfBirth(LocalDate.parse("1999-07-05"));
        profile1.setAge(24);

        Profile profile2 = new Profile();
        profile2.setUserId(2L);
        profile2.setEmail("tiara@gmail.com");
        profile2.setFirstName("Tiara");
        profile2.setLastName("Andini");
        profile2.setDateOfBirth(LocalDate.parse("1999-07-05"));
        profile2.setAge(24);

        List<Profile> profiles = Arrays.asList(profile1, profile2);

        when(profileRepository.findAll()).thenReturn(profiles);

        List<Profile> result = profileService.getAllProfiles();

        assertEquals(profiles.size(), result.size());
        assertTrue(result.containsAll(profiles));
    }

    @Test
    public void testGetProfileById() {
        // Prepare test data
        long id = 1;
        Profile profile = new Profile();
        profile.setUserId(id);
        profile.setEmail("njwasshi@gmail.com");
        profile.setFirstName("Ain");
        profile.setLastName("Najwa");
        profile.setDateOfBirth(LocalDate.parse("1999-07-05"));
        profile.setAge(24);

        // Mock profileRepository behavior
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        // Call the service method
        Profile result = profileService.getProfileById(id);

        // Assertions
        assertNotNull(result);
        assertEquals(profile.getUserId(), result.getUserId());
        assertEquals(profile.getEmail(), result.getEmail());
        assertEquals(profile.getFirstName(), result.getFirstName());
        assertEquals(profile.getLastName(), result.getLastName());
        assertEquals(profile.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(profile.getAge(), result.getAge());
    }
}
