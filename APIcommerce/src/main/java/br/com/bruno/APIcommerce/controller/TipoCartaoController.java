package br.com.bruno.APIcommerce.controller;

import br.com.bruno.APIcommerce.model.TipoCartao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipo-cartao")

public class TipoCartaoController {

    @GetMapping(produces = "application/json")
    public TipoCartao[] listarTipos() {
        return TipoCartao.values();
    }
}
