package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.Service.ColorServiceImpl;
import com.example._4_man_fashion.entities.Color;
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
    public ResponseEntity<ApiResponse<PageDTO<ColorDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "1") Integer status,
                                                                 @RequestParam(defaultValue = "") String search) {
        PageDTO<ColorDTO> result = colorService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/color/create")
    public ResponseEntity<ApiResponse<Color>> create(@Valid @RequestBody ColorDTO dto) {
        Color color = colorService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(color));
    }

    @PutMapping("/color/update")
    public ResponseEntity<ApiResponse<Color>> update(@Valid @RequestBody ColorDTO dto) {
        Color color = colorService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(color));
    }

    @DeleteMapping("/color/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        colorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
