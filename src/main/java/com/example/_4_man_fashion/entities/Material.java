package com.example._4_man_fashion.entities;

import com.example._4_man_fashion.dto.MaterialDTO;
import javax.persistence.*;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "material_name", nullable = false, length = 225)
    private String materialName;

    @Column(name = "status")
    private int status;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    public static Material fromDTO(MaterialDTO dto) {
        Material entity = new Material();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(1);

        return entity;
    }
}
