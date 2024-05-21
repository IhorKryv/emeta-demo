package com.emetaplus.workplace.userworkplace.service;

import com.beust.ah.A;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.email.model.EmailRequest;
import com.emetaplus.workplace.email.service.EmailService;
import com.emetaplus.workplace.user.model.Provider;
import com.emetaplus.workplace.user.model.User;
import com.emetaplus.workplace.user.repository.UserRepository;
import com.emetaplus.workplace.userworkplace.dto.WorkplaceAdminDataDTO;
import com.emetaplus.workplace.userworkplace.model.UserWorkplace;
import com.emetaplus.workplace.userworkplace.repository.UserWorkplaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserWorkplaceService {

    private final UserWorkplaceRepository userWorkplaceRepository;
    private final UserRepository userRepository;
    private final MinioService minioService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AdminWorkplaceService adminWorkplaceService;

    @Transactional
    public User createUserWorkplace(User user) {
        UserWorkplace workplace = new UserWorkplace();
        workplace.setWorkplaceId(UUID.randomUUID());
        workplace = userWorkplaceRepository.save(workplace);
        userWorkplaceRepository.save(workplace);
        user.setWorkplaceId(workplace.getId());
        WorkplaceAdminDataDTO dto = new WorkplaceAdminDataDTO();
        dto.setWorkplaceId(workplace.getWorkplaceId());
        dto.setEmail(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        adminWorkplaceService.createWorkplaceOnAdmin(workplace, dto);
        minioService.addWorkplaceDefaultFolders(workplace.getWorkplaceId().toString());
        return userRepository.save(user);
    }

    @Transactional
    public void createWorkplaceFromAdmin(WorkplaceAdminDataDTO dto) {
        UserWorkplace workplace = new UserWorkplace();
        User user = new User();
        workplace.setWorkplaceId(dto.getWorkplaceId());
        workplace = userWorkplaceRepository.save(workplace);
        user.setUsername(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setProvider(Provider.LOCAL);
        user.setCreatedAt(LocalDateTime.now());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getTemporaryPassword()));
        user.setTemporaryPassword(true);
        user.setWorkplaceId(workplace.getId());
        minioService.addWorkplaceDefaultFolders(workplace.getWorkplaceId().toString());
        userRepository.save(user);
        sendNotificationAboutCreatedWorkplace(user.getFirstName(), user.getUsername(), dto.getTemporaryPassword());
    }

    public void sendNotificationAboutCreatedWorkplace(String firstName, String username, String password) {
        Map<String, Object> data = Map.of(
                "firstName", firstName,
                "username", username,
                "password", password,
                "workplaceLoginUrl", "https://workplace.emetaplus.com/uk/auth/login"
        );

        String subject = "EMeta workplace has been created for you by administration";
        String message = emailService.getContentFromTemplate(data, "workplace-created-by-admin-template");
        EmailRequest emailRequest = new EmailRequest(
                "no-reply@emetaplus.com",
                username,
                subject,
                message
        );
        emailService.sendEmail(emailRequest);
    }

    public UserWorkplace getWorkplace(UUID id) {
        return getWorkplaceById(id);
    }

    private UserWorkplace getWorkplaceById(UUID id) {
        return userWorkplaceRepository.findById(id).orElseGet(() -> {
            log.error("[User Workplace Exception]: User Workplace with id = {} not found", id);
            throw ExceptionHelper.getNotFoundException(UserWorkplace.class);
        });
    }

}
