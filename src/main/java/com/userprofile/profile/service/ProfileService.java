package com.userprofile.profile.service;

import com.userprofile.profile.model.Status;
import com.userprofile.profile.model.entity.Profile;
import com.userprofile.profile.model.response.ResponseHandler;
import com.userprofile.profile.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ResponseEntity<?> createProfile(Profile profile) {
        ResponseHandler<Profile> responseHandler = new ResponseHandler<>();
        Status status = new Status();

        if (profile.getEmail() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Email must not be null"));
        } else if (profile.getFirstName() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("First Name must not be null"));
        } else if (profile.getLastName() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("First Name must not be null"));
        } else if (profile.getDateOfBirth() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("DOB must not be null"));
        } else {
            profileRepository.save(profile);
            return ResponseEntity.ok(responseHandler.generateSuccessResponse("Profile Creation Success"));
        }
    }
}
