package com.haydenmuhl.nes.processor;

public class UnsupportedInstructionException extends RuntimeException {
    public UnsupportedInstructionException() {
        super();
    }

    public UnsupportedInstructionException(String message) {
        super(message);
    }

    public UnsupportedInstructionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedInstructionException(Throwable cause) {
        super(cause);
    }
}
