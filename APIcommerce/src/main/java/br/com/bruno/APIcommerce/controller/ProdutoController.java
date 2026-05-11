package br.com.bruno.APIcommerce.controller;

import br.com.bruno.APIcommerce.exception.categoria.CategoriaNotFoundException;
import br.com.bruno.APIcommerce.exception.produto.ProdutoNotFoundException;
import br.com.bruno.APIcommerce.model.Categoria;
import br.com.bruno.APIcommerce.model.Produto;
import br.com.bruno.APIcommerce.repository.CategoriaRepository;
import br.com.bruno.APIcommerce.repository.ProdutoRepository;
import br.com.bruno.APIcommerce.dto.produto.ProdutoCreateRequest;
import br.com.bruno.APIcommerce.dto.produto.ProdutoResponse;
import br.com.bruno.APIcommerce.mapper.ProdutoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.net.URI;
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
    public ResponseEntity<List<ProdutoResponse>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo) {

        List<Produto> produtos;

        if (nome == null && valorMinimo == null && valorMaximo == null) {
            produtos = produtorepository.findAll();
        }

        else if (nome != null && valorMinimo == null && valorMaximo == null) {
            produtos = produtorepository.findByNomeContaining(nome);
        }

        else if (nome == null && valorMinimo != null && valorMaximo != null) {
            produtos = produtorepository.findByValorUnitarioBetween(valorMinimo, valorMaximo);
        }

        else if (nome != null && valorMinimo != null && valorMaximo != null) {
            produtos = produtorepository.findByNomeContainingAndValorUnitarioBetween(nome, valorMinimo, valorMaximo);
        }

        else {
            produtos = List.of();
        }

        List<ProdutoResponse> response = produtos.stream()
                .map(ProdutoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return produtorepository.findById(id)
                .map(produto -> ResponseEntity.ok(ProdutoMapper.toResponse(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProdutoResponse> salvar(@Valid @RequestBody ProdutoCreateRequest req) {
        Categoria categoria = categoriarepository.findById(req.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException("categoria não encontrada"));

        Produto produto = ProdutoMapper.toEntity(req, categoria);

        Produto produtoSalvo = produtorepository.save(produto);

        ProdutoResponse response = ProdutoMapper.toResponse(produtoSalvo);

        URI location = URI.create("/produtos/" + produtoSalvo.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoCreateRequest req) {
        Categoria categoria = categoriarepository.findById(req.getCategoriaId())
                .orElseThrow(() ->  new CategoriaNotFoundException("Categoria não encontrada"));

        Produto produtoExistente = produtorepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        produtoExistente.setNome(req.getNome());
        produtoExistente.setDescricao(req.getDescricao());
        produtoExistente.setFotoUrl(req.getFotoUrl());
        produtoExistente.setValorUnitario(req.getValorUnitario());
        produtoExistente.setCategoria(categoria);

        Produto produtoAtualizado = produtorepository.save(produtoExistente);

        ProdutoResponse response = ProdutoMapper.toResponse(produtoAtualizado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Produto produtoExistente = produtorepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        produtorepository.delete(produtoExistente);

        return ResponseEntity.noContent().build();
    }

}
