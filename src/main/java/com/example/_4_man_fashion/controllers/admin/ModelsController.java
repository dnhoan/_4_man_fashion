package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.DTOs.ModelsDto;
import com.example._4_man_fashion.DTOs.ResponseDTO;
import com.example._4_man_fashion.Service.ModelsService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Color;
import com.example._4_man_fashion.entities.Models;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class ModelsController {

    @Autowired
    private ModelsService modelsService;

    @GetMapping("model/getAll")
    public ResponseEntity<ApiResponse<PageDTO<ModelsDto>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "1") Integer status,
                                                                 @RequestParam(defaultValue = "") String search) {
        PageDTO<ModelsDto> result = modelsService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }


    @PostMapping("/model/create")
    public ResponseEntity<ApiResponse<Models>> create(@Valid @RequestBody ModelsDto dto) {
        Models models = modelsService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(models));
    }


    @PutMapping("/model/update")
    public ResponseEntity<ApiResponse<Models>> update(@Valid @RequestBody ModelsDto dto) {
        Models models = modelsService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(models));
    }

    @DeleteMapping("/model/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        modelsService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
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

}
