package CarmineGargiulo.Progetto_Settimana_17;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Scanner;

@Configuration
public class BeansConfig {

    @Bean
    public Faker getFaker() {
        return new Faker(Locale.ITALY);
    }

    @Bean
    public Scanner getScanner(){
        return new Scanner(System.in);
    }
}
