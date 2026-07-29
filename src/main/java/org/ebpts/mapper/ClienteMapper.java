package org.ebpts.mapper;

import org.ebpts.dto.request.ClienteRequestDTO;
import org.ebpts.dto.response.ClienteResponseDTO;
import org.ebpts.entity.ClienteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ClienteMapper {

    ClienteEntity toEntity(ClienteRequestDTO dto);

    ClienteResponseDTO toDTO(ClienteEntity entity);

    List<ClienteResponseDTO> toDTOList(List<ClienteEntity> list);



}
