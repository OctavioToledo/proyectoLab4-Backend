package com.example.instrumentos_api.Controllers;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Entities.Pedido;
import com.example.instrumentos_api.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public Pedido crearPedido(@RequestBody List<Instrumento> instrumentos) {
        return pedidoService.guardarPedido(instrumentos);
    }
}

