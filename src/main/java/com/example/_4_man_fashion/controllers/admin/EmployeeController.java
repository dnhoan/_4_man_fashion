package com.example._4_man_fashion.controllers.admin;

import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example._4_man_fashion.Service.EmployeeServiceImpl;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.EmployeeDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Employee;
import com.example._4_man_fashion.utils.ApiResponse;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin("*")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/employee/getAll")
    public ResponseEntity<ApiResponse<PageDTO<EmployeeDTO>>> getPageEmployee(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageDTO<EmployeeDTO> items = this.employeeService.getAll(offset, limit);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/employee/findByStatus")
    public ResponseEntity<ApiResponse<PageDTO<EmployeeDTO>>> findByStatus(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status", defaultValue = "1") int status) {
        try {
            offset = offset < 0 ? 0 : offset;
            Pageable pageable;
            pageable = PageRequest.of(offset, limit, Sort.by("status"));
            PageDTO<EmployeeDTO> pageEmployee = this.employeeService.findByStatus(pageable, status);
            return ResponseEntity.ok(ApiResponse.success(pageEmployee));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/employee/findByKey")
    public ResponseEntity<ApiResponse<PageDTO<EmployeeDTO>>> findByKey(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(defaultValue = "") String valueSearch) {
        try {
            offset = offset < 0 ? 0 : offset;
            Pageable pageable;
            pageable = PageRequest.of(offset, limit, Sort.by("id"));
            PageDTO<EmployeeDTO> pageEmployee = this.employeeService.findByKey(pageable, valueSearch);
            return ResponseEntity.ok(ApiResponse.success(pageEmployee));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/employee/create")
    public ResponseEntity<ApiResponse<Employee>> create(@Valid @RequestBody EmployeeDTO dto) throws SQLException {
        Employee employee = this.employeeService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(employee));
    }

    @PutMapping("/employee/update")
    public ResponseEntity<ApiResponse<Employee>> update(@Valid @RequestBody EmployeeDTO dto) throws SQLException {
        Employee employee = this.employeeService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(employee));
    }

    @PutMapping("/employee/restore/{id}")
    public ResponseEntity<ApiResponse<Void>> restore(@PathVariable Integer id) throws SQLException {
        this.employeeService.restore(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) throws SQLException {
        this.employeeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
