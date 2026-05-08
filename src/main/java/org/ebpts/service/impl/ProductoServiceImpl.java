package org.ebpts.service.impl;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.ebpts.dto.request.ProductoRequestDTO;
import org.ebpts.dto.response.ProductoResponseDTO;
import org.ebpts.entity.CategoriaEntity;
import org.ebpts.entity.MarcaEntity;
import org.ebpts.entity.ProductoEntity;
import org.ebpts.mapper.ProductoMapper;
import org.ebpts.repository.CategoriaRepository;
import org.ebpts.repository.MarcaRepository;
import org.ebpts.repository.ProductoRepository;
import org.ebpts.service.ProductoService;

import java.util.List;

@ApplicationScoped
public class ProductoServiceImpl implements ProductoService {

    @Inject
    ProductoRepository productoRepository;
    @Inject
    CategoriaRepository categoriaRepository;
    @Inject
    MarcaRepository marcaRepository;

    @Inject
    ProductoMapper productoMapper;

    @Override
    public List<ProductoResponseDTO> getAllProductos() {
        return productoMapper.toDTOList(productoRepository
                .find("activo = true").list());
    }

    @Override
    @Transactional
    public ProductoResponseDTO saveProducto(ProductoRequestDTO dto) {

        if (productoRepository.existsByCodigo(dto.getCodigo())) {
            throw new IllegalArgumentException("El código del producto ya existe");
        }

        CategoriaEntity categoria = categoriaRepository.findById(dto.getIdCategoria());
        if (categoria == null) {
            throw new NotFoundException("Categoría no encontrada");
        }

        MarcaEntity marca = marcaRepository.findById(dto.getIdMarca());
        if (marca == null) {
            throw new NotFoundException("Marca no encontrada");
        }

        ProductoEntity producto = new ProductoEntity();
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setAfectaIgv(dto.getAfectaIgv() != null ? dto.getAfectaIgv() : true);
        producto.setActivo(true);
        PanacheEntityBase.persist(producto);

        return productoMapper.toDTO(producto);

    }

    @Override
    @Transactional
    public ProductoResponseDTO updateProducto(Long id, ProductoRequestDTO dto) {

        ProductoEntity producto = productoRepository.findById(id);
        if (producto == null) {
            throw new NotFoundException("Producto no encontrado");
        }

        CategoriaEntity categoria = categoriaRepository
                .findById(dto.getIdCategoria());
        MarcaEntity marca = marcaRepository.findById(dto.getIdMarca());

        if (categoria == null || marca == null) {
            throw new NotFoundException("Categoría o marca inválida");
        }

        producto.setNombre(dto.getNombre());
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setAfectaIgv(dto.getAfectaIgv());


        return productoMapper.toDTO(producto);

    }

    @Override
    public ProductoResponseDTO getProductoById(Long id) {
        ProductoEntity producto = productoRepository.findById(id);
        if (producto == null) {
            throw new NotFoundException("Producto no encontrado");
        }
        return productoMapper.toDTO(producto);
    }

    @Override
    @Transactional
    public void updateEstado(Long id, Boolean activo) {

        ProductoEntity producto = productoRepository.findById(id);
        if (producto == null) {
            throw new NotFoundException("Producto no encontrado");
        }

        producto.setActivo(activo);


    }

}
