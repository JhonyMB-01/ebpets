package org.ebpts.service;

import org.ebpts.dto.request.ClienteRequestDTO;
import org.ebpts.dto.response.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    List<ClienteResponseDTO> getAllCliente();

    ClienteResponseDTO saveCliente(ClienteRequestDTO requestDTO);

    ClienteResponseDTO updateCliente(Long idCliente, ClienteRequestDTO requestDTO);

    ClienteResponseDTO getClienteById(Long idCliente);

    void deleteCliente(Long idCliente);
}
