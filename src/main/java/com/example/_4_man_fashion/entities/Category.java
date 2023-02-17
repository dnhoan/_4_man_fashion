package com.example._4_man_fashion.entities;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import com.example._4_man_fashion.dto.CategoryDTO;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 255)
    private String categoryName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    public static Category fromDTO(CategoryDTO dto) {
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(1);

        return entity;
    }
}