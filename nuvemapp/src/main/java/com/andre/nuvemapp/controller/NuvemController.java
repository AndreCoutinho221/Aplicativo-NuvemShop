package com.andre.nuvemapp.controller;

import com.andre.nuvemapp.model.Loja;
import com.andre.nuvemapp.model.LojaPrincipal;
import com.andre.nuvemapp.service.LojaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
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

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @GetMapping("/oauth/nuvemshop/callback")
    public void callback(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        //Troca o code recebido da NuvemShop por um token e as informações da loja
        Map<String, Object> lojaJSON = lojaService.trocarCodePorToken(code, state);
        System.out.println(lojaJSON);
        Integer id = (Integer) lojaJSON.get("user_id");
        String loja_id = id.toString();
        Loja loja;
        //Cadastra a loja ou atualiza caso ja esteja cadastrada
        if (!lojaService.verificaCadastroLoja(loja_id)){
            loja = lojaService.cadastraLoja(lojaJSON);
        } else {
            loja = lojaService.atualizaLoja(loja_id);
        }

        LojaPrincipal principal = new LojaPrincipal(
                loja.getLoja_id(),
                loja.getLoja_id(),
                loja.getAccessToken()
        );

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                    principal,
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
