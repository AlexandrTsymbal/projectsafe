package com.example.project;

import java.util.ArrayList;
import java.util.List;

public class Question {
    String name;
    String url;

    Question(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
