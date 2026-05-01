package org.ebpts.service;

import org.ebpts.dto.request.UsuarioRequestDTO;
import org.ebpts.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO saveUsuario(UsuarioRequestDTO dto);
    List<UsuarioResponseDTO> listarUsaurio();
}
