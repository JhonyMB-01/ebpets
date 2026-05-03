package org.ebpts.service;

import org.ebpts.dto.request.ProveedorRequestDTO;
import org.ebpts.dto.response.ProveedorResponseDTO;

import java.util.List;

public interface ProveedorService {

    List<ProveedorResponseDTO> getAllProveedor();

    ProveedorResponseDTO saveProveedor(ProveedorRequestDTO requestDTO);

    ProveedorResponseDTO updateProveedor(Long idProveedor, ProveedorRequestDTO requestDTO);

    void deleteProveedor(Long idProveedor);
}
