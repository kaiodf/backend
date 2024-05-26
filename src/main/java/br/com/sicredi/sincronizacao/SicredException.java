package br.com.sicredi.sincronizacao;

public class SicredException extends RuntimeException {

    public SicredException() {
    }

    public SicredException(String message) {
        super(message);
    }

    public SicredException(String message, Throwable cause) {
        super(message, cause);
    }

    public SicredException(Throwable cause) {
        super(cause);
    }

    public SicredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
