package com.example._4_man_fashion.controllers.admin;

import com.example._4_man_fashion.Service.AccountService;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.AccountDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.ResponseDTO;
import com.example._4_man_fashion.entities.Account;
import com.example._4_man_fashion.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping("account/getAll")
    public ResponseEntity<ApiResponse<PageDTO<AccountDTO>>> getAll(@RequestParam(defaultValue = "0") int offset,
                                                                   @RequestParam(defaultValue = "10") int limit,
                                                                   @RequestParam(defaultValue = "1") Integer status,
                                                                   @RequestParam(defaultValue = "") String search) {
        PageDTO<AccountDTO> result = accountService.getAll(offset, limit, status, search);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/account/create")
    public ResponseEntity<ApiResponse<Account>> create(@Valid @RequestBody AccountDTO accountDTO) {
        Account account = accountService.create(accountDTO);
        return ResponseEntity.ok(ApiResponse.success(account));
    }

    @PutMapping("/account/update")
    public ResponseEntity<ApiResponse<Account>> update(@Valid @RequestBody AccountDTO accountDTO) {
        Account accountUpdate = accountService.update(accountDTO);
        return ResponseEntity.ok(ApiResponse.success(accountUpdate));
    }

    @DeleteMapping("/account/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        accountService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("account/restore/{id}")
    public ResponseEntity<ResponseDTO> restore(
            @PathVariable("id") Integer accountId
    ) {
        boolean res = this.accountService.restore(accountId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Restore " + (res ? "success" : "false"))
                        .status(res ? OK : INTERNAL_SERVER_ERROR)
                        .data(Map.of("res", res))
                        .statusCode(res ? OK.value() : INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }


}
