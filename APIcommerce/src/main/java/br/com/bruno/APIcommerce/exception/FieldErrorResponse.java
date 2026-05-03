package br.com.bruno.APIcommerce.exception;

public class FieldErrorResponse {
    private String name;
    private String message;

    public FieldErrorResponse(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

}
