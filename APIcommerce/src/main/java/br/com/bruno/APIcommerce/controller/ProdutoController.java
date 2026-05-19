package br.com.bruno.APIcommerce.controller;

import br.com.bruno.APIcommerce.model.Produto;
import br.com.bruno.APIcommerce.model.ProdutoModel;
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
    private final ProdutoModel produtoModel;

    public ProdutoController(ProdutoModel produtoModel) {
        this.produtoModel = produtoModel;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProdutoResponse>> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo) {

        List<ProdutoResponse> response = produtoModel.buscar(nome, valorMinimo, valorMaximo)
                .stream()
                .map(ProdutoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProdutoResponse> obterPorId(@PathVariable Long id) {
        Produto produto = produtoModel.obterPorId(id);
        ProdutoResponse response = ProdutoMapper.toResponse(produto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoCreateRequest req) {
        Produto produto = produtoModel.criar(req);
        ProdutoResponse response = ProdutoMapper.toResponse(produto);
        URI location = URI.create("/produtos/" + produto.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoCreateRequest req) {
        Produto produto = produtoModel.atualizar(id, req);
        ProdutoResponse response = ProdutoMapper.toResponse(produto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoModel.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
