package br.com.bruno.APIcommerce.service;

import br.com.bruno.APIcommerce.dto.produto.ProdutoCreateRequest;
import br.com.bruno.APIcommerce.exception.produto.ProdutoNotFoundException;
import br.com.bruno.APIcommerce.mapper.ProdutoMapper;
import br.com.bruno.APIcommerce.model.Categoria;
import br.com.bruno.APIcommerce.model.Produto;
import br.com.bruno.APIcommerce.model.ProdutoModel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("mem")
public class ProdutoModelMemoria implements ProdutoModel {
    private final List<Produto> banco = new ArrayList<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Produto criar(ProdutoCreateRequest req) {
        Long novoId = seq.incrementAndGet();
        Produto p = ProdutoMapper.toEntity(req, null);
        p.setId(novoId);

        p.setDataCadastro(LocalDateTime.now());
        p.setDataUltimaAtualizacao(LocalDateTime.now());

        if (req.getCategoriaId() != null) {
            Categoria categoriaFake = new Categoria();
            categoriaFake.setId(req.getCategoriaId());
            p.setCategoria(categoriaFake);
        }

        banco.add(p);
        return p;
    }

    @Override
    public Produto obterPorId(Long id) {
        return banco.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
    }

    @Override
    public List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return banco.stream()
                .filter(p ->(nome == null || p.getNome().contains(nome)) &&
                        (valorMinimo == null || p.getValorUnitario().compareTo(valorMinimo) >= 0) &&
                        (valorMaximo == null || p.getValorUnitario().compareTo(valorMaximo) <= 0))
                .toList();
    }

    @Override
    public Produto atualizar(Long id, ProdutoCreateRequest req) {
        Produto produtoExistente = obterPorId(id);
        produtoExistente.setNome(req.getNome());
        produtoExistente.setDescricao(req.getDescricao());
        produtoExistente.setFotoUrl(req.getFotoUrl());
        produtoExistente.setValorUnitario(req.getValorUnitario());
        return produtoExistente;
    }

    @Override
    public void deletar(Long id) {
        Produto produtoExistente = obterPorId(id);
        banco.remove(produtoExistente);
    }
}
