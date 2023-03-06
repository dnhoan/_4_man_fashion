package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.VoucherDTO;
import com.example._4_man_fashion.entities.Voucher;

public interface VoucherService {

    PageDTO<VoucherDTO> getAll(int offset, int limit, Integer status, String search);

    Voucher create(VoucherDTO voucherDTO);

    Voucher update(VoucherDTO voucherDTO);

    void delete(Integer voucherId);
}
