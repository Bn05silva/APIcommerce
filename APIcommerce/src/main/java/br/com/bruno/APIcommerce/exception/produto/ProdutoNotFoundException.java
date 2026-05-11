package br.com.bruno.APIcommerce.exception.produto;

public class ProdutoNotFoundException extends RuntimeException {
    public ProdutoNotFoundException(String message) {
        super(message);
    }
}
