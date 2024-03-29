package dev.notypie.global.error.exceptions;

import dev.notypie.global.error.ArgumentError;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.util.List;

@Getter
public class JsonConvertException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -5267387154891496719L;

    private final ErrorCode errorCode;
    private final List<ArgumentError> detail;

    @Builder
    public JsonConvertException(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = argumentErrors;
    }
}