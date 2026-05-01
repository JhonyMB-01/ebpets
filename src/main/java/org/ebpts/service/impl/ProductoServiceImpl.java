package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ebpts.dto.ProductoDto;
import org.ebpts.mapper.ProductoMapper;
import org.ebpts.repository.ProductoRepository;
import org.ebpts.service.ProductoService;

import java.util.List;

@ApplicationScoped
public class ProductoServiceImpl implements ProductoService {

    @Inject
    ProductoRepository productoRepository;

    @Inject
    ProductoMapper productoMapper;

    @Override
    public List<ProductoDto> getAllProductos() {
        return productoRepository.listAll()
                .stream()
                .map(productoMapper::toDTO)
                .toList();
    }

}
