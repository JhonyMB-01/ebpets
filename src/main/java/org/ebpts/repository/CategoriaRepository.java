package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.CategoriaEntity;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<CategoriaEntity> {
}
