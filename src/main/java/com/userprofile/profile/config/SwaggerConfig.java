package com.userprofile.profile.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

//@Configuration
//@SecurityScheme(
//        name = "bearerAuth",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
@OpenAPIDefinition(
        info = @Info(
                title = "Profile Microservice",
                version = "1.0.0",
                description = "This is Profile Microservice for Activity 1"
        ),
        security = { @SecurityRequirement(name = "bearerAuth")}
)
public class SwaggerConfig {


}
