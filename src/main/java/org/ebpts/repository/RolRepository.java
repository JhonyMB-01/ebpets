package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.RolEntity;

@ApplicationScoped
public class RolRepository implements PanacheRepository<RolEntity> {
}
