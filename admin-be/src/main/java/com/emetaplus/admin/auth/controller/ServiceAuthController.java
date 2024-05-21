package com.emetaplus.admin.auth.controller;

import com.emetaplus.admin.auth.dto.LoginResponseDto;
import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.security.jwt.CookieUtils;
import com.emetaplus.admin.security.jwt.JwtProvider;
import com.emetaplus.admin.security.user.DefaultSuperUser;
import com.emetaplus.admin.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/export")
@RequiredArgsConstructor
public class ServiceAuthController {

    private static final String LOGIN_FROM_WORKPLACE_PATH = "login";

    private final JwtProvider jwtProvider;
    private final DefaultSuperUser defaultSuperUser;
    private final CookieUtils cookieUtils;

    @Value("${workplace.auth.public}")
    private String PUBLIC_KEY;

    @Value("${workplace.auth.private}")
    private String PRIVATE_KEY;

    @PostMapping(LOGIN_FROM_WORKPLACE_PATH)
    public String superUserLogin(@RequestHeader("public-key") String publicKey, @RequestHeader("private-key") String privateKey) {
        if (!publicKey.equals(PUBLIC_KEY) || !privateKey.equals(PRIVATE_KEY)) {
            throw ExceptionHelper.buildException(HttpStatus.UNAUTHORIZED, "You have no access for this operation");
        }
        return buildResponseEntity(defaultSuperUser.getSuperUserData("superuser@localhost.com")).getBody().getToken();
    }


    private ResponseEntity<LoginResponseDto> buildResponseEntity(User user) {
        String token = jwtProvider.generateToken(user);

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setUsername(user.getUsername());
        responseDto.setAuthorities(user.getAuthorities().stream().map(Object::toString).toList());
        responseDto.setToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookieUtils.createAccessTokenCookie(token).toString());
        return ResponseEntity.ok().headers(headers).body(responseDto);
    }
}
