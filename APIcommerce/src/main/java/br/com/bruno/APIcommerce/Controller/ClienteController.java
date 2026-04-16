package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Cartao;
import br.com.bruno.APIcommerce.Model.Cliente;
import br.com.bruno.APIcommerce.Repository.CartaoRepository;
import br.com.bruno.APIcommerce.Repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/clientes")

public class ClienteController {
    private final ClienteRepository clienterepository;
    private final CartaoRepository cartaorepository;

    public ClienteController(ClienteRepository clienterepository, CartaoRepository cartaorepository) {
        this.clienterepository = clienterepository;
        this.cartaorepository = cartaorepository;
    }


    @GetMapping
    public List<Cliente> listar() {
        return clienterepository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe clientes com esse id"));
    }

    @GetMapping("/{id}/formas-de-pagamento")
    public List<Cartao> listarFormasDePagamento(@PathVariable Long id) {
        Cliente cliente = clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("nenhuma forma de pagamento"));

        return cliente.getCartoes();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Cliente salvar(@RequestBody Cliente cliente) {
        return clienterepository.save(cliente);
    }

    @PostMapping("/{id}/formas-de-pagamento")
    public Cartao adicionarFormaDePagamento(@PathVariable Long id, @RequestBody Cartao cartao) {
        Cliente cliente = clienterepository.findById(id)
                .orElseThrow(() -> new RuntimeException("cliente não encontrado"));

        cartao.setCliente(cliente);
        return cartaorepository.save(cartao);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return clienterepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienterepository.deleteById(id);
    }
}
