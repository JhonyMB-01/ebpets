package org.ebpts.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoriaResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;

}
