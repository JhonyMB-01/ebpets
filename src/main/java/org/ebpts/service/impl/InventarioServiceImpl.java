package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ebpts.dto.response.InventarioResponseDTO;
import org.ebpts.dto.response.ValidacionInventarioDTO;
import org.ebpts.entity.InventarioEntity;
import org.ebpts.exception.NotFoundException;
import org.ebpts.mapper.InventarioMapper;
import org.ebpts.repository.InventarioRepository;
import org.ebpts.service.InventarioService;

import java.util.List;

@ApplicationScoped
public class InventarioServiceImpl implements InventarioService {

    @Inject
    InventarioRepository repository;

    @Inject
    InventarioMapper mapper;


    @Override
    public List<InventarioResponseDTO> obtenerPorProducto(Long idProducto) {

        List<InventarioEntity> inventarios =
                repository.findByProducto(idProducto);

        if (inventarios.isEmpty()) {
            throw new NotFoundException("No hay inventario para este producto");
        }

        return mapper.toDTOList(inventarios);

    }

    @Override
    public List<InventarioResponseDTO> listarTodos() {

        List<InventarioEntity> list = repository.findAllOrdenado();

        return mapper.toDTOList(list);

    }

    @Override
    public InventarioResponseDTO obtenerPorId(Long id) {

        InventarioEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Inventario no encontrado");
        }

        return mapper.toDTO(entity);

    }

    @Override
    public ValidacionInventarioDTO validarStokById(Long id, Integer cantidad) {

        InventarioEntity inv = repository.findById(id);

        if (inv == null) {
            throw new NotFoundException("Inventario no encontrado");
        }

        ValidacionInventarioDTO res = new ValidacionInventarioDTO();

        res.setStockDisponible(inv.getStock());
        res.setValido(inv.getStock() >= cantidad);

        return res;

    }
}
