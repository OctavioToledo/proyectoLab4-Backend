package com.example.instrumentos_api.Services.impl;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Repositories.InstrumentoRepository;
import com.example.instrumentos_api.Services.InstrumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentoServiceImpl implements InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Override
    public List<Instrumento> getAllInstrumentos() {
        return instrumentoRepository.findAll();
    }

    @Override
    public Instrumento getInstrumentoById(Long id) {
        return instrumentoRepository.findById(id).orElse(null);
    }

    @Override
    public Instrumento createInstrumento(Instrumento instrumento) {
        return instrumentoRepository.save(instrumento);
    }

    @Override
    public Instrumento updateInstrumento(Long id, Instrumento instrumento) {
        Instrumento existingInstrumento = instrumentoRepository.findById(id).orElse(null);
        if (existingInstrumento != null) {
            existingInstrumento.setInstrumento(instrumento.getInstrumento());
            existingInstrumento.setModelo(instrumento.getModelo());
            existingInstrumento.setPrecio(instrumento.getPrecio());
            existingInstrumento.setCategoria(instrumento.getCategoria());
            existingInstrumento.setCostoEnvio(instrumento.getCostoEnvio());
            existingInstrumento.setDescripcion(instrumento.getDescripcion());
            return instrumentoRepository.save(existingInstrumento);
        }
        return null;
    }

    @Override
    public void deleteInstrumento(Long id) {
        instrumentoRepository.deleteById(id);
    }

    @Override
    public Iterable<Instrumento> saveAll(List<Instrumento> instrumentos) {
        return instrumentoRepository.saveAll(instrumentos);
    }
}
