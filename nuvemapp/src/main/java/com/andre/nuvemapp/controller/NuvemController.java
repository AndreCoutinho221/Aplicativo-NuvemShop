package com.andre.nuvemapp.controller;

import com.andre.nuvemapp.dto.LojaAutenticacao;
import com.andre.nuvemapp.service.LojaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

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

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @GetMapping("/oauth/nuvemshop/callback")
    public void callback(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        //Troca o code recebido da NuvemShop por um token e as informações da loja
        LojaAutenticacao lojaValidada = lojaService.validarLoja(code, state);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                    lojaValidada,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_STORE"))
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, request, response);

        response.sendRedirect("/sucesso");
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

    @GetMapping("/sucesso")
    public String sucesso(){
        return "sucesso";
    }
}
