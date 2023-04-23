package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.VoucherDTO;
import com.example._4_man_fashion.entities.Voucher;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface VoucherService {

    PageDTO<VoucherDTO> getAll(int offset, int limit, Integer status, String search);

    Voucher create(VoucherDTO voucherDTO);

    Voucher update(VoucherDTO voucherDTO);
    List<Voucher> getListVoucherByExpDay();

    void delete(Integer voucherId);
}
