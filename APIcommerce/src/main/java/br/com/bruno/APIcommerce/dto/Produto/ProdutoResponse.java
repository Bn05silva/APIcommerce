package br.com.bruno.APIcommerce.dto.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private String fotoUrl;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;
    private BigDecimal valorUnitario;
    private Long categoriaId;

    public ProdutoResponse(Long id, String nome, String descricao, String fotoUrl, LocalDateTime dataCadastro, LocalDateTime dataUltimaAtualizacao, BigDecimal valorUnitario, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.fotoUrl = fotoUrl;
        this.dataCadastro = dataCadastro;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.valorUnitario = valorUnitario;
        this.categoriaId = categoriaId;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

}
