package br.com.bruno.APIcommerce.model;

import br.com.bruno.APIcommerce.dto.produto.ProdutoCreateRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoModel {
    List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo);
    Produto criar(ProdutoCreateRequest req);
    Produto obterPorId(Long id);
    Produto atualizar(Long id, ProdutoCreateRequest req);
    void deletar(Long id);
}
