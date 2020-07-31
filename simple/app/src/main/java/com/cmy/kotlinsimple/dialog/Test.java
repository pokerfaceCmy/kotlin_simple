package com.cmy.kotlinsimple.dialog;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class Test {
    public static String name;
    private static Test test;

    private Test(@NotNull Builder builder) {
        this.name = builder.name;
    }

    private static Test getInstance(Builder builder) {
        if (test == null) {
            test = new Test(builder);
            Log.d("new Test", "==");
        } else {
            name = builder.name;
        }
        return test;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        String name;

        public Test build() {
            return getInstance(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
    }
}

