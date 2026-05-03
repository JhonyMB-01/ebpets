package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.ProveedorEntity;

@ApplicationScoped
public class ProveedorRepository implements PanacheRepository<ProveedorEntity> {
}
