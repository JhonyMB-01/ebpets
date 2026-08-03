package org.ebpts.mapper;

import org.ebpts.dto.response.UsuarioResponseDTO;
import org.ebpts.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UsuarioMapper {

    //@Mapping(source = "rol.nombre", target = "rol")
    UsuarioResponseDTO toDTO(UsuarioEntity entity);

}
