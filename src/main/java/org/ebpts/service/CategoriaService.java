package org.ebpts.service;

import org.ebpts.dto.request.CategoriaRequestDTO;
import org.ebpts.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> getAllCategoria();

    CategoriaResponseDTO createCategoria(CategoriaRequestDTO request);

    CategoriaResponseDTO obtenerCategoriaById(Long id);

    CategoriaResponseDTO updateCategory(Long id, CategoriaRequestDTO request);

    void deleteCategory(Long id);


}
