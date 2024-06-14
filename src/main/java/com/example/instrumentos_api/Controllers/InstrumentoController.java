package com.example.instrumentos_api.Controllers;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Services.InstrumentoService;
import com.example.instrumentos_api.Services.PdfService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api/instrumentos")
@CrossOrigin(origins = "http://localhost:5173")
public class InstrumentoController {

    @Autowired
    private PdfService pdfService;
    @Autowired
    private InstrumentoService instrumentoService;

    @GetMapping
    public List<Instrumento> getAllInstrumentos() {
        return instrumentoService.getAllInstrumentos();
    }

    @GetMapping("/{id}")
    public Instrumento getInstrumentoById(@PathVariable Long id) {
        return instrumentoService.getInstrumentoById(id);
    }

    @PostMapping
    public Instrumento createInstrumento(@RequestBody Instrumento instrumento) {
        return instrumentoService.createInstrumento(instrumento);
    }

    @PutMapping("/{id}")
    public Instrumento updateInstrumento(@PathVariable Long id, @RequestBody Instrumento instrumentoDetails) {
        return instrumentoService.updateInstrumento(id, instrumentoDetails);
    }

    /*@DeleteMapping("/{id}")
    public void deleteInstrumento(@PathVariable Long id) {
        instrumentoService.deleteInstrumento(id);
    }*/
    @DeleteMapping("/{id}")
    public void deleteInstrumento(@PathVariable Long id) {
        Instrumento instrumento = instrumentoService.getInstrumentoById(id);
        instrumento.setEliminado(true);
        instrumentoService.updateInstrumento(id, instrumento);
    }

    @GetMapping("/{id}/reportePdf")
    public ResponseEntity<Object> generarReportePdf(@PathVariable Long id) {
        try {
            byte[] reporteBytes = pdfService.generateInstrumentDetailPdf(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "instrumento_detalle.pdf");

            return new ResponseEntity<>(reporteBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el reporte: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Instrumento no encontrado: " + e.getMessage());
        }
    }
}


