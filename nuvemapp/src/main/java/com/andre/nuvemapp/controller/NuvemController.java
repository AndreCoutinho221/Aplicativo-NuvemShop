package com.andre.nuvemapp.controller;

import com.andre.nuvemapp.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NuvemController {

    @Value("${nuvemshop.app-id}")
    private String appId;

    @Autowired
    private LojaService lojaService;

    @GetMapping("/oauth/nuvemshop/callback")
    public ResponseEntity<String> callback(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state){
        lojaService.trocarCodePorToken(code, state);
        return ResponseEntity.ok("Loja conectada com sucesso.");
    };

    @GetMapping("/integrations/nuvemshop/install")
    public String authNuvemShop(){
        return "redirect:https://www.nuvemshop.com.br/apps/"+ appId +"/authorize";
    }

    @GetMapping("/home")
    public String homeTemporaria(){
        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Instalar Integração</title>
            </head>
            <body>
                <button onclick="window.location.href='/integrations/nuvemshop/install'">
                    Instalar Integração
                </button>
            </body>
            </html>
            """;
    }
}
