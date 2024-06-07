package com.example.instrumentos_api.Repositories;

import com.example.instrumentos_api.Entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
