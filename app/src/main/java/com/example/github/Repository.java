package com.example.github;

public class Repository {
    String name;
    String language;
    String description;

    public Repository(String reponame, String language, String description) {
        this.name = reponame;
        this.language = language;
        this.description = description;
    }
}
