package com.example._4_man_fashion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDTO <T>{
    int totalPages;
    long totalElement;
    long offset;
    long limit;
    List<T> items;
    boolean isFirst;
    boolean isLast;
    boolean hasNext;
    boolean hasPrevious;
}
