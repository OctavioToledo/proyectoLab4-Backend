package com.example.instrumentos_api.Repositories;



import com.example.instrumentos_api.Entities.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {
}
