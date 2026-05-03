package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.ProductoEntity;

@ApplicationScoped
public class ProductoRepository implements PanacheRepository<ProductoEntity> {

    public boolean existsByCodigo(String codigo) {
        return count("codigo", codigo) > 0;
    }

}
