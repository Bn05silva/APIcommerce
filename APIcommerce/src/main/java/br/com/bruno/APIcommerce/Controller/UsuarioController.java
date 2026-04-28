package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Usuario;
import br.com.bruno.APIcommerce.Repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuariorepository;

    public UsuarioController(UsuarioRepository usuariorepository) {
        this.usuariorepository = usuariorepository;
    }

    @GetMapping(produces = "application/json")
    public List<Usuario> listar() {
        return usuariorepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuariorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("erro ao identificar usuario"));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Usuario salvar(@RequestBody Usuario usuario) {
        return usuariorepository.save(usuario);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
                return usuariorepository.save(usuario);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deletar(@PathVariable Long id) {
        usuariorepository.deleteById(id);
    }

}
