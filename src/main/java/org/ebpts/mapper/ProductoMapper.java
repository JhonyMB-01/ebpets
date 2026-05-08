package org.ebpts.mapper;

import org.ebpts.dto.response.ProductoResponseDTO;
import org.ebpts.entity.ProductoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProductoMapper {


    ProductoResponseDTO toDTO(ProductoEntity entity);

    List<ProductoResponseDTO> toDTOList(List<ProductoEntity> list);
}
