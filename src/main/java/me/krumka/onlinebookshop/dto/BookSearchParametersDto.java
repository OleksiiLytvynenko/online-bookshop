package me.krumka.onlinebookshop.dto;

public record BookSearchParametersDto(String[] titles, String[] authors, String[] isbns) {
}
