package com.example._4_man_fashion.models;

import lombok.Data;

import java.util.List;

@Data
public class SearchOrder {
    private int offset;
    private int limit;
    private Integer status;
    private String searchTerm;
    private Integer delivery;
    private List<String> date;
    private Integer purchaseType;
}
