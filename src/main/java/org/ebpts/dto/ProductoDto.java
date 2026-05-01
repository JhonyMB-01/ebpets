package org.ebpts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDto {

    public Long id;
    public String codigo;
    public String nombre;
    public Double precio;
    public Boolean afectaIgv;
}
