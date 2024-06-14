    package com.example.instrumentos_api.Services;



    import java.util.Date;
    import java.util.List;


    public interface ReporteService {
        byte[] generarReporteExcel(List<Object[]> reporteData) throws Exception;
    }

