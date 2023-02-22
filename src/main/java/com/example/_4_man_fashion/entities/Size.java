package com.example._4_man_fashion.entities;

import javax.persistence.*;

import com.example._4_man_fashion.dto.SizeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "size", nullable = false, length = 10)
    private String sizeName;

    @Column(name = "status")
    private int status;

    @JsonIgnore
    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<ProductDetail> productDetails;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    public static Size fromDTO(SizeDto dto) {
        Size entity = new Size();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(1);

        return entity;
    }
}
