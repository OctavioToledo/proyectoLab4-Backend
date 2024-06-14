package com.example.instrumentos_api.Services;

import org.dom4j.DocumentException;

import java.io.IOException;

public interface PdfService {
    byte[] generateInstrumentDetailPdf(Long instrumentoId) throws IOException;
}
