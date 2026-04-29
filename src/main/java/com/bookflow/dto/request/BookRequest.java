package com.bookflow.dto.request;

public record BookRequest(String title, String author, String isbn, Integer totalCopies) {}