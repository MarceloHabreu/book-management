package io.github.marcelohabreu.bookManagement;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookManagementApplication {

    public static void main(String[] args) {
        // Carrega o arquivo .env
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // Diretório onde o .env está (raiz do projeto)
                .load();

        // Define as variáveis como propriedades do sistema
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(BookManagementApplication.class, args);
    }

}
