package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.MaterialDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.Service.MaterialService;
import com.example._4_man_fashion.utils.ApiResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class MaterialController {
    @Autowired
    public MaterialService materialService;

    @GetMapping("material/getAll")
    public ResponseEntity<ApiResponse<PageDTO<MaterialDTO>>> getAll(@RequestParam int offset, @RequestParam int limit, @RequestParam Integer status) {
        PageDTO<MaterialDTO> result = materialService.getAll(offset, limit, status);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/material/create")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody MaterialDTO dto) {
        materialService.create(dto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("/material/update")
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody MaterialDTO dto) {
        materialService.update(dto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/material/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        materialService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
