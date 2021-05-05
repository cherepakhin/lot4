package ru.stm.lot4.statistic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Модель ошибки при валидации запроса")
public class ApiValidationError {
    @ApiModelProperty(dataType = "HttpStatus", name = "status", notes = "Статус ответа")
    private HttpStatus status;
    @ApiModelProperty(dataType = "String", name = "message", notes = "Сообщение об ошибке")
    private String message;
    @ApiModelProperty(dataType = "List<ValidationField>", name = "errors", notes = "Список ошибок")
    private List<ValidationField> errors = new ArrayList<>();

    public void addError(String field, String value, String error) {
        this.errors.add(new ValidationField(field, value, error));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "Модель поля валидации")
    public static class ValidationField {
        @ApiModelProperty(dataType = "String", name = "param", notes = "Имя параметра")
        private String param;
        @ApiModelProperty(dataType = "String", name = "value", notes = "Значение параметра")
        private String value;
        @ApiModelProperty(dataType = "String", name = "error", notes = "Сообщение об ошибке")
        private String error;
    }
}
