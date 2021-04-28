package ru.stm.lot4.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiValidationError {
    private HttpStatus status;
    private String message;
    private List<ValidationField> errors = new ArrayList<>();

    public ApiValidationError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public void addError(String field, String value, String error) {
        this.errors.add(new ValidationField(field, value, error));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationField {
        private String param;
        private String value;
        private String error;
    }
}
