package com.emetaplus.workplace.client.service;

import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.client.repository.ClientRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import com.emetaplus.workplace.core.utils.SpecificationUtils;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;
    private final MinioService minioService;

    public Client createClient(Client client) {
        clientValidator.validateClientForCreate(client);
        client.setWorkplaceId(getCurrentUserWorkplaceId());
        client = clientRepository.save(client);
        minioService.addClientFolder(client.getId());
        return client;
    }

    public Client updateClient(UUID id, Client client) {
        clientValidator.validateClientForUpdate(client);
        Client existingClient = getClientById(id);
        isUserHasAccessToThisWorkplace(existingClient.getWorkplaceId());
        client.setId(existingClient.getId());
        client.setWorkplaceId(existingClient.getWorkplaceId());
        return clientRepository.save(client);
    }

    public Client getClient(UUID id) {
        Client client = getClientById(id);
        isUserHasAccessToThisWorkplace(client.getWorkplaceId());
        return client;
    }

    public Page<Client> getAllClientsForCurrentUser(PageableRequestQuery pageableRequestQuery) {
        UUID workplaceId = getCurrentUserWorkplaceId();
        Specification<Client> specification = getSpecification(workplaceId, pageableRequestQuery.getSearchText());
        return clientRepository.findAll(specification, pageableRequestQuery.getPageable());
    }

    public Set<Client> getAllExistingClientsForCurrentWorkplace() {
        return clientRepository.findAllByWorkplaceId(UserUtils.getCurrentUser().getWorkplaceId());
    }


    public void deleteClient(UUID id) {
        Client existingClient = getClientById(id);
        isUserHasAccessToThisWorkplace(existingClient.getWorkplaceId());
        //TODO: Check is client on active session or not?
        clientRepository.delete(existingClient);

    }

    private Client getClientById(UUID id) {
        return clientRepository.findById(id).orElseGet(() -> {
            log.error("[Client Exception]: Client with id={} not found", id);
            throw ExceptionHelper.getNotFoundException(Client.class);
        });
    }

    private UUID getCurrentUserWorkplaceId() {
        return UserUtils.getCurrentUser().getWorkplaceId();
    }

    private void isUserHasAccessToThisWorkplace(UUID workplaceId) {
        if (!Objects.equals(workplaceId, getCurrentUserWorkplaceId())) {
            throw ExceptionHelper.getUserHasNoAccessException(User.class);
        }
    }

    private Specification<Client> getSpecification(UUID workplaceId, String searchText) {
        Specification<Client> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Client.Fields.workplaceId), workplaceId);
        specification.and(
                SpecificationUtils.getSearchSpecification(searchText, root ->
                        Arrays.asList(
                                root.get(Client.Fields.firstName),
                                root.get(Client.Fields.lastName),
                                root.get(Client.Fields.email),
                                root.get(Client.Fields.phone)
                        )
                )
        );
        return specification;
    }
}
