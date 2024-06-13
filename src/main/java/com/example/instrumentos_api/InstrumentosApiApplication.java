package com.example.instrumentos_api;

import com.example.instrumentos_api.Entities.Instrumento;
import com.example.instrumentos_api.Entities.Usuario;
import com.example.instrumentos_api.Repositories.UsuarioRepository;
import com.example.instrumentos_api.Services.impl.InstrumentoServiceImpl;
import com.example.instrumentos_api.Utils.EncryptionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class InstrumentosApiApplication {

	@Autowired
	private InstrumentoServiceImpl instrumentoService;

	public static void main(String[] args) {
		SpringApplication.run(InstrumentosApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Instrumento>> typeReference = new TypeReference<List<Instrumento>>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
			try {
				List<Instrumento> instrumentos = mapper.readValue(inputStream, typeReference);
				instrumentoService.saveAll(instrumentos);
				System.out.println("Instrumentos guardados!");
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
