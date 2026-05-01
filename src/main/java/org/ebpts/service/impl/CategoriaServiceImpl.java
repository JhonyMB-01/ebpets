package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.ebpts.dto.request.CategoriaRequestDTO;
import org.ebpts.dto.response.CategoriaResponseDTO;
import org.ebpts.entity.CategoriaEntity;
import org.ebpts.mapper.CategoriaMapper;
import org.ebpts.repository.CategoriaRepository;
import org.ebpts.service.CategoriaService;

import java.util.List;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository repository;

    @Inject
    CategoriaMapper mapper;

    @Override
    public List<CategoriaResponseDTO> getAllCategoria() {
        return mapper.toDTOList(repository.listAll());
    }

    @Transactional
    @Override
    public CategoriaResponseDTO createCategoria(CategoriaRequestDTO request) {
        CategoriaEntity categoria = mapper.toEntity(request);
        repository.persist(categoria);
        return mapper.toDTO(categoria);

    }

    @Transactional
    @Override
    public CategoriaResponseDTO updateCategory(Long id, CategoriaRequestDTO request) {
        CategoriaEntity categoria = repository.findById(id);
        if (categoria == null) {
            throw new NotFoundException("Categoría no encontrada");
        }
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());

        return mapper.toDTO(categoria);

    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Categoría no encontrada");
        }
    }

}
