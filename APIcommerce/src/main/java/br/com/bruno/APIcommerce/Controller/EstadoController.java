package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Cidade;
import br.com.bruno.APIcommerce.Model.Estado;
import br.com.bruno.APIcommerce.Repository.CidadeRepository;
import br.com.bruno.APIcommerce.Repository.EstadoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    private final EstadoRepository estadorepository;
    private final CidadeRepository cidaderepository;

    public EstadoController(EstadoRepository estadorepository, CidadeRepository cidaderepository) {
        this.estadorepository = estadorepository;
        this.cidaderepository = cidaderepository;
    }

    @GetMapping(produces = "application/json")
    public List<Estado> listar() {
        return estadorepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Estado listarPorId(@PathVariable Long id) {
        return estadorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado não cadastrado"));
    }

    @GetMapping(value = "{id}/cidades", produces = "application/json")
    public List<Cidade> listarCidadePorId(@PathVariable Long id) {
        Estado estado = estadorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("estado não existe"));

        return estado.getCidades();
    }

}
