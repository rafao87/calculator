package com.example.calculator.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
public enum TypeFinancing {

    INTERNAL(4.0,  IntStream.range(1,49).boxed().collect(Collectors.toList())),
    EXTERNAL(6.5, List.of(12,24, 36, 48, 60));

    private Double tax;
    private List<Integer> intervals;

    TypeFinancing(Double tax, List<Integer> intervals) {
        this.tax = tax;
        this.intervals = intervals;
    }

    public Double getTax() {
        return tax;
    }

    public List<Integer> getIntervals() {
        return this.intervals;
    }

}
