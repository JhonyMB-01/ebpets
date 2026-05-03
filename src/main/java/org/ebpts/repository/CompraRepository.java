package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.CompraEntity;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<CompraEntity> {
}
