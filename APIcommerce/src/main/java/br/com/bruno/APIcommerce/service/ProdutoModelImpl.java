package br.com.bruno.APIcommerce.service;

import br.com.bruno.APIcommerce.dto.produto.ProdutoCreateRequest;
import br.com.bruno.APIcommerce.exception.categoria.CategoriaNotFoundException;
import br.com.bruno.APIcommerce.exception.produto.ProdutoNotFoundException;
import br.com.bruno.APIcommerce.mapper.ProdutoMapper;
import br.com.bruno.APIcommerce.model.Categoria;
import br.com.bruno.APIcommerce.model.Produto;
import br.com.bruno.APIcommerce.model.ProdutoModel;
import br.com.bruno.APIcommerce.repository.CategoriaRepository;
import br.com.bruno.APIcommerce.repository.ProdutoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Profile("sql")
public class ProdutoModelImpl  implements ProdutoModel {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoModelImpl(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo) {
        if (nome == null && valorMinimo == null && valorMaximo == null) {
            return produtoRepository.findAll();
        }
        else if (nome != null && valorMinimo == null && valorMaximo == null) {
            return produtoRepository.findByNomeContaining(nome);
        }
        else if (nome == null && valorMinimo != null && valorMaximo != null) {
            return produtoRepository.findByValorUnitarioBetween(valorMinimo, valorMaximo);
        }
        else if (nome != null && valorMinimo != null && valorMaximo != null) {
            return produtoRepository.findByNomeContainingAndValorUnitarioBetween(nome, valorMinimo, valorMaximo);
        }

        return List.of();
    }

    @Override
    public Produto obterPorId(Long id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
    }

    @Override
    public Produto criar(ProdutoCreateRequest req) {
        Categoria categoria = categoriaRepository.findById(req.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada"));

        Produto produto = ProdutoMapper.toEntity(req, categoria);
        return produtoRepository.save(produto);
    }

    @Override
    public Produto atualizar(Long id, ProdutoCreateRequest req) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria não existe"));

        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não existe"));

        produtoExistente.setNome(req.getNome());
        produtoExistente.setDescricao(req.getDescricao());
        produtoExistente.setFotoUrl(req.getFotoUrl());
        produtoExistente.setValorUnitario(req.getValorUnitario());
        produtoExistente.setCategoria(categoria);

        return produtoRepository.save(produtoExistente);
    }

    @Override
    public void deletar(Long id) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não existe"));

        produtoRepository.delete(produtoExistente);
    }

    }
