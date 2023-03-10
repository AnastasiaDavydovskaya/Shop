package by.tms.shop.services.impl;

import by.tms.shop.dto.CategoryDto;
import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.entities.Bucket;
import by.tms.shop.entities.User;
import by.tms.shop.exceptions.NotFoundException;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.exceptions.UploadFailedException;
import by.tms.shop.mapper.CategoryMapper;
import by.tms.shop.mapper.ProductMapper;
import by.tms.shop.repositories.ProductRepository;
import by.tms.shop.services.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService implements CrudService<ProductDto> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final UserService userService;
    private final BucketService bucketService;

    @Override
    public ProductDto create(ProductDto productDto) {
        if (productDto.getTitle() != null && productDto.getPrice() != null) {
            String nameOfPhoto = productDto.getNameOfPhoto();
            if (nameOfPhoto.endsWith(".png") || nameOfPhoto.endsWith(".jpg") || nameOfPhoto.endsWith(".jpeg")) {
                productRepository.save(productMapper.toEntity(productDto));
            } else {
                throw new UploadFailedException("Разрешается загружать только картинки.");
            }
        } else {
            throw new UploadFailedException("Продукт не найден.");
        }

        return productDto;
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден."));
    }

    @Override
    public ProductDto deleteById(Long id) {
        ProductDto productDto = findById(id);
        productRepository.deleteById(id);

        return productDto;
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        productRepository.save(productMapper.toEntity(productDto));

        return productDto;
    }

    public Bucket addToUserBucket(Long productId, String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден: " + login);
        }

        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }

        return bucket;
    }

    public ProductCreatedDto create(ProductCreatedDto productCreatedDto) {
        String nameOfPhoto = productCreatedDto.getNameOfPhoto();
        if (nameOfPhoto.endsWith(".png") || nameOfPhoto.endsWith(".jpg") || nameOfPhoto.endsWith(".jpeg")) {
            productRepository.save(productMapper.toEntity(productCreatedDto));
        } else {
            throw new UploadFailedException("Разрешается загружать только картинки.");
        }

        return productCreatedDto;
    }

    public Page<ProductDto> findAllInPage(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    public List<ProductDto> findByCategory(CategoryDto categoryDto) {
        return productRepository.findByCategory(categoryMapper.toEntity(categoryDto))
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

}
