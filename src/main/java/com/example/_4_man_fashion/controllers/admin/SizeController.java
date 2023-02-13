package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.DTOs.ResponseDTO;
import com.example._4_man_fashion.DTOs.SizeDto;
import com.example._4_man_fashion.Service.SizeService;
import com.example._4_man_fashion.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/hiha")
@CrossOrigin("*")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping("size/getAllActive")
    public ResponseEntity<ResponseDTO> getAllSizeActive() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("size", this.sizeService.getAll()))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("size/getAllNoActive")
    public ResponseEntity<ResponseDTO> getAllSizeNoActive() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("size", this.sizeService.getAllNoActive()))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("size/searchByName")
    public ResponseEntity<ResponseDTO> getByName(@RequestParam("name") String name) {
        List<Size> sizes = this.sizeService.findByName(name);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("size", sizes))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("size/searchByNameNoActive")
    public ResponseEntity<ResponseDTO> getByNameNoActive(@RequestParam("name") String name) {
        List<Size> sizes = this.sizeService.findByNameNoActive(name);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("size", sizes))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("size/create")
    public ResponseEntity<ResponseDTO> createSize(@RequestBody SizeDto sizeDto){
        try {
            SizeDto sizeDto1 = this.sizeService.create(sizeDto);
            return  ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(CREATED)
                            .data(Map.of("size", sizeDto1))
                            .statusCode(CREATED.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("size/update")
    public ResponseEntity<ResponseDTO> updateSize(
            @RequestBody SizeDto sizeDto
    ){
        try {
            SizeDto sizeDto1 = this.sizeService.update(sizeDto);
            return  ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("size", sizeDto1))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("size/restore/{id}")
    public ResponseEntity<ResponseDTO> restore(
            @PathVariable("id") Integer sizeId
    ){
        boolean res = this.sizeService.restore(sizeId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Restore " + (res ? "success" : "false"))
                        .status(res ? OK : INTERNAL_SERVER_ERROR)
                        .data(Map.of("res",res ))
                        .statusCode(res ? OK.value() : INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }

    @DeleteMapping("size/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(
            @PathVariable("id") Integer sizeId
    ){
        boolean res = this.sizeService.delete(sizeId);
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
