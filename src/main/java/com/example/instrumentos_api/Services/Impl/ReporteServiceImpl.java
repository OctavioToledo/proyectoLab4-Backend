package com.example.instrumentos_api.Services.Impl;

import com.example.instrumentos_api.Services.ReporteService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Override
    public byte[] generarReporteExcel(List<Object[]> reporteData) throws Exception {
        // Crear el libro de Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte de Pedidos");

        // Crear la fila de encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Fecha Pedido", "Instrumento", "Marca", "Modelo", "Cantidad", "Precio Subtotal"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Llenar el contenido del reporte
        int rowNum = 1;
        for (Object[] rowData : reporteData) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                if (rowData[i] instanceof java.util.Date) {
                    cell.setCellValue((java.util.Date) rowData[i]);
                } else if (rowData[i] instanceof String) {
                    cell.setCellValue((String) rowData[i]);
                } else if (rowData[i] instanceof Double) {
                    cell.setCellValue((Double) rowData[i]);
                } else if (rowData[i] instanceof Integer) {
                    cell.setCellValue((Integer) rowData[i]);
                }
            }
        }

        // Convertir el libro de Excel a bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] reporteBytes = outputStream.toByteArray();
        outputStream.close();

        return reporteBytes;
    }
}
