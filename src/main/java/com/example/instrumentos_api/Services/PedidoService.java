package com.example.instrumentos_api.Services;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Entities.Pedido;

import java.util.List;

public interface PedidoService {
    Pedido guardarPedido(List<Instrumento> instrumentos);
}
