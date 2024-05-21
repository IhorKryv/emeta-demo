package com.emetaplus.workplace.client.controller;

import com.emetaplus.workplace.client.dto.ClientDTO;
import com.emetaplus.workplace.client.mapper.ClientMapper;
import com.emetaplus.workplace.client.service.ClientService;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    private static final String CREATE_CLIENT_PATH = "create";
    private static final String UPDATE_CLIENT_PATH = "update/{id}";
    private static final String GET_CLIENT_PATH = "get/{id}";
    private static final String GET_ALL_CLIENTS_PATH = "get/all";

    private static final String GET_ALL_FOR_SELECTIONS_PATH = "get/all/selection";
    private static final String DELETE_CLIENT_PATH = "delete/{id}";

    @PostMapping(CREATE_CLIENT_PATH)
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        return clientMapper.toDTO(clientService.createClient(clientMapper.toEntity(clientDTO)));
    }

    @PutMapping(UPDATE_CLIENT_PATH)
    public ClientDTO updateClient(@PathVariable UUID id, @RequestBody ClientDTO clientDTO) {
        return clientMapper.toDTO(clientService.updateClient(id, clientMapper.toEntity(clientDTO)));
    }

    @GetMapping(GET_CLIENT_PATH)
    public ClientDTO getClient(@PathVariable UUID id) {
        return clientMapper.toDTO(clientService.getClient(id));
    }

    @GetMapping(GET_ALL_CLIENTS_PATH)
    public PageDTO<ClientDTO> getAllClients(PageableRequestQuery pageableRequestQuery) {
        return clientMapper.toClientsPageDTO(clientService.getAllClientsForCurrentUser(pageableRequestQuery));
    }

    @GetMapping(GET_ALL_FOR_SELECTIONS_PATH)
    public Set<ClientDTO> getAllClientsForSelection() {
        return clientMapper.toClientDTOSet(clientService.getAllExistingClientsForCurrentWorkplace());
    }

    @DeleteMapping(DELETE_CLIENT_PATH)
    public void deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
    }
}
