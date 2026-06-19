package com.andre.nuvemapp.controller;

import com.andre.nuvemapp.service.LojaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpResponse;
import java.util.Map;

@Controller
public class NuvemController {

    @Value("${nuvemshop.app-id}")
    private String appId;

    @Autowired
    private LojaService lojaService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/oauth/nuvemshop/callback")
    public String callback(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state,
            HttpRequest request,
            HttpResponse response){

        //Troca o code recebido da NuvemShop por um token e as informações da loja
        Map<String, Object> lojaJSON = lojaService.trocarCodePorToken(code, state);

        String loja_id = (String) lojaJSON.get("user_id");

        //Cadastra a loja ou atualiza caso ja esteja cadastrada
        if (!lojaService.verificaCadastroLoja(loja_id)){
            lojaService.cadastraLoja(lojaJSON);
        } else lojaService.atualizaLoja(loja_id);



        return "redirect:/log";
    };

    @GetMapping("/integrations/nuvemshop/install")
    public String authNuvemShop(){
        return "redirect:https://www.nuvemshop.com.br/apps/"+ appId +"/authorize";
    }


    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/log")
    public String log(){
        return "log";
    }
}
