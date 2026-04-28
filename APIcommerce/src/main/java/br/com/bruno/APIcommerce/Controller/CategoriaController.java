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

    @GetMapping(produces = "application/json")
    public List<Categoria> listarOuBuscarPorNome(@RequestParam(required = false) String nome) {
        if (nome != null) {
            Categoria categoria = categoriarepository.findByNome(nome)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            return List.of(categoria);
        }

        return categoriarepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("categoria não encontradaaaa"));
    }

    @GetMapping(value = "/{id}/produtos", produces = "application/json")
    public List<Produto> listarProdutosPorCategoriaNomePreco(
            @PathVariable Long id,
            @RequestParam (required = false) String nome,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam (required = false) BigDecimal valorMaximo) {

        Categoria categoria = categoriarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("não hà produtos nessa categoria"));

        if (nome == null && valorMinimo == null && valorMaximo == null) {
            return produtorepository.findByCategoria(categoria);
        }

        if (nome != null && valorMinimo == null && valorMaximo == null) {
            return produtorepository.findByCategoriaAndNomeContaining(categoria, nome);
        }

        if (nome == null && valorMinimo != null && valorMaximo != null) {
            return produtorepository.findByCategoriaAndValorUnitarioBetween(categoria, valorMinimo, valorMaximo);
        }

        if (nome != null && valorMinimo != null && valorMaximo != null) {
            return produtorepository.findByCategoriaAndNomeContainingAndValorUnitarioBetween(categoria, nome, valorMinimo, valorMaximo);
        }

        throw new UnsupportedOperationException("Filtro indisponivel");
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Categoria salvar(@RequestBody Categoria categoria){
        return categoriarepository.save(categoria);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Categoria atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        return categoriarepository.save(categoria);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deletar(@PathVariable Long id) {
        categoriarepository.deleteById(id);
        /* ao configurar DTOs colocar mensagem de erro: "Não é possível excluir a categoria porque existem produtos vinculados"*/
    }
}
