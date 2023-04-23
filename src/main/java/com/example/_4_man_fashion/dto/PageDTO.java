package com.example._4_man_fashion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
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
    float minPrice;
    float maxPrice;

    public PageDTO(int totalPages, long totalElement, long offset, long limit, List<T> items, boolean isFirst, boolean isLast, boolean hasNext, boolean hasPrevious) {
        this.totalPages = totalPages;
        this.totalElement = totalElement;
        this.offset = offset;
        this.limit = limit;
        this.items = items;
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }
}
