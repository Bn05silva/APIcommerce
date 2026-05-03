package br.com.bruno.APIcommerce.exception.categoria;

public class CategoriaNotFoundException extends RuntimeException {
        public CategoriaNotFoundException(String message) {
            super(message);
        }
}

