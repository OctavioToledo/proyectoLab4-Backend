package com.example.instrumentos_api.Controllers;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Services.InstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/instrumentos")
@CrossOrigin(origins = "http://localhost:5173")
public class InstrumentoController {

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

}
