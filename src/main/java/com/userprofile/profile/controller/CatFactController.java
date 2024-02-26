package com.userprofile.profile.controller;

import com.userprofile.profile.model.response.ResponseHandler;
import com.userprofile.profile.service.CatFactService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CatFactController {

    ResponseHandler<String> responseHandler = new ResponseHandler<>();
    private CatFactService catFactService;

    @GetMapping("/catFacts")
    private ResponseEntity<ResponseHandler> getCatFacts() throws Exception {
        return ResponseEntity.ok(responseHandler.generateSuccessResponse(catFactService.getCatFacts())) ;
    }
}
