package com.userprofile.profile.service;

import com.userprofile.profile.model.entity.Profile;
import com.userprofile.profile.model.response.ResponseHandler;
import com.userprofile.profile.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    ResponseHandler<Profile> responseHandler = new ResponseHandler<>();
    public ResponseEntity<ResponseHandler> createProfile(Profile profile) {

        if (profile.getEmail() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Email must not be null"));
        } else if (profile.getFirstName() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("First Name must not be null"));
        } else if (profile.getLastName() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Last Name must not be null"));
        } else if (profile.getDateOfBirth() == null) {
            return ResponseEntity.ok(responseHandler.generateFailResponse("DOB must not be null"));
        } else {
            int age = calculateAge(profile.getDateOfBirth()); //.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            profile.setAge(age);
            profileRepository.save(profile);
            return ResponseEntity.ok(responseHandler.generateSuccessResponse("Profile Creation Success"));
        }
    }

    public List<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

    public Profile getProfileById(long id){
        Optional<Profile> optionalProfile = profileRepository.findById(id);

        return optionalProfile.orElse(null);
    }

    public ResponseEntity<ResponseHandler> updateProfile(Long userId, Profile profile) {

        Optional<Profile> optionalProfile = profileRepository.findById(userId);

        if (optionalProfile.isPresent()) {
            Profile updateProfile = optionalProfile.get();

            if (profile.getEmail() != null) {
                updateProfile.setEmail(profile.getEmail());
            }  if (profile.getFirstName() != null) {
                updateProfile.setFirstName(profile.getFirstName());
            }  if (profile.getLastName() != null) {
                updateProfile.setLastName(profile.getLastName());
            }  if (profile.getDateOfBirth() != null) {
                updateProfile.setDateOfBirth(profile.getDateOfBirth());
                int age = calculateAge(profile.getDateOfBirth());
                updateProfile.setAge(age);
            }
            log.info("EMAIL: "+ updateProfile.getEmail());
            log.info(updateProfile.getFirstName());
            log.info(updateProfile.getLastName());
                profileRepository.save(updateProfile);
                return ResponseEntity.ok(responseHandler.generateSuccessResponse("Profile Update Success"));
            //}
        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Profile not found with ID: " + userId));
        }
    }

    public ResponseEntity<?> deleteProfile(Long id){

        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isPresent()) {
            profileRepository.deleteById(id);
            return ResponseEntity.ok(responseHandler.generateSuccessResponse("Profile deletion with ID: " + id + " successfully deleted."));
        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Profile not found with ID: " + id));
        }
    }

    public int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        log.info("MASA NOW: "+ currentDate);
        log.info("UMUR : " + Period.between(dateOfBirth, currentDate).getYears());
        return Period.between(dateOfBirth, currentDate).getYears();
    }
}
