package br.com.bruno.APIcommerce.Model;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Cliente {
    private String fotoUrl;
    private LocalDate dataNascimento;
    private String cpf;

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


}
