package br.com.bruno.APIcommerce.Controller;

import br.com.bruno.APIcommerce.Model.Categoria;
import br.com.bruno.APIcommerce.Model.Produto;
import br.com.bruno.APIcommerce.Repository.CategoriaRepository;
import br.com.bruno.APIcommerce.Repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/produtos")

public class ProdutoController {
    private final ProdutoRepository produtorepository;
    private final CategoriaRepository categoriarepository;

    public ProdutoController(ProdutoRepository produtorepository, CategoriaRepository categoriarepository) {
        this.produtorepository = produtorepository;
        this.categoriarepository = categoriarepository;
    }

    @GetMapping(produces = "application/json")
    public List<Produto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo) {

        if (nome == null && valorMinimo == null && valorMaximo == null) {
            return produtorepository.findAll();
        }

        if (nome != null && valorMinimo == null && valorMaximo == null) {
            return produtorepository.findByNomeContaining(nome);
        }

        if (nome == null && valorMinimo != null && valorMaximo != null) {
            return produtorepository.findByValorUnitarioBetween(valorMinimo, valorMaximo);
        }

        if (nome != null && valorMinimo != null && valorMaximo != null) {
            return produtorepository.findByNomeContainingAndValorUnitarioBetween(nome, valorMinimo, valorMaximo);
        }

        throw new UnsupportedOperationException("Filtro indisponivel");

    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("produto não cadastrado no sistema"));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Produto salvar(@RequestBody Produto produto) {
        Categoria categoria = categoriarepository.findById(produto.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("categoria não encontrada"));
        produto.setCategoria(categoria);
        return produtorepository.save(produto);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        produto.setId(id);
        return produtorepository.save(produto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deletar(@PathVariable Long id) {
        produtorepository.deleteById(id);
    }

}
