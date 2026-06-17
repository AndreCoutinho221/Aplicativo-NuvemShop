package com.andre.nuvemapp.controller;

import com.andre.nuvemapp.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NuvemController {

    @Autowired
    private LojaService lojaService;

    @GetMapping("/callback")
    public ResponseEntity<String> callback(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state){
        lojaService.trocarCodePorToken(code, state);
        return ResponseEntity.ok("Loja conectada com sucesso.");
    };
}
