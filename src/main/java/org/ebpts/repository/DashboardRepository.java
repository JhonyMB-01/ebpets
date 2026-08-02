package org.ebpts.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.ebpts.dto.record.DashboardKpiDTO;
import org.ebpts.dto.record.ProductoStockDTO;
import org.ebpts.dto.record.VentaRecienteDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.ebpts.utils.QuerysSql.*;

@ApplicationScoped
public class DashboardRepository {

    @Inject
    EntityManager em;

    public DashboardKpiDTO obtenerDashboard() {

        Object[] row = (Object[]) em
                .createNativeQuery(SQL_DASHBOARD)
                .getSingleResult();

        return new DashboardKpiDTO(
                (BigDecimal) row[0],
                (BigDecimal) row[1],
                (BigDecimal) row[2],
                ((Number) row[3]).intValue(),
                ((Number) row[4]).intValue(),
                ((Number) row[5]).intValue(),
                ((Number) row[6]).intValue(),
                ((Number) row[7]).intValue(),
                (BigDecimal) row[8],
                ((Number) row[9]).intValue()
        );
    }

    public List<ProductoStockDTO> obtenerProductosSinStock(){
        List<Object[]> rows = em.createNativeQuery(SQL_PRODUCTOS_SIN_STOCK).getResultList();

        List<ProductoStockDTO> lista = new ArrayList<>();

        for (Object[] r : rows) {

            lista.add(new ProductoStockDTO(

                    ((Number) r[0]).longValue(),
                    (String) r[1],
                    (String) r[2],
                    ((Number) r[3]).intValue(),
                    ((Number) r[4]).intValue()

            ));

        }

        return lista;
    }

    public List<VentaRecienteDTO> obtenerUltimasVentas(int limite){
        //Query query = em.createNativeQuery(SQL_ULTIMAS_VENTAS);

        List<Object[]> rows = em.createNativeQuery(SQL_ULTIMAS_VENTAS).getResultList();

        //query.setParameter("limite", limite);

        //List<Object[]> rows = query.getResultList();

        List<VentaRecienteDTO> lista = new ArrayList<>();

        for (Object[] r : rows) {

            LocalDateTime fecha = ((LocalDateTime) r[3]);

            lista.add(new VentaRecienteDTO(

                    ((Number) r[0]).longValue(),
                    (String) r[1],
                    (BigDecimal) r[2],
                    fecha

            ));
        }

        return lista;
    }



}