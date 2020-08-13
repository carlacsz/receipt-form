package com.company.utils;

public class Response {
    private final boolean successful;
    private final String msg;

    public Response(boolean successful, String msg) {
        this.msg = msg;
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMsg() {
        return msg;
    }
}
