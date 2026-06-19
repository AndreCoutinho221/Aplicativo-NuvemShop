package com.andre.nuvemapp.service;

import com.andre.nuvemapp.model.Loja;
import com.andre.nuvemapp.repository.LojaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class LojaService {

    @Value("${nuvemshop.app-id}")
    private String clientId;

    @Value("${nuvemshop.client-secret}")
    private String clientSecret;

    private RestClient restClient;
    private ObjectMapper objectMapper;

    @Autowired
    private LojaRepository lojaRepository;

    public LojaService(RestClient.Builder builder, ObjectMapper objectMapper) {
        this.restClient = builder.build();
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Map<String, Object> trocarCodePorToken(String code, String state){
        Map<String, String> body = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "grant_type", "authorization_code",
                "code", code
        );
        String response = restClient.post()
                .uri("https://www.tiendanube.com/apps/authorize/token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(String.class);

        Map<String, Object> json = objectMapper.readValue(response, Map.class);
        System.out.println(json);
        return json;
    }

    public boolean verificaCadastroLoja(String loja_id){
        return lojaRepository.existsById(loja_id);
    }


    public Loja cadastraLoja(Map<String, Object> lojaJSON){
        String access_token = (String) lojaJSON.get("access_token");
        Integer loja_id = (Integer) lojaJSON.get("user_id");
        String scope = (String) lojaJSON.get("scope");

        Loja loja = new Loja();
        loja.setAccessToken(access_token);
        loja.setLoja_id(Long.valueOf(loja_id));
        loja.setScope(scope);
        loja.setInstalledAt(LocalDateTime.now());
        loja.setUpdatedAt(LocalDateTime.now());
        lojaRepository.save(loja);

        return loja;
    }

    @Transactional
    public Loja atualizaLoja(String loja_id){
        Loja loja = lojaRepository.findById(loja_id).orElseThrow();
        loja.setUpdatedAt(LocalDateTime.now());

        return loja;
    }
}
