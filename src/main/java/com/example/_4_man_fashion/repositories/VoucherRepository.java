package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VoucherRepository extends JpaRepository<Voucher, Integer> {


    @Query("select voucher from Voucher voucher where" +
            " (:name is null or voucher.voucherName like :name) and (:status = -1 or voucher.status = :status) " +
            "order by voucher.ctime,voucher.startDate, voucher.voucherName, voucher.voucherCode")
    Page<Voucher> getVoucherByName(Pageable pageable, Integer status, String name);

    boolean existsByVoucherNameAndIdIsNot(Integer id,String voucherName);

    boolean existsByVoucherCodeAndIdIsNot(Integer id,String voucherCode);

    boolean existsByVoucherName(String voucherName);

    boolean existsByVoucherCode(String voucherCode);
}
