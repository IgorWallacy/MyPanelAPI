package com.doks.mypanel.controller;

import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/teste")
public class ConexaoController {

    @RequestMapping("/teste")
    public String teste(){
        return "Teste";
    }
}
