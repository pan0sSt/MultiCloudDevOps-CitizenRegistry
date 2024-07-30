package gr.ekpa.citizen.client;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import gr.ekpa.citizen.domain.Citizen;
import reactor.core.publisher.Mono;

@Component
public class CitizenClient {

    private static final String BASE_URL = "http://localhost:8080/api/citizens";
    private final WebClient webClient;
    
    public CitizenClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }
    
    public List<Citizen> getAllCitizens() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(Citizen.class)
                .collectList()
                .block();
    }

    public Citizen getCitizenById(String id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Citizen.class)
                .block();
    }

    public Citizen createCitizen(Citizen citizen) {
        return webClient.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(citizen), Citizen.class)
                .retrieve()
                .bodyToMono(Citizen.class)
                .block();
    }

    public void updateCitizen(String id, Citizen citizen) {
        webClient.put()
                .uri("/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(citizen), Citizen.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteCitizen(String id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}