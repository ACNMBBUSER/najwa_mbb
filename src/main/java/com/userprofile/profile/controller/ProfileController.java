package com.userprofile.profile.controller;

import com.userprofile.profile.model.entity.Profile;
import com.userprofile.profile.model.response.ResponseHandler;
import com.userprofile.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@SecurityRequirement(name="bearerAuth")
@Tag(name="Profile API", description = "This is an API for Profile to Create, Read, Update and Delete Profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    ResponseHandler<Profile> responseHandler = new ResponseHandler<>();

    @PostMapping("/create")
    public ResponseEntity<ResponseHandler> createProfile(@RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseHandler> getThings(){
        return ResponseEntity.ok(responseHandler.generateSuccessResponse(profileService.getAllProfiles()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseHandler> fetchThingById(@PathVariable long id){
        if (profileService.getProfileById(id) != null){
            return ResponseEntity.ok(responseHandler.generateSuccessResponse(profileService.getProfileById(id)));
        }else{
            return ResponseEntity.ok(responseHandler.generateFailResponse("Profile not found with ID: " + id));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseHandler> updateProfile(@PathVariable long id, @RequestBody Profile profile){
        return profileService.updateProfile(id,profile);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable long id){
        return profileService.deleteProfile(id);
    }
}
