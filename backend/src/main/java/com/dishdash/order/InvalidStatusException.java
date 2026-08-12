package com.dishdash.order;

public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException(String value) {
        super("Invalid order status: " + value);
    }
}
