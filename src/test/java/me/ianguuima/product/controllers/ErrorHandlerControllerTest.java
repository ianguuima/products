package me.ianguuima.product.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class ErrorHandlerControllerTest {

    @SpyBean
    private ErrorHandlerController errorHandlerController;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void handleMethodArgumentNotValidException_shouldReturnValidationErrorResponse_whenExceptionIsThrown() {
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

        FieldError fieldError = new FieldError(
                "random Object",
                "random Field",
                "This field can't be modified."
        );
        BDDMockito.when(exception.getFieldError()).thenReturn(fieldError);

        var validationErrorResponse = errorHandlerController
                .handleMethodArgumentNotValidException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, validationErrorResponse.getStatusCode());
        assertEquals("This field can't be modified.", Objects.requireNonNull(validationErrorResponse.getBody()).getMessage());
    }

}