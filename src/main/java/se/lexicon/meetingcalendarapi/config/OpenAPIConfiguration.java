package se.lexicon.meetingcalendarapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenAPI(HttpSession httpSession) {

        Server server = new Server().url("http://localhost:8080").description("Development Server");
        Contact contact = new Contact().name("Sayana").email("sayana@gmail.com");
        Info info = new Info().title("MeetingCalendar API").version("1.0")
                .description("API to publish and manage meeting calendar.").contact(contact);
        return new OpenAPI().info(info).servers(List.of(server));
    }
}
