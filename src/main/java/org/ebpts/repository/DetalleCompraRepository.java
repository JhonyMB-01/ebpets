package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.DetalleCompraEntity;

@ApplicationScoped
public class DetalleCompraRepository implements PanacheRepository<DetalleCompraEntity> {
}

