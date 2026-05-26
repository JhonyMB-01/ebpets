package org.ebpts.service;

import org.ebpts.dto.request.VentaRequestDTO;
import org.ebpts.dto.response.VentaDetalleResponseDTO;
import org.ebpts.dto.response.VentaResponseDTO;

import java.util.List;

public interface VentaService {

    VentaResponseDTO saveVenta(VentaRequestDTO dto);

    List<VentaResponseDTO> getAllVenta();

    VentaResponseDTO getVentaById(Long id);

    //VentaResponseDTO registrarVentaPendiente(VentaRequestDTO dto);

    VentaResponseDTO confirmarVenta(Long idVenta);

    VentaDetalleResponseDTO obtenerDetalleVentaById(Long id);
}
