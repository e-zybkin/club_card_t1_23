package develop.backend.Club_card.config;

import develop.backend.Club_card.client.CurrencyInteractionRestClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public CurrencyInteractionRestClientImpl currencyInteractionRestClient() {
        return new CurrencyInteractionRestClientImpl(RestClient.builder().build());
    }
}
