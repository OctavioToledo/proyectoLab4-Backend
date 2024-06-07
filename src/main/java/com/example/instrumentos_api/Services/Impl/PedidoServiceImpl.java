package com.example.instrumentos_api.Services.impl;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Entities.Pedido;
import com.example.instrumentos_api.Entities.PedidoDetalle;
import com.example.instrumentos_api.Repositories.PedidoDetalleRepository;
import com.example.instrumentos_api.Repositories.PedidoRepository;
import com.example.instrumentos_api.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Override
    public Pedido guardarPedido(List<Instrumento> instrumentos) {
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(new Date());

        double totalPedido = 0;

        for (Instrumento instrumento : instrumentos) {
            totalPedido += instrumento.getPrecio() * instrumento.getQuantity();
        }

        pedido.setTotalPedido(totalPedido);

        pedido = pedidoRepository.save(pedido);

        for (Instrumento instrumento : instrumentos) {
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setCantidad(instrumento.getQuantity()); // Asume 1 por simplicidad, puedes ajustar según sea necesario
            detalle.setInstrumento(instrumento);
            detalle.setPedido(pedido);
            pedidoDetalleRepository.save(detalle);
        }

        return pedido;
    }
}
