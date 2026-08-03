package org.ebpts.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.UsuarioEntity;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity> {

    public boolean existsByUsername(String username) {
        return count("username", username) > 0;
    }

    public UsuarioEntity findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
