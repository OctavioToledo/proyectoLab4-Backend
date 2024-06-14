package com.example.instrumentos_api;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Entities.Pedido;
import com.example.instrumentos_api.Entities.PedidoDetalle;
import com.example.instrumentos_api.Entities.Usuario;
import com.example.instrumentos_api.Repositories.InstrumentoRepository;
import com.example.instrumentos_api.Repositories.PedidoRepository;
import com.example.instrumentos_api.Repositories.UsuarioRepository;
import com.example.instrumentos_api.Services.impl.InstrumentoServiceImpl;
import com.example.instrumentos_api.Utils.EncryptionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class InstrumentosApiApplication {

	@Autowired
	private InstrumentoServiceImpl instrumentoService;

	@Autowired
	private InstrumentoRepository instrumentoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(InstrumentosApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			// Cargar instrumentos desde data.json
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Instrumento>> typeReference = new TypeReference<List<Instrumento>>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
			try {
				List<Instrumento> instrumentos = mapper.readValue(inputStream, typeReference);
				instrumentoService.saveAll(instrumentos);
				System.out.println("Instrumentos guardados!");

				// Crear algunos pedidos y detalles de pedido
				if (instrumentos.size() >= 2) {
					Pedido pedido1 = new Pedido();
					pedido1.setFechaPedido(new Date());

					PedidoDetalle detalle1 = new PedidoDetalle();
					detalle1.setCantidad(1);
					detalle1.setInstrumento(instrumentos.get(0));
					detalle1.setPedido(pedido1);

					pedido1.setDetalles(Arrays.asList(detalle1));
					pedido1.setTotalPedido(detalle1.getCantidad() * instrumentos.get(0).getPrecio());

					Pedido pedido2 = new Pedido();
					pedido2.setFechaPedido(new Date());

					PedidoDetalle detalle2 = new PedidoDetalle();
					detalle2.setCantidad(1);
					detalle2.setInstrumento(instrumentos.get(1));
					detalle2.setPedido(pedido2);

					pedido2.setDetalles(Arrays.asList(detalle2));
					pedido2.setTotalPedido(detalle2.getCantidad() * instrumentos.get(1).getPrecio());

					Pedido pedido3 = new Pedido();
					pedido3.setFechaPedido(new Date(2023 - 1900, 3 - 1, 10));

					PedidoDetalle detalle3 = new PedidoDetalle();
					detalle3.setCantidad(3);
					detalle3.setInstrumento(instrumentos.get(7));
					detalle3.setPedido(pedido3);

					pedido3.setDetalles(Arrays.asList(detalle3));
					pedido3.setTotalPedido(detalle3.getCantidad() * instrumentos.get(7).getPrecio());

					Pedido pedido4 = new Pedido();
					pedido4.setFechaPedido(new Date(2023 - 1900, 4 - 1, 10));
					PedidoDetalle detalle4 = new PedidoDetalle();
					detalle4.setCantidad(5);
					detalle4.setInstrumento(instrumentos.get(6));
					detalle4.setPedido(pedido4);

					pedido4.setDetalles(Arrays.asList(detalle4));
					pedido4.setTotalPedido(detalle4.getCantidad() * instrumentos.get(6).getPrecio());

					pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2, pedido3, pedido4));
					System.out.println("Pedidos guardados!");
				}

			} catch (Exception e) {
				System.out.println("No se pudo guardar los instrumentos: " + e.getMessage());
			}
		};
	}

	@Configuration
	public class LoadDatabase {
		@Bean
		public CommandLineRunner initDatabase(UsuarioRepository repository) {
			return args -> {
				repository.save(new Usuario("admin", EncryptionUtils.encryptPassword("adminpass"), "Admin"));
				repository.save(new Usuario("operador", EncryptionUtils.encryptPassword("operadorpass"), "Operador"));
			};
		}
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // Asegúrate de que permite todas las rutas
						.allowedOrigins("http://localhost:5173")
						.allowedMethods("*")
						.allowedHeaders("*");
			}
		};
	}
}
