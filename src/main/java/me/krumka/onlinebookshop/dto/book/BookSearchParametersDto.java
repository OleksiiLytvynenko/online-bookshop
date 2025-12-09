package me.krumka.onlinebookshop.dto.book;

public record BookSearchParametersDto(String[] titles, String[] authors, String[] isbns) {
}
