package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Categoria;
import br.com.bruno.APIcommerce.Model.Produto;
import br.com.bruno.APIcommerce.Repository.CategoriaRepository;
import br.com.bruno.APIcommerce.Repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/categorias")

public class CategoriaController {
    private final CategoriaRepository categoriarepository;
    private final ProdutoRepository produtorepository;

    public CategoriaController(CategoriaRepository categoriarepository, ProdutoRepository produtorepository) {
        this.categoriarepository = categoriarepository;
        this.produtorepository = produtorepository;
    }

    @GetMapping
    public List<Categoria> listar() {
        return categoriarepository.findAll();
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("categoria não encontradaaaa"));
    }

    @GetMapping("/{id}/produtos")
    public List<Produto> listarProdutosPorCategoriaNomePreco(
            @PathVariable Long id,
            @RequestParam (required = false) String nome,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam (required = false) BigDecimal valorMaximo) {

        Categoria categoria = categoriarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("não hà produtos nessa categoria"));

        if (nome == null && valorMaximo == null && valorMinimo == null) {
            return produtorepository.findByCategoria(categoria);
        }

        if (nome != null && valorMaximo == null && valorMinimo == null) {
            return produtorepository.findByCategoriaAndNomeContaining(categoria, nome);
        }

        if (nome == null && valorMaximo != null && valorMinimo != null) {
            return produtorepository.findByCategoriaAndValorUnitarioBetween(categoria, valorMinimo, valorMaximo);
        }

        throw new UnsupportedOperationException("Filtro indisponivel");

    }

    @GetMapping("/nome")
    public Categoria buscarPorNome(@RequestParam String nome) {
        return categoriarepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("nome não encontrado"));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Categoria salvar(@RequestBody Categoria categoria){
        return categoriarepository.save(categoria);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        return categoriarepository.save(categoria);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        categoriarepository.deleteById(id);
    }
}
