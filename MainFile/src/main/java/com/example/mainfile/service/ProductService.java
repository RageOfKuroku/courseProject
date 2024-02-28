package com.example.mainfile.service;

import com.example.mainfile.dto.StoreDto;
import com.example.mainfile.entity.StoreEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.OrderMapper;
import com.example.mainfile.mapper.StoreMapper;
import com.example.mainfile.model.ProductStatus;
import com.example.mainfile.repository.StoreRepository;
import com.example.mainfile.repository.ProductRepository;
import com.example.mainfile.dto.ProductDto;
import com.example.mainfile.entity.ProductEntity;
import com.example.mainfile.mapper.ProductMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final OrderMapper orderMapper;

    public ProductDto getProductById(Integer id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        ProductDto productDto = productMapper.toDto(productEntity);

        StoreDto storeDto = storeMapper.toDto(productEntity.getStore());
        productDto.setStore(storeDto);
        return productDto;
    }

    public List<ProductDto> getProductsByStoreId(Integer storeId) {
        StoreEntity store = storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store with this ID not found"));
        List<ProductEntity> products = productRepository.findByStore(store);
        return productMapper.toListDto(products);
    }

    public List<ProductDto> getAllProducts() {
        return productMapper.toListDto(productRepository.findAll());
    }

    public ProductDto addProduct(ProductDto product) {
        Integer storeId = product.getStore().getStoreId();
        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store with id " + storeId + " not found"));
        ProductEntity productEntity = productMapper.toEntity(product);
        productEntity.setStore(store);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return productMapper.toDto(savedProduct);
    }

    public void updateProduct(Integer id, ProductDto dto) {
        if(id != null){
            ProductEntity productEntity = productRepository.getReferenceById(id);
            productMapper.update(productEntity, dto);

            if (productEntity.getQuantity() == 0) {
                productEntity.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }

            productRepository.save(productEntity);
            productMapper.toDto(productEntity);
        }else {
            throw new ResourceNotFoundException("Product with this ID not found");
        }
    }




    public void deleteProduct(Integer id) {
        if(id != null) {
            productRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("Product with this ID not found");
        }
    }

    public void deleteAll(){
        productRepository.deleteAll();
    }
}
