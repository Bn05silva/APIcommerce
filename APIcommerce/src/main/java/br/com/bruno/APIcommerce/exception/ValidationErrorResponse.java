package br.com.bruno.APIcommerce.exception;

import java.util.List;

public class ValidationErrorResponse {
    private String error;
    private List<FieldErrorResponse> fields;

    public ValidationErrorResponse(String error, List<FieldErrorResponse> fields){
        this.error = error;
        this.fields = fields;
    }

    public String getError() {
        return error;
    }

    public List<FieldErrorResponse> getFields() {
        return fields;
    }

}
