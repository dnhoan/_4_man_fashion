package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.DTOs.ModelsDto;
import com.example._4_man_fashion.DTOs.ResponseDTO;
import com.example._4_man_fashion.Service.ModelsService;
import com.example._4_man_fashion.entities.Models;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/hiha")
@CrossOrigin("*")
public class ModelsController {

    @Autowired
    private ModelsService modelsService;

    @GetMapping("models/getAllActive")
    public ResponseEntity<ResponseDTO> getAllModelActive() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("model", this.modelsService.getAll()))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("models/getAllNoActive")
    public ResponseEntity<ResponseDTO> getAllModelNoActive() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("model", this.modelsService.getAllNoActive()))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("models/searchByName")
    public ResponseEntity<ResponseDTO> getByName(@RequestParam("name") String name) {
        List<Models> modelsDtos = this.modelsService.findByName(name);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("model", modelsDtos))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("models/searchByNameNoActive")
    public ResponseEntity<ResponseDTO> getByNameNoActive(@RequestParam("name") String name) {
        List<Models> modelsDtos = this.modelsService.findByNameNoActive(name);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("model", modelsDtos))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("models/create")
    public ResponseEntity<ResponseDTO> createModel(@RequestBody ModelsDto modelsDto){
        try {
            ModelsDto modelsDto1 = this.modelsService.create(modelsDto);
            return  ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(CREATED)
                            .data(Map.of("model", modelsDto1))
                            .statusCode(CREATED.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("models/update")
    public ResponseEntity<ResponseDTO> update(
            @RequestBody ModelsDto modelsDto
    ){
        try {
            ModelsDto modelsDto1 = this.modelsService.update(modelsDto);
            return  ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("model", modelsDto1))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("models/restore/{id}")
    public ResponseEntity<ResponseDTO> restore(
            @PathVariable("id") Integer modelId
    ){
        boolean res = this.modelsService.restore(modelId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Restore " + (res ? "success" : "false"))
                        .status(res ? OK : INTERNAL_SERVER_ERROR)
                        .data(Map.of("res",res ))
                        .statusCode(res ? OK.value() : INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }

    @DeleteMapping("models/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(
            @PathVariable("id") Integer modelId
    ){
        boolean res = this.modelsService.delete(modelId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Delete " + (res ? "success" : "false"))
                        .status(res ? OK : INTERNAL_SERVER_ERROR)
                        .data(Map.of("res",res ))
                        .statusCode(res ? OK.value() : INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }
}
