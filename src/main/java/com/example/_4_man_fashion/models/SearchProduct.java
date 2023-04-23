package com.example._4_man_fashion.models;

import lombok.Data;

import java.util.List;

@Data
public class SearchProduct {
    int limit;
    int offset;
    Integer status;
    String name;
    List<Integer> categories;
    List<Integer> materials;
    List<Integer> models;
    List<Integer> colors;
    List<Integer> sizes;
    List<Float> price;

    Integer sort;

}
