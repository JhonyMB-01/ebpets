package org.ebpts.service;

import org.ebpts.dto.request.CompraRequestDTO;
import org.ebpts.dto.response.CompraDetalleResponseDTO;
import org.ebpts.dto.response.CompraResponseDTO;

import java.util.List;

public interface CompraService {

    CompraResponseDTO crearCompra(CompraRequestDTO request);

    List<CompraResponseDTO> getAllCompras();

    CompraDetalleResponseDTO getByIdCompra(Long id);
}
