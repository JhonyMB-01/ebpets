package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.InventarioEntity;

@ApplicationScoped
public class InventarioRepository implements PanacheRepository<InventarioEntity> {
}
