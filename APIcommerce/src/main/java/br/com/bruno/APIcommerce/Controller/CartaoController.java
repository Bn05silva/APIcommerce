package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Cartao;
import br.com.bruno.APIcommerce.Repository.CartaoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tiposcatao")

public class CartaoController {
    private final CartaoRepository cartaorepository;

    public CartaoController(CartaoRepository cartaorepository) {
        this.cartaorepository = cartaorepository;
    }

    @GetMapping
    public List<Cartao> listar() {
        return  cartaorepository.findAll();
    }
}
