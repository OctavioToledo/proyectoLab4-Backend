package com.example.instrumentos_api.Services;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Entities.Pedido;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;




public interface PedidoService {
    Pedido guardarPedido(List<Instrumento> instrumentos);
    List<Object[]> countPedidosByMonthAndYear();
    List<Object[]> countPedidosByInstrumento();// Añadir esta línea

        List<Object[]> generarReportePorFechas(LocalDate startDate, LocalDate endDate);
}


