package org.ebpts.service;

import org.ebpts.dto.response.InventarioResponseDTO;
import org.ebpts.dto.response.ValidacionInventarioDTO;

import java.util.List;

public interface InventarioService {


    List<InventarioResponseDTO> obtenerPorProducto(Long idProducto);

    List<InventarioResponseDTO> listarTodos();

    InventarioResponseDTO obtenerPorId(Long id);

    ValidacionInventarioDTO validarStokById(Long id, Integer cantidad);

}
