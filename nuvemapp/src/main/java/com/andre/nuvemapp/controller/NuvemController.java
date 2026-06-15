package com.andre.nuvemapp.controller;

import com.andre.nuvemapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NuvemController {

    private final String APP_ID = "26778";

    @Autowired
    private UsuarioService usuarioService;
}
