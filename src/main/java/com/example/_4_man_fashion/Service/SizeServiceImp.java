package com.example._4_man_fashion.Service;

import com.example._4_man_fashion.DTOs.SizeDto;
import com.example._4_man_fashion.entities.Size;
import com.example._4_man_fashion.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SizeServiceImp implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;


    private SizeDto sizeMapToSizeDto(Size size) {
        return SizeDto.builder()
                .id(size.getId())
                .size(size.getSize())
                .status(size.getStatus())
                .build();
    }

    private Size sizeDtoMapToSize(SizeDto sizeDto) {
        Size size = Size.builder()
                .id(sizeDto.getId())
                .size(sizeDto.getSize())
                .status(1)
                .build();

        return size;
    }

    private Size updateSize(SizeDto sizeDto) {
        Size size = Size.builder()
                .size(sizeDto.getSize())
                .build();

        return size;
    }


    @Override
    public List<Size> getAll() {
        return this.sizeRepository.getAllActive();
    }

    @Override
    public List<Size> getAllNoActive() {
        return this.sizeRepository.getAllNoActive();
    }

    @Transactional
    public SizeDto create(SizeDto sizeDto) {
        Size size = this.sizeRepository.save(this.sizeDtoMapToSize(sizeDto));
        return sizeMapToSizeDto(size);
    }

    @Transactional
    public SizeDto update(SizeDto sizeDto) {
        Size size = this.sizeRepository.saveAndFlush(this.updateSize(sizeDto));
        return sizeMapToSizeDto(size);
    }

    @Override
    public List<Size> findByName(String name) {
        return this.sizeRepository.findByName(name);

    }

    @Override
    public List<Size> findByNameNoActive(String name) {

        return this.sizeRepository.findByNameNoActive(name);
    }

    @Override
    @Transactional
    public boolean delete(Integer sizeId) {
        try {
            this.sizeRepository.deleteSize(sizeId);
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
