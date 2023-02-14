package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.MaterialDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.Service.MaterialService;
import com.example._4_man_fashion.entities.Material;
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
    public ResponseEntity<ApiResponse<PageDTO<MaterialDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                    @RequestParam(defaultValue = "10") int limit,
                                                                    @RequestParam(defaultValue = "1") Integer status,
                                                                    @RequestParam(defaultValue = "") String search) {
        PageDTO<MaterialDTO> result = materialService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/material/create")
    public ResponseEntity<ApiResponse<Material>> create(@Valid @RequestBody MaterialDTO dto) {
        Material material = materialService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(material));
    }

    @PutMapping("/material/update")
    public ResponseEntity<ApiResponse<Material>> update(@Valid @RequestBody MaterialDTO dto) {
        Material material = materialService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(material));
    }

    @DeleteMapping("/material/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        materialService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
