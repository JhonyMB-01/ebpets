package org.ebpts.service;

import org.ebpts.dto.request.MarcaRequestDTO;
import org.ebpts.dto.response.MarcaResponseDTO;

import java.util.List;

public interface MarcaService {

    List<MarcaResponseDTO> getAllMarca();

    MarcaResponseDTO saveMarca(MarcaRequestDTO requestDTO);

    MarcaResponseDTO updateMarca(Long id, MarcaRequestDTO marcaRequestDTO);

    void deleteMarca(Long id);
}
