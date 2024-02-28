package com.example.mainfile.service;

import com.example.mainfile.dto.StoreDto;
import com.example.mainfile.entity.StoreEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.StoreMapper;
import com.example.mainfile.repository.StoreRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreDto getStoreById(Integer id) {
        StoreEntity storeNotFound = storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store with this ID not found"));
        return storeMapper.toDto(storeNotFound);
    }

    public List<StoreDto> getAllStores() {
        return storeMapper.toListDto(storeRepository.findAll());
    }

    public void createStore(StoreDto store) {
        StoreEntity save = storeRepository.save(storeMapper.toEntity(store));
        storeMapper.toDto(save);
    }

    public void updateStore(Integer id, StoreDto dto) {
        if(id != null){
            StoreEntity storeEntity = storeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Store with this ID not found"));
            if(dto.getLogo() != null) {
                storeMapper.update(storeEntity, dto);
            } else {
                storeMapper.updateWithoutImage(storeEntity, dto);
            }
            storeRepository.save(storeEntity);
        }else {
            throw new ResourceNotFoundException("Store with this ID not found");
        }
    }

    public void deleteStore(Integer id) {
        if(id != null) {
            storeRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("Store with this ID not found");
        }
    }

    public void deleteAll(){
        storeRepository.deleteAll();
    }

    public List<StoreDto> searchStores(String searchName) {
        return storeMapper.toListDto(storeRepository.findByNameContaining(searchName));
    }

    public List<StoreDto> sortStoresAscending() {
        return storeMapper.toListDto(storeRepository.findAll(Sort.by(Sort.Direction.ASC, "rating")));
    }

    public List<StoreDto> sortStoresDescending() {
        return storeMapper.toListDto(storeRepository.findAll(Sort.by(Sort.Direction.DESC, "rating")));
    }
}
