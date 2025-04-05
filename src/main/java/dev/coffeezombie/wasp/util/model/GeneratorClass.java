package dev.coffeezombie.wasp.util.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneratorClass {

    private Boolean entity;
    private Boolean dto;
    private Boolean repository;
    private Boolean service;
    private Boolean controller;
    private Boolean tests;

}
