package com.example.labOdc.Controller;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.AuthenticationRequest;
import com.example.labOdc.DTO.IntrospectRequest;
import com.example.labOdc.DTO.Response.AutheticationResponse;
import com.example.labOdc.DTO.Response.IntrospectResponse;
import com.example.labOdc.Service.Implement.AuthenticationSvc;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationSvc authenticationSvc;

    @PostMapping("/token")
    ApiResponse<AutheticationResponse> authenticated(@RequestBody AuthenticationRequest request) {
        AutheticationResponse result = authenticationSvc.Authenticate(request);

        return ApiResponse.success(result, "thanh cong", HttpStatus.OK);
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticated(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse result = authenticationSvc.introspect(request);

        return ApiResponse.success(result, "thanh cong", HttpStatus.OK);
    }
}
