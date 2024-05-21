package com.emetaplus.admin.workplace.service;

import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.core.query.OrderAttribute;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.core.utils.SpecificationUtils;
import com.emetaplus.admin.plans.model.Plan;
import com.emetaplus.admin.plans.model.PlanDuration;
import com.emetaplus.admin.plans.service.PlanLogService;
import com.emetaplus.admin.workplace.dto.WorkplaceExportDTO;
import com.emetaplus.admin.workplace.model.Workplace;
import com.emetaplus.admin.workplace.model.WorkplaceStatus;
import com.emetaplus.admin.workplace.repository.WorkplaceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceRepository workplaceRepository;
    private final WorkplaceValidators workplaceValidators;
    private final PlanLogService planLogService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${workplace.host}")
    private String workplaceHost;
    @Value("${workplace.auth.public}")
    private String publicKey;
    @Value("${workplace.auth.private}")
    private String privateKey;
    private static String TOKEN = "";

    @Override
    public Page<Workplace> getAll(PageableRequestQuery requestQuery) {
        requestQuery.setOrder(Map.of(
                1, new OrderAttribute(false, Workplace.Fields.createdDate)
        ));
        Specification<Workplace> specification = getSpecification(requestQuery.getSearchText());
        return workplaceRepository.findAll(specification, requestQuery.getPageable());
    }

    @Override
    public Workplace getWorkplace(UUID id) {
        return workplaceRepository.findById(id).orElseThrow(() -> ExceptionHelper.getNotFoundException(Workplace.class));
    }

    @Override
    public Workplace createNew(Workplace workplace) {
        workplaceValidators.validateWorkplaceCreation(workplace);
        workplace.setCreatedDate(LocalDateTime.now());
        workplace.setStatus(WorkplaceStatus.NEW);
        workplace.setWorkplaceId(UUID.randomUUID());
        workplace = workplaceRepository.save(workplace);
        createWorkplaceOnWorkplaceService(workplace);
        return workplace;

    }

    @Override
    public Workplace createTemporary(Workplace workplace) {
        workplaceValidators.validateWorkplaceCreation(workplace);
        workplace.setCreatedDate(LocalDateTime.now());
        workplace.setStatus(WorkplaceStatus.TEMPORARY);
        workplace.setExpiredAt(LocalDateTime.now().plusDays(3));
        return workplaceRepository.save(workplace);
    }

    @Override
    public Workplace updateUserData(UUID id, Workplace newData) {
        Workplace workplace = getWorkplace(id);
        workplace.setFirstName(newData.getFirstName());
        workplace.setLastName(newData.getLastName());
        workplace.setEmail(newData.getEmail());
        workplaceValidators.validateWorkplaceDataUpdate(workplace);
        return workplaceRepository.save(workplace);
    }

    @Override
    public Workplace updatePaymentPlan(UUID id, Plan plan) {
        Workplace workplace = getWorkplace(id);
        String prevPlan = Objects.isNull(workplace.getPlan()) ? "N/A" : workplace.getPlan().getName();
        LocalDateTime currentExpiration = Objects.nonNull(workplace.getPlanPaidUntil())
                ? workplace.getPlanPaidUntil()
                : LocalDateTime.now();
        workplace.setPlan(plan);
        workplace.setPlanPaidUntil(calculateExpirationDate(plan.getDuration(), currentExpiration));
        workplaceValidators.validateWorkplacePlanUpdate(workplace);
        planLogService.saveLogByAdmin(workplace.getEmail(), prevPlan, plan.getName());
        return workplaceRepository.save(workplace);
    }

    @Override
    public Workplace updateWorkplaceStatus(UUID id, WorkplaceStatus status) {
        Workplace workplace = getWorkplace(id);
        workplace.setStatus(status);
        return workplaceRepository.save(workplace);
    }

    @Override
    public void activate(UUID id) {
        updateStatus(id, WorkplaceStatus.ACTIVATED);
    }

    @Override
    public void delete(UUID id) {
        updateStatus(id, WorkplaceStatus.DELETED);
    }

    @Override
    public void suspend(UUID id) {
        updateStatus(id, WorkplaceStatus.SUSPENDED);
    }

    @Override
    public void permanentlyDelete(UUID id) {
        Workplace workplace = getWorkplace(id);
        if (workplace.getStatus() == WorkplaceStatus.TEMPORARY) {
            workplaceRepository.delete(workplace);
        }
    }

    private LocalDateTime calculateExpirationDate(PlanDuration planDuration, LocalDateTime currentExpiration) {
        LocalDateTime value = Objects.isNull(currentExpiration) || LocalDateTime.now().isAfter(currentExpiration)
                ? LocalDateTime.now()
                : currentExpiration;
        switch (planDuration) {
            case WEEK -> {
                return value.plusWeeks(1);
            }
            case MONTH -> {
                return value.plusMonths(1);
            }
            case THREE_MONTHS -> {
                return value.plusMonths(3);
            }
            case SIX_MONTHS -> {
                return value.plusMonths(6);
            }
            case YEAR -> {
                return value.plusYears(1);
            }
            default -> {
                return value.plusDays(1);
            }
        }
    }

    private void createWorkplaceOnWorkplaceService(Workplace workplace) {
        loginIntoWorkplaceService(false);

        WorkplaceExportDTO workplaceExportDTO = new WorkplaceExportDTO();
        workplaceExportDTO.setWorkplaceId(workplace.getWorkplaceId());
        workplaceExportDTO.setEmail(workplace.getEmail());
        workplaceExportDTO.setFirstName(workplace.getFirstName());
        workplaceExportDTO.setLastName(workplace.getLastName());
        workplaceExportDTO.setTemporaryPassword(generatePassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            String body = mapper.writeValueAsString(workplaceExportDTO);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            restTemplate.postForObject(getCreateWorkplacePath(), entity, String.class);
            System.out.println("[GENERATED TEMPORARY PASSWORD FOR CREATED WORKPLACE] = " + workplaceExportDTO.getTemporaryPassword());
        } catch (Exception e) {
            workplaceRepository.delete(workplace);
            throw ExceptionHelper.getInternalServerError();
        }

    }

    private String generatePassword() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789").toCharArray();
        return RandomStringUtils.random(
                12,
                0,
                possibleCharacters.length - 1,
                false,
                false,
                possibleCharacters,
                new SecureRandom()
        );
    }

    private void loginIntoWorkplaceService(boolean expired) {
        if (!expired && StringUtils.isNotBlank(TOKEN)) {
            return;
        }
        HttpHeaders loginHeaders = new HttpHeaders();
        HttpEntity<String> loginEntity = new HttpEntity<>("body", loginHeaders);
        loginHeaders.add("public-key", publicKey);
        loginHeaders.add("private-key", privateKey);
        final var loginResponse = restTemplate.exchange(getLoginPath(), HttpMethod.POST, loginEntity, String.class);
        if (loginResponse.getStatusCode().equals(HttpStatus.OK)) {
            setToken(loginResponse.getBody());
        }
    }

    private String getLoginPath() {
        return String.format("%sapi/export/login", workplaceHost);
    }
    private String getCreateWorkplacePath() {
        return String.format("%sapi/admin/workplace/create", workplaceHost);
    }

    private static void setToken(String token) {
        TOKEN = "Bearer " + token;
    }

    private void updateStatus(UUID id, WorkplaceStatus status) {
        Workplace workplace = getWorkplace(id);
        workplace.setStatus(status);
        workplaceRepository.save(workplace);
    }

    private Specification<Workplace> getSpecification(String searchText) {
        return SpecificationUtils.getSearchSpecification(searchText, root ->
                Arrays.asList(root.get(Workplace.Fields.firstName), root.get(Workplace.Fields.lastName),
                        root.get(Workplace.Fields.email), root.get(Workplace.Fields.phone))
        );
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    protected void generateNewToken() {
        log.info("Creating the new token for services connections");
        loginIntoWorkplaceService(true);
    }
}
