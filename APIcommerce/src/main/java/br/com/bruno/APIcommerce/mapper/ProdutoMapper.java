package br.com.bruno.APIcommerce.mapper;

import br.com.bruno.APIcommerce.Model.Categoria;
import br.com.bruno.APIcommerce.Model.Produto;
import br.com.bruno.APIcommerce.dto.Produto.ProdutoCreateRequest;
import br.com.bruno.APIcommerce.dto.Produto.ProdutoResponse;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoCreateRequest req, Categoria categoria) {
        Produto produto = new Produto();
        produto.setNome(req.getNome());
        produto.setDescricao(req.getDescricao());
        produto.setFotoUrl(req.getFotoUrl());
        produto.setValorUnitario(req.getValorUnitario());
        produto.setCategoria(categoria);
        return produto;
    }

    public static ProdutoResponse toResponse(Produto p) {
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getFotoUrl(),
                p.getDataCadastro(),
                p.getDataUltimaAtualizacao(),
                p.getValorUnitario(),
                p.getCategoria() != null ? p.getCategoria().getId() : null
        );
    }

}
