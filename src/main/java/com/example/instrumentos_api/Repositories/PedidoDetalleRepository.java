package com.example.instrumentos_api.Repositories;

import com.example.instrumentos_api.Entities.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
}
