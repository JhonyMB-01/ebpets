package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.VentaEntity;

@ApplicationScoped
public class VentaRepository implements PanacheRepository<VentaEntity> {
}
