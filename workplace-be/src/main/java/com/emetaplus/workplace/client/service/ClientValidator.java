package com.emetaplus.workplace.client.service;

import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientValidator {

    public void validateClientForCreate(Client client) {
        validateClientFirstName(client.getFirstName(), "create");
    }

    public void validateClientForUpdate(Client client) {
        validateClientFirstName(client.getFirstName(), "update");
    }

    private void validateClientFirstName(String firstName, String method) {
        if (StringUtils.isBlank(firstName)) {
            log.error("[Client Exception]: Can't {} a client without first name", method);
            throw ExceptionHelper.getInvalidFieldException(Client.class, "first-name");
        }
    }
}
