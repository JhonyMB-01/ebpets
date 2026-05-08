package org.ebpts.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.VentaEntity;
import org.ebpts.utils.EstadoVenta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VentaRepository implements PanacheRepository<VentaEntity> {


    public List<VentaEntity> listarPorEstado(
            EstadoVenta estado,
            LocalDate desde,
            LocalDate hasta,
            String vendedor
    ) {
        StringBuilder query = new StringBuilder(
                "estado = ?1"
        );

        List<Object> params = new ArrayList<>();
        params.add(estado);

        if (desde != null) {
            query.append(" AND fecha >= ?").append(params.size() + 1);
            params.add(desde.atStartOfDay());
        }

        if (hasta != null) {
            query.append(" AND fecha <= ?").append(params.size() + 1);
            params.add(hasta.atTime(23, 59, 59));
        }

        if (vendedor != null) {
            query.append(" AND usuario.username = ?").append(params.size() + 1);
            params.add(vendedor);
        }

        return find(query.toString(), params.toArray()).list();
    }

}
