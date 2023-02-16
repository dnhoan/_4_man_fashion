package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.DTOs.ResponseDTO;
import com.example._4_man_fashion.DTOs.SizeDto;
import com.example._4_man_fashion.Service.SizeService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Size;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping("size/getAll")
    public ResponseEntity<ApiResponse<PageDTO<SizeDto>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                  @RequestParam(defaultValue = "10") int limit,
                                                                  @RequestParam(defaultValue = "1") Integer status,
                                                                  @RequestParam(defaultValue = "") String search) {
        PageDTO<SizeDto> result = sizeService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }


    @PostMapping("/size/create")
    public ResponseEntity<ApiResponse<Size>> create(@Valid @RequestBody SizeDto dto) {
        Size size = sizeService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(size));
    }


    @PutMapping("/size/update")
    public ResponseEntity<ApiResponse<Size>> update(@Valid @RequestBody SizeDto dto) {
        Size size = sizeService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(size));
    }

    @DeleteMapping("/size/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        sizeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
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

}
