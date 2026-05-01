package org.ebpts.service;

import org.ebpts.dto.request.RolRequestDTO;
import org.ebpts.dto.response.RolResponseDTO;

import java.util.List;

public interface RolSevice {

    List<RolResponseDTO> getAllRol();

    RolResponseDTO saveRol(RolRequestDTO rolRequestDTO);

    void deleteRol(Long idRol);
}
