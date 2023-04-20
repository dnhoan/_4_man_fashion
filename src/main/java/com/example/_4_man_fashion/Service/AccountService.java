package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.AccountDTO;
import com.example._4_man_fashion.dto.ColorDTO;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    PageDTO<AccountDTO> getAll(int offset, int limit, Integer status, String search);
    Account create(AccountDTO accountDTO);
    Account update(AccountDTO accountDTO);
    void delete(Integer id);
    Account findByPhoneNumber(String phoneNumber);
    List<Account> findByEmail(String email);
    boolean restore(Integer sizeId);

}
