package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.TipoCartao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipo-cartao")

public class TipoCartaoController {

    @GetMapping
    public TipoCartao[] listarTipos() {
        return TipoCartao.values();
    }
}
