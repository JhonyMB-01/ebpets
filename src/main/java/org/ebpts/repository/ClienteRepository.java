package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.ClienteEntity;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<ClienteEntity> {
}
