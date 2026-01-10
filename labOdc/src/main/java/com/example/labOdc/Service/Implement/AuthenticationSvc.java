package com.example.labOdc.Service.Implement;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.AuthenticationRequest;
import com.example.labOdc.DTO.IntrospectRequest;
import com.example.labOdc.DTO.Response.AutheticationResponse;
import com.example.labOdc.DTO.Response.IntrospectResponse;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import com.nimbusds.jose.Payload;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationSvc {
    UserRepository userRepository;
    @NonFinal // ko inject vao constructor
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    // kiem tra token con hop le khong
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiraTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiraTime.after(new Date()))
                .build();

    }

    public AutheticationResponse Authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Ko thay username"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        // matches de so sanh password
        if (!authenticated) {
            throw new RuntimeException("Ko thanh cong");
        }
        String token = generateToken(user);// goi den ham tao token
        return AutheticationResponse.builder()
                .token(token)
                .Authenticated(true)
                .build();
    }

    public String generateToken(User user) { // tao token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) // dai dien cho subject
                .issuer("dev")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("role", buildRole(user))
                .claim("Custom", "Custom")
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        // ky token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));// mac can chuoi 32bytes
            return jwsObject.serialize();
        } catch (JOSEException ex) {

            throw new RuntimeException(ex);
        }

    }

    private String buildRole(User user) {
        return user.getRole() != null ? user.getRole().name() : "";
    }
}
