package com.siinus.shared;

import org.jetbrains.annotations.NotNull;

public class StandardExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(@NotNull Throwable cause) {
        cause.printStackTrace();
    }
}
