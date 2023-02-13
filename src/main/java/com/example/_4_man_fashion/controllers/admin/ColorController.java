package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.Service.ColorServiceImpl;
import com.example._4_man_fashion.utils.ApiResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class ColorController {
    @Autowired
    public ColorServiceImpl colorService;

    @GetMapping("color/getAll")
    public ResponseEntity<ApiResponse<PageDTO<ColorDTO>>> getAll(@RequestParam int offset, @RequestParam int limit, @RequestParam Integer status) {
        PageDTO<ColorDTO> result = colorService.getAll(offset, limit, status);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/color/create")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody ColorDTO dto) {
        colorService.create(dto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("/color/update")
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody ColorDTO dto) {
        colorService.update(dto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/color/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        colorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
