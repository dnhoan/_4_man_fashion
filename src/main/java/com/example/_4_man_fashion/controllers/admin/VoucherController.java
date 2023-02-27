package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.VoucherService;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.VoucherDTO;
import com.example._4_man_fashion.entities.Voucher;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("hhh")
@CrossOrigin("*")
public class VoucherController {

    @Autowired
    VoucherService voucherService;


    @GetMapping("/voucher/getAll")
    public ResponseEntity<ApiResponse<PageDTO<VoucherDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                   @RequestParam(defaultValue = "10") int limit,
                                                                   @RequestParam(defaultValue = "1") Integer status,
                                                                   @RequestParam(defaultValue = "") String search) {
        PageDTO<VoucherDTO> result = voucherService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
}

    @PostMapping("/voucher/create")
    public ResponseEntity<ApiResponse<Voucher>> create(@Valid @RequestBody VoucherDTO dto) {
        Voucher voucher = voucherService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(voucher));
    }


    @PutMapping("/voucher/update")
    public ResponseEntity<ApiResponse<Voucher>> update(@Valid @RequestBody VoucherDTO dto) {
        Voucher voucher = voucherService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(voucher));
    }

    @DeleteMapping("/voucher/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        voucherService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }


}
