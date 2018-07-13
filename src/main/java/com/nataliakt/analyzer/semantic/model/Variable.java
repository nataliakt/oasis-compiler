package com.nataliakt.analyzer.semantic.model;

public class Variable<T> {

    private String type;
    private String name;
    private T value;

    public Variable(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Variable(String type, String name, T value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(type);
        stringBuilder.append(" ");
        stringBuilder.append(name);
        stringBuilder.append("=");
        stringBuilder.append(value);
        return stringBuilder.toString();
    }
}
