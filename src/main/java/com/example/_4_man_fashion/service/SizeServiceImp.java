package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.dto.SizeDto;
import com.example._4_man_fashion.constants.Constant;
import com.example._4_man_fashion.dto.PageDTO;
import com.example._4_man_fashion.entities.Size;
import com.example._4_man_fashion.repositories.SizeRepository;
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
public class SizeServiceImp implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PageDTO<SizeDto> getAll(int offset, int limit, Integer status, String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Size> page = this.sizeRepository.getSizeByName(pageable, status, StringCommon.getLikeCondition(search));
        List<SizeDto> sizeDtoList = page.stream().map(u -> this.modelMapper.map(u, SizeDto.class)).collect(Collectors.toList());
        return new PageDTO<SizeDto>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                sizeDtoList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    @Override
    public List<Size> getListSize() {
        List<Size> lstSize = this.sizeRepository.findAll();
        return lstSize;
    }


    @Transactional
    public Size create(SizeDto sizeDto) {
        if(StringCommon.isNullOrBlank(sizeDto.getSizeName())) {
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID);
        }

        boolean isExistSizeName = sizeRepository.existsBySizeNameLike(sizeDto.getSizeName().trim());
        if(isExistSizeName) {
            throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên size"));
        }

        sizeDto.setCtime(LocalDateTime.now());
        sizeDto.setStatus(Constant.Status.ACTIVE);

        return this.sizeRepository.save(Size.fromDTO(sizeDto));

    }

    public Size update(SizeDto sizeDto) {
        Optional<Size> optionalSize = this.sizeRepository.findById(sizeDto.getId());
        if(optionalSize.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Size Id"));

        if(StringCommon.isNullOrBlank(sizeDto.getSizeName()))
            throw new DATNException(ErrorMessage.ARGUMENT_NOT_VALID.format("Tên size"));

        Size size = optionalSize.get();
        if(!size.getSizeName().equals(sizeDto.getSizeName())) {
            boolean isExistColorName = sizeRepository.existsBySizeNameLike(sizeDto.getSizeName().trim());
            if(isExistColorName) {
                throw new DATNException(ErrorMessage.DUPLICATE_PARAMS.format("Tên size"));
            }
        }

        size.setSizeName(sizeDto.getSizeName());
        size.setMtime(LocalDateTime.now());
        size.setStatus(sizeDto.getStatus());
        return this.sizeRepository.save(size);
    }

    public void delete(Integer id) {
        Optional<Size> optionalSize = this.sizeRepository.findById(id);
        if(optionalSize.isEmpty())
            throw new DATNException(ErrorMessage.OBJECT_NOT_FOUND.format("Size Id"));

        Size size = optionalSize.get();
        size.setMtime(LocalDateTime.now());
        size.setStatus(Constant.Status.INACTIVE);

        this.sizeRepository.save(size);
    }

    @Override
    @Transactional
    public boolean restore(Integer sizeId) {
        try {
            this.sizeRepository.restoreSize(sizeId);
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
