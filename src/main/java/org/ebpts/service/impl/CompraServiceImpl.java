package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.ebpts.dto.ItemCompraDTO;
import org.ebpts.dto.request.CompraRequestDTO;
import org.ebpts.dto.response.CompraDetalleResponseDTO;
import org.ebpts.dto.response.CompraResponseDTO;
import org.ebpts.entity.CompraEntity;
import org.ebpts.entity.DetalleCompraEntity;
import org.ebpts.entity.ProductoEntity;
import org.ebpts.entity.ProveedorEntity;
import org.ebpts.mapper.CompraMapper;
import org.ebpts.mapper.DetalleCompraMapper;
import org.ebpts.repository.CompraRepository;
import org.ebpts.repository.DetalleCompraRepository;
import org.ebpts.repository.ProductoRepository;
import org.ebpts.repository.ProveedorRepository;
import org.ebpts.service.CompraService;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {


    @Inject
    CompraRepository compraRepository;
    @Inject
    DetalleCompraRepository detalleCompraRepository;
    @Inject
    ProveedorRepository proveedorRepository;
    @Inject
    ProductoRepository productoRepository;
    @Inject
    CompraMapper compraMapper ;


    @Override
    @Transactional
    public CompraResponseDTO crearCompra(CompraRequestDTO request) {

        ProveedorEntity proveedor = proveedorRepository.findById(request.getIdProveedor());
        if (proveedor == null) {
            throw new NotFoundException("Proveedor no encontrado");
        }

        CompraEntity compra = new CompraEntity();
        compra.proveedor = proveedor;
        compra.total = BigDecimal.ZERO;
        compra.persist();

        BigDecimal total = BigDecimal.ZERO;

        for (ItemCompraDTO item : request.getItems()) {

            ProductoEntity producto = productoRepository.findById(item.getIdProducto());
            if (producto == null) {
                throw new NotFoundException("Producto no encontrado");
            }

            BigDecimal subtotal = item.getPrecioCompra()
                    .multiply(BigDecimal.valueOf(item.getCantidad()));

            total = total.add(subtotal);

            detalleCompraRepository.persist(DetalleCompraEntity.builder()
                    .compra(compra)
                    .producto(producto)
                    .cantidad(item.getCantidad())
                    .precioCompra(item.getPrecioCompra())
                    .lote(item.getLote())
                    .fechaVencimiento(item.getFechaVencimiento())
                    .build());
        }

        compra.total = total;

        return compraMapper.toDTO(compra);

    }

    @Override
    public List<CompraResponseDTO> getAllCompras() {

        List<CompraEntity> compras = compraRepository.listAll();

        return compras.stream()
                .map(compraMapper::toDTO)
                .toList();

        //return mapper.toDTOList(compraRepository.listAll());
    }

    @Override
    public CompraDetalleResponseDTO getByIdCompra(Long id) {

        CompraEntity compra = compraRepository.findById(id);

        if (compra == null) {
            throw new NotFoundException("Compra no encontrada");
        }


        return compraMapper.toDetailResponse(compra);
    }
}
