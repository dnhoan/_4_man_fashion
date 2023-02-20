package com.example._4_man_fashion.entities;

import javax.persistence.*;

import com.example._4_man_fashion.dto.ModelsDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "models")
public class Models {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "model_name", nullable = false, length = 100)
    private String modelsName;

    @Column(name = "status")
    private int status;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;


    public static Models fromDTO(ModelsDto dto) {
        Models entity = new Models();
        BeanUtils.copyProperties(dto, entity);
        entity.setCtime(dto.getCtime());
        entity.setMtime(dto.getMtime());
        entity.setStatus(1);

        return entity;
    }

}
