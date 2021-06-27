package utility.model.SchemaParser.exception;

import static java.lang.String.format;

import java.time.Instant;

public class FileValidatorException extends RuntimeException {
    String message = "The following error happened at %s ";

    public FileValidatorException(String message) {
        super(format(message, Instant.now().toString(), message));
    }
}
