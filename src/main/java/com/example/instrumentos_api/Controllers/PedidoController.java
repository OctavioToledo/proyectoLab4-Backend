package com.example.instrumentos_api.Controllers;

import com.example.instrumentos_api.InstrumentosApiApplication;
import com.example.instrumentos_api.Repositories.PedidoRepository;
import com.example.instrumentos_api.Services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Entities.Pedido;
import com.example.instrumentos_api.Services.PedidoService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {


    @Autowired
    private ReporteService reporteService;
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/pieChart")
    public List<Object[]> countPedidosByInstrumento() {
        return pedidoService.countPedidosByInstrumento();
    }
    @GetMapping("/barChart")
    public List<Object[]> countPedidosByMonthAndYear() {
        return pedidoService.countPedidosByMonthAndYear();
    }

    @PostMapping
    public Pedido crearPedido(@RequestBody List<Instrumento> instrumentos) {
        return pedidoService.guardarPedido(instrumentos);

    }


    @GetMapping("/reporteExcel")
    public ResponseEntity<Object> generarReporteExcel(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            // Obtener los datos del reporte desde el servicio de pedidos
            List<Object[]> reporteData = pedidoService.generarReportePorFechas(startDate, endDate);

            // Generar el reporte en formato Excel
            byte[] reporteBytes = reporteService.generarReporteExcel(reporteData);

            // Configurar los encabezados para la descarga del archivo
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reporte_pedidos.xlsx");

            return new ResponseEntity<>(reporteBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el reporte: " + e.getMessage());
        }
    }



}