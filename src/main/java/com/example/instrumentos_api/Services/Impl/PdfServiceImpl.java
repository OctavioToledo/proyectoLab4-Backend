package com.example.instrumentos_api.Services.Impl;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Repositories.InstrumentoRepository;
import com.example.instrumentos_api.Services.PdfService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Override
    public byte[] generateInstrumentDetailPdf(Long instrumentoId) throws IOException {
        Instrumento instrumento = instrumentoRepository.findById(instrumentoId).orElse(null);
        if (instrumento == null) {
            throw new IllegalArgumentException("Instrumento no encontrado");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Título
            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Paragraph title = new Paragraph("Detalle del Instrumento", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Tabla
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2});

            addTableHeader(table);
            addRows(table, instrumento);

            document.add(table);
        } catch (DocumentException e) {
            throw new IOException("Error al generar el documento PDF", e);
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        PdfPCell header = new PdfPCell(new Phrase("Atributo"));
        header.setBackgroundColor(new Color(140, 221, 8));
        table.addCell(header);

        header = new PdfPCell(new Phrase("Valor"));
        header.setBackgroundColor(new Color(140, 221, 8));
        table.addCell(header);
    }

    private void addRows(PdfPTable table, Instrumento instrumento) {
        table.addCell("Instrumento");
        table.addCell(instrumento.getInstrumento());

        table.addCell("Marca");
        table.addCell(instrumento.getMarca());

        table.addCell("Modelo");
        table.addCell(instrumento.getModelo());

        table.addCell("Categoria");
        table.addCell(instrumento.getCategoria());

        table.addCell("Precio");
        table.addCell("$" + instrumento.getPrecio());

        table.addCell("Costo de Envío");
        String costoEnvioTexto = instrumento.getCostoEnvio().equals("G") ? "Envío gratis a todo el país" : "$" + instrumento.getCostoEnvio();
        table.addCell(costoEnvioTexto);

        table.addCell("Cantidad Vendida");
        table.addCell(String.valueOf(instrumento.getCantidadVendida()));

        table.addCell("Descripción");
        table.addCell(instrumento.getDescripcion());
    }
}
