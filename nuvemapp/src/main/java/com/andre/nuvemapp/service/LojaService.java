package com.andre.nuvemapp.service;

import com.andre.nuvemapp.model.Loja;
import com.andre.nuvemapp.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class LojaService {

    @Value("${nuvemshop.client-id")
    private String clientId;

    @Value("${nuvemshop.client-secret}")
    private String clientSecret;

    private RestClient restClient;
    private ObjectMapper objectMapper;

    @Autowired
    private LojaRepository lojaRepository;

    public void trocarCodePorToken(String code, String state){
        Map<String, String> body = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "grant_type", "autorization_code",
                "code", code
        );

        String response = restClient.post()
                .uri("https://www.nuvemshop.com/apps/authorize/token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(String.class);

        Map<String, Object> json = objectMapper.readValue(response, Map.class);

        Loja loja = new Loja();
        String accessToken = (String) json.get("access_token");
        Long userId = ((Number) json.get("user_id")).longValue();
        String scope = (String) json.get("scope");

        loja.setLoja_id(userId);
        loja.setAcessToken(accessToken);
        loja.setScope(scope);
        lojaRepository.save(loja);
    }
}
