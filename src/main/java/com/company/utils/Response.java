package com.company.utils;

public class Response<T> {
    private final boolean successful;
    private final T msg;

    public Response(boolean successful, T msg) {
        this.msg = msg;
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public T getMsg() {
        return msg;
    }
}
