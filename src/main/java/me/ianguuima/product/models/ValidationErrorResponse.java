package me.ianguuima.product.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationErrorResponse {

    private HttpStatus httpStatus;
    private String message;

}
