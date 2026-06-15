package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.InventarioEntity;

import java.util.List;

@ApplicationScoped
public class InventarioRepository implements PanacheRepository<InventarioEntity> {


    //Nos permite devolver una lista ordenada por la feha de vencimiento
    public List<InventarioEntity> findByProducto(Long idProducto) {
        return find(
                "producto.id = ?1 AND stock > 0 ORDER BY fechaVencimiento ASC",
                idProducto
        ).list();
    }


    public List<InventarioEntity> findAllOrdenado() {
        return find("ORDER BY producto.nombre ASC, fechaVencimiento ASC")
                .list();
    }


}
