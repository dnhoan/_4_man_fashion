package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.dto.VoucherDTO;
import com.example._4_man_fashion.entities.Voucher;
import com.example._4_man_fashion.repositories.VoucherRepository;
import com.example._4_man_fashion.utils.DATNException;
import com.example._4_man_fashion.utils.ErrorMessage;
import com.example._4_man_fashion.utils.StringCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService{

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<VoucherDTO> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Voucher> page = this.voucherRepository.getVoucherByName(pageable, status, StringCommon.getLikeCondition(search));
        List<VoucherDTO> voucherDTOList = page.stream().map(u -> this.modelMapper.map(u, VoucherDTO.class)).collect(Collectors.toList());
        return new PageDTO<VoucherDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                voucherDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Transactional
    public Voucher create(VoucherDTO voucherDTO) {
        if(StringCommon.isNullOrBlank(voucherDTO.getVoucherName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistVoucherName = voucherRepository.existsByVoucherNameLike(voucherDTO.getVoucherName().trim());
        if(isExistVoucherName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên voucher"));
        }
        boolean isExistVoucherCode = voucherRepository.existsByVoucherCodeLike(voucherDTO.getVoucherCode().trim());
        if(isExistVoucherCode) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Mã voucher"));
        }

        voucherDTO.setCtime(LocalDateTime.now());
        voucherDTO.setStatus(Constant.Status.ACTIVE);

        return this.voucherRepository.save(Voucher.fromDTO(voucherDTO));

    }

    public Voucher update(VoucherDTO voucherDTO) {
        Optional<Voucher> optionalSize = this.voucherRepository.findById(voucherDTO.getId());
        if(optionalSize.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Voucher Id"));

        if(StringCommon.isNullOrBlank(voucherDTO.getVoucherName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Tên Voucher"));

        Voucher voucher = optionalSize.get();
        if(!voucher.getVoucherName().equals(voucherDTO.getVoucherName())) {
            boolean isExistVoucherName = voucherRepository.existsByVoucherNameLike(voucher.getVoucherName().trim());
            if(isExistVoucherName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên Voucher"));
            }
        }

        voucher.setVoucherName(voucherDTO.getVoucherName());
        voucher.setMtime(LocalDateTime.now());
        voucher.setStatus(voucher.getStatus());
        return this.voucherRepository.save(voucher);
    }

    public void delete(Integer id) {
        Optional<Voucher> optionalSize = this.voucherRepository.findById(id);
        if(optionalSize.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Voucher Id"));

        Voucher voucher = optionalSize.get();
        voucher.setMtime(LocalDateTime.now());
        voucher.setStatus(Constant.Status.INACTIVE);

        this.voucherRepository.save(voucher);
    }
}
