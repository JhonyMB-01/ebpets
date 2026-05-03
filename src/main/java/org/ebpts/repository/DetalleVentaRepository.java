package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.DetalleVentaEntity;

@ApplicationScoped
public class DetalleVentaRepository implements PanacheRepository<DetalleVentaEntity> {
}
