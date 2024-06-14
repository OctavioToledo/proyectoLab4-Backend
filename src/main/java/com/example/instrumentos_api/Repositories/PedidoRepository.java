package com.example.instrumentos_api.Repositories;

import com.example.instrumentos_api.Entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT MONTH(p.fechaPedido) AS mes, YEAR(p.fechaPedido) AS anio, COUNT(p) AS cantidadPedidos " +
            "FROM Pedido p " +
            "GROUP BY YEAR(p.fechaPedido), MONTH(p.fechaPedido) " +
            "ORDER BY YEAR(p.fechaPedido) DESC, MONTH(p.fechaPedido) DESC")
    List<Object[]> countPedidosByMonthAndYear();
    @Query("SELECT pd.instrumento.instrumento AS nombreInstrumento, SUM(pd.cantidad) AS cantidadPedidos " +
            "FROM PedidoDetalle pd " +
            "GROUP BY pd.instrumento.instrumento")
    List<Object[]> countPedidosByInstrumento();

    @Query("SELECT p.fechaPedido AS fechaPedido, " +
            "pd.instrumento.instrumento AS instrumento, " +
            "pd.instrumento.marca AS marca, " +
            "pd.instrumento.modelo AS modelo, " +
            "pd.cantidad AS cantidad, " +
            "pd.instrumento.precio * pd.cantidad AS subtotal " +
            "FROM PedidoDetalle pd " +
            "JOIN pd.pedido p")
    List<Object[]> generarReporteCompleto();


}
