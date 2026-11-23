package edu.handong.week12.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank String title,
        @NotBlank String author,
        @NotNull @Min(0) Integer price) { }
