package com.example.financial;

import java.time.LocalDate;

public class History {
    private Type type;
    private LocalDate date;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public History() {
    }

    public History(Type type, LocalDate date) {
        this.type = type;
        this.date = date;
    }
}
