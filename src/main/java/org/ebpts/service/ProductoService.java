package org.ebpts.service;

import org.ebpts.dto.request.ProductoRequestDTO;
import org.ebpts.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    List<ProductoResponseDTO> getAllProductos();

    ProductoResponseDTO saveProducto(ProductoRequestDTO requestDTO);

    ProductoResponseDTO updateProducto(Long id, ProductoRequestDTO requestDTO);

    ProductoResponseDTO getProductoById(Long id);

    void updateEstado(Long id, Boolean activo);

    List<ProductoResponseDTO> getProductosConStock();
}
