package com.emetaplus.admin.auth.controller;

import com.emetaplus.admin.auth.dto.LoginRequestDto;
import com.emetaplus.admin.auth.dto.LoginResponseDto;
import com.emetaplus.admin.auth.dto.QrCodeDto;
import com.emetaplus.admin.auth.dto.SignInResponseDto;
import com.emetaplus.admin.auth.dto.VerificationRequestDto;
import com.emetaplus.admin.security.jwt.CookieUtils;
import com.emetaplus.admin.security.jwt.JwtProvider;
import com.emetaplus.admin.security.user.DefaultSuperUser;
import com.emetaplus.admin.user.model.User;
import com.emetaplus.admin.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    private static final String SIGN_IN_PATH = "login";
    private static final String SIGN_IN_CONFIRM_PATH = "login-confirm";
    private static final String GENERATE_CODE_PATH = "generate-code";
    private static final String VALIDATE_TOKEN_PATH = "validate";

    private final JwtProvider jwtProvider;
    private final DefaultSuperUser defaultSuperUser;
    private final CookieUtils cookieUtils;
    private final UserService userService;

    public AuthController(JwtProvider jwtProvider, UserService userService,
                          DefaultSuperUser defaultSuperUser, CookieUtils cookieUtils) {
        this.jwtProvider = jwtProvider;
        this.defaultSuperUser = defaultSuperUser;
        this.cookieUtils = cookieUtils;
        this.userService = userService;
    }

    @PostMapping(VALIDATE_TOKEN_PATH)
    public ResponseEntity<Boolean> validateToken(@RequestBody String token) {
        if (StringUtils.isBlank(token)) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        Boolean isValid = jwtProvider.validateToken(token);
        return new ResponseEntity<>(isValid, HttpStatus.OK);

    }

    @PostMapping(SIGN_IN_PATH)
    public SignInResponseDto singIn(@Valid @RequestBody LoginRequestDto requestDto) {
        User user = userService.getUserByUsernameAndPassword(requestDto.getUsername(), requestDto.getPassword());
        SignInResponseDto signInResponseDto = new SignInResponseDto();
        signInResponseDto.setFirstLogin(user.isFirstLogin());
        return signInResponseDto;

    }

    @PostMapping(GENERATE_CODE_PATH)
    public ResponseEntity<QrCodeDto> generateQrCode(@Valid @RequestBody LoginRequestDto requestDto) {
        User user = userService.getUserByUsernameAndPassword(requestDto.getUsername(), requestDto.getPassword());
        log.debug("Creating QrCode");
        QrCodeDto qrCodeDto = new QrCodeDto();
        qrCodeDto.setQrCodeUrl(userService.createQrCode(user.getUsername()));
        return new ResponseEntity<>(qrCodeDto, HttpStatus.OK);
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

    @PostMapping(SIGN_IN_CONFIRM_PATH)
    public ResponseEntity<LoginResponseDto> verifyAccount(@RequestBody VerificationRequestDto dto) {
        User user = userService.getValidUserByUsernameAndPasswordAndCode(
                dto.getUsername(), dto.getPassword(), dto.getCode()
        );
        return buildResponseEntity(user);
    }

    //TODO: Just for swagger fast auth when no UI for auth to receive token
    @Profile("dev")
    @PostMapping("swagger/sign-in")
    public String superUserLogin() {
        return buildResponseEntity(defaultSuperUser.getSuperUserData("superuser@localhost.com")).getBody().getToken();
    }
}
