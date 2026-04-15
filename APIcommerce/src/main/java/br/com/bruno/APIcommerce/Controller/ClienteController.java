package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Categoria;
import br.com.bruno.APIcommerce.Model.Cliente;
import br.com.bruno.APIcommerce.Repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")

public class ClienteController {
    private final ClienteRepository clienterepository;

    public ClienteController(ClienteRepository clienterepository) {
        this.clienterepository = clienterepository;
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

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Cliente salvar(@RequestBody Cliente cliente) {
        return clienterepository.save(cliente);
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
