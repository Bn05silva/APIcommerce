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

    @GetMapping
    public List<Usuario> listar() {
        return usuariorepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuariorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("erro ao identificar usuario"));
    }

    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {
        return usuariorepository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
                return usuariorepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuariorepository.deleteById(id);
    }
}
