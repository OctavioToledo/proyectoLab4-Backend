package com.example.instrumentos_api.Services;

import com.example.instrumentos_api.Entities.Instrumento;

import java.util.List;

public interface InstrumentoService {
    List<Instrumento> getAllInstrumentos();
    Instrumento getInstrumentoById(Long id);
    Instrumento createInstrumento(Instrumento instrumento);
    Instrumento updateInstrumento(Long id, Instrumento instrumento);
    void deleteInstrumento(Long id);
    Iterable<Instrumento> saveAll(List<Instrumento> instrumentos);
}
