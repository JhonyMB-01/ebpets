package org.ebpts.service.impl;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.ebpts.dto.ItemVentaDTO;
import org.ebpts.dto.request.VentaRequestDTO;
import org.ebpts.dto.response.VentaDetalleResponseDTO;
import org.ebpts.dto.response.VentaResponseDTO;
import org.ebpts.entity.*;
import org.ebpts.exception.NotFoundException;
import org.ebpts.mapper.VentaMapper;
import org.ebpts.repository.*;
import org.ebpts.service.VentaService;
import org.ebpts.utils.EstadoVenta;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class VentaServiceImpl implements VentaService {

    private static final BigDecimal IGV = BigDecimal.valueOf(0.18);

    @Inject
    VentaRepository ventaRepository;
    @Inject
    DetalleVentaRepository detalleRepository;
    @Inject
    ClienteRepository clienteRepository;
    @Inject
    UsuarioRepository usuarioRepository;
    @Inject
    InventarioRepository inventarioRepository;
    @Inject
    VentaMapper mapper;
    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    JsonWebToken jwt;
    @Inject
    EntityManager entityManager;


    @Override
    @Transactional
    public VentaResponseDTO saveVenta(VentaRequestDTO requestDTO) {

        if (requestDTO.items == null || requestDTO.items.isEmpty()) {
            throw new BadRequestException("La venta debe tener al menos un item");
        }

        long userId = Long.parseLong(jwt.getClaim("userId").toString());

        ClienteEntity cliente = clienteRepository.findById(requestDTO.idCliente);
        UsuarioEntity usuario = usuarioRepository.findById(userId);

        if (cliente == null || usuario == null) {
            throw new NotFoundException("Cliente o usuario no encontrado");
        }

        // Crear cabecera de venta
        VentaEntity venta = new VentaEntity();
        venta.cliente = cliente;
        venta.usuario = usuario;
        venta.metodoPago = requestDTO.metodoPago;
        venta.estado = requestDTO.estadoVenta;

        venta.subtotal = BigDecimal.ZERO;
        venta.persist();

        BigDecimal subtotal = BigDecimal.ZERO;

        // ITEM LOOP
        for (ItemVentaDTO item : requestDTO.items) {

            InventarioEntity inventario = inventarioRepository
                    .findById(item.getIdInventario());

            if (inventario == null) {
                throw new NotFoundException("Inventario no encontrado");
            }

            // cálculo total línea
            BigDecimal totalItem = item.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(item.getCantidad()))
                    .subtract(
                            item.getDescuento() != null ? item.getDescuento() : BigDecimal.ZERO
                    );

            subtotal = subtotal.add(totalItem);

            DetalleVentaEntity detalle = new DetalleVentaEntity();
            detalle.venta = venta;
            detalle.producto = inventario.getProducto();
            detalle.inventario = inventario;
            detalle.cantidad = item.getCantidad();
            detalle.precioUnitario = item.getPrecioUnitario();
            detalle.descuento = item.getDescuento() != null ? item.getDescuento() : BigDecimal.ZERO;
            detalle.total = totalItem;

            detalleRepository.persist(detalle);
        }

        // cálculo cabecera
        venta.subtotal = subtotal;
        venta.igv = subtotal.multiply(IGV);
        venta.total = venta.subtotal.add(venta.igv);

        venta.persist();

        // respuesta
        return mapper.toDTO(venta);

    }

    @Override
    public List<VentaResponseDTO> getAllVenta() {
        String query = "1=1";
        Map<String, Object> params = new HashMap<>();

        if (securityIdentity.hasRole("Administrador")) {
            return mapper.toDTOList(ventaRepository.listAll());
        }

        String username = securityIdentity.getPrincipal().getName();

        return mapper.toDTOList(
                ventaRepository.find("usuario.username", username).list()
        );

    }

    @Override
    public VentaResponseDTO getVentaById(Long id) {

        VentaEntity venta = ventaRepository.findById(id);
        if (venta == null) {
            throw new NotFoundException("Venta no encontrada");
        }

        if (securityIdentity.hasRole("VENDEDOR")) {
            String username = securityIdentity.getPrincipal().getName();
            if (!venta.usuario.username.equals(username)) {
                throw new ForbiddenException("No autorizado");
            }
        }

        return mapper.toDTO(venta);

    }

    /*@Override
    @Transactional
    public VentaResponseDTO registrarVentaPendiente(VentaRequestDTO dto) {

        long userId = Long.parseLong(jwt.getClaim("userId").toString());
        UsuarioEntity usuario = usuarioRepository.findById(userId);
        ClienteEntity cliente = clienteRepository.findById(dto.idCliente);

        if (cliente == null || usuario == null) {
            throw new NotFoundException("Cliente o usuario no encontrado");
        }

        VentaEntity venta = new VentaEntity();
        venta.cliente = cliente;
        venta.usuario = usuario;
        venta.metodoPago = dto.metodoPago;
        venta.estado = EstadoVenta.PENDIENTE;
        venta.subtotal = BigDecimal.ZERO;

        venta.persist();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemVentaDTO item : dto.items) {

            BigDecimal totalItem = item.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(item.getCantidad()))
                    .subtract(
                            item.getDescuento() != null ? item.getDescuento() : BigDecimal.ZERO
                    );

            subtotal = subtotal.add(totalItem);

            DetalleVentaEntity detalle = new DetalleVentaEntity();
            detalle.venta = venta;
            detalle.inventario = inventarioRepository.findById(item.getIdInventario());
            detalle.producto = detalle.inventario.getProducto();
            detalle.cantidad = item.getCantidad();
            detalle.precioUnitario = item.getPrecioUnitario();
            detalle.descuento = item.getDescuento() != null ? item.getDescuento() : BigDecimal.ZERO;
            detalle.total = totalItem;

            detalleRepository.persist(detalle);
        }

        venta.subtotal = subtotal;
        venta.igv = subtotal.multiply(BigDecimal.valueOf(0.18));
        venta.total = venta.subtotal.add(venta.igv);

        return mapper.toDTO(venta);

    }*/

    @Override
    @Transactional
    public VentaResponseDTO confirmarVenta(Long idVenta) {

        VentaEntity venta = ventaRepository.findById(idVenta);

        if (venta == null) {
            throw new NotFoundException("Venta no encontrada");
        }

        // validar estado actual
        if (venta.estado != EstadoVenta.PENDIENTE) {
            throw new IllegalStateException("La venta no está pendiente");
        }

        //seguridad JWT
        /*boolean isAdmin = jwt.getGroups().contains("Administrador");

        if (!isAdmin) {
            String username = jwt.getName();

            if (!venta.getUsuario().getUsername().equals(username)) {
                throw new ForbiddenException("No autorizado");
            }
        }*/

        venta.estado = EstadoVenta.PAGADO;
        venta.persist();

        return mapper.toDTO(venta);

    }

    @Override
    public VentaDetalleResponseDTO obtenerDetalleVentaById(Long id) {

        VentaEntity venta = ventaRepository.findByIdWithDetalles(id);

        if (venta == null) {
            throw new NotFoundException("Venta no encontrada");
        }

        // control por roles
        boolean isAdmin = jwt.getGroups().contains("Administrador");

        if (!isAdmin) {
            String username = jwt.getName();

            if (!venta.getUsuario().getUsername().equals(username)) {
                throw new ForbiddenException("No autorizado");
            }
        }

        return mapper.toVentaDetalleDTO(venta);


    }
}
