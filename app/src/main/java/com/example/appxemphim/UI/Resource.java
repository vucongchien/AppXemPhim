package com.example.appxemphim.UI;

public class Resource<T>{
    public final String status;
    public final T data;
    public final String message;

    public Resource(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> loading() {
        return new Resource<>("LOADING", null, null);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>("SUCCESS", data, null);
    }

    public static <T> Resource<T> error(String message) {
        return new Resource<>("ERROR", null, message);
    }
}
