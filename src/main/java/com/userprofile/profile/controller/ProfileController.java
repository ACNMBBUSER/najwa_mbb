package com.userprofile.profile.controller;

import com.userprofile.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

//    @PostMapping("/create")
//    public ResponseEntity<?> createProfile(@RequestBody Profile profile){
//        return profileService.createProfile(profile);
//    }
}
