package Servidor_CDA.Servidor_CDA;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public PDFGenerator pdfGenerator() {
        return new PDFGenerator();
    }
}
