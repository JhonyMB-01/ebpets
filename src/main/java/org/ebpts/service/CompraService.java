package org.ebpts.service;

import org.ebpts.dto.request.CompraRequestDTO;
import org.ebpts.dto.response.CompraResponseDTO;

import java.util.List;

public interface CompraService {

    CompraResponseDTO crearCompra(CompraRequestDTO request);

    List<CompraResponseDTO> getAllCompras();

    CompraResponseDTO getByIdCompra(Long id);
}
