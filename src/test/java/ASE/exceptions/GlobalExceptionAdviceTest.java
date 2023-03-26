package ASE.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionAdviceTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private GlobalExceptionAdvice globalExceptionAdvice;

    @Test
    void handleConflict_returnsResponseEntityWithConflictStatusAndErrorMessage() {
        // Arrange
        RuntimeException ex = new RuntimeException("Conflict");
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionAdvice.handleConflict(ex, request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("This should be application specific", responseEntity.getBody());
    }

    @Test
    void handleTransactionSystemException_returnsResponseStatusExceptionWithConflictStatusAndErrorMessage() {
        // Arrange
        Exception ex = new Exception("Transaction System Exception");
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Act
        ResponseStatusException responseStatusException = globalExceptionAdvice.handleTransactionSystemException(ex, request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseStatusException.getStatus());
        assertEquals("Transaction System Exception", responseStatusException.getReason());
        assertEquals(ex, responseStatusException.getCause());
        verify(logger).error("Request: {} raised {}", request.getRequestURL(), ex);
    }

    @Test
    void handleException_returnsResponseStatusExceptionWithInternalServerErrorStatusAndErrorMessage() {
        // Arrange
        Exception ex = new Exception("Internal Server Error");

        // Act
        ResponseStatusException responseStatusException = globalExceptionAdvice.handleException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatusException.getStatus());
        assertEquals("Internal Server Error", responseStatusException.getReason());
        assertEquals(ex, responseStatusException.getCause());
        verify(logger).error("Default Exception Handler -> caught:", ex);
    }
}
