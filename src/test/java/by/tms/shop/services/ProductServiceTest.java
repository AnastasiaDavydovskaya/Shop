package by.tms.shop.services;

import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.dto.UserDto;
import by.tms.shop.entities.*;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.CategoryMapper;
import by.tms.shop.mapper.ProductMapper;
import by.tms.shop.repositories.BucketRepository;
import by.tms.shop.repositories.ProductRepository;
import by.tms.shop.repositories.UserRepository;
import by.tms.shop.services.impl.BucketService;
import by.tms.shop.services.impl.ProductService;
import by.tms.shop.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BucketService bucketService;
    @Mock
    private BucketRepository bucketRepository;
    @InjectMocks
    private ProductService productService;
    private ProductDto productDto;
    private ProductCreatedDto productCreatedDto;
    private Product product;

    @BeforeEach
    public void init() {
        productDto = ProductDto.builder()
                .id(1L)
                .title("test")
                .nameOfPhoto("test")
                .price(1.0)
                .build();

        product = Product.builder()
                .id(1L)
                .title("test")
                .nameOfPhoto("test")
                .price(1.0)
                .build();

        productCreatedDto = ProductCreatedDto.builder()
                .title("test")
                .nameOfPhoto("test")
                .price(1.0)
                .build();
    }

    @Test
    void testCreateDto() {
        ProductDto actual = productService.create(productDto);

        assertEquals(productDto, actual);
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<ProductDto> actual = productService.findAll();

        assertEquals(List.of(), actual);
    }

    @Test
    void testFindByIdThrowsNotFound() {
        Long id = 1L;
        when(productRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Not found"));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(id);
        });

        assertEquals("Not found", thrown.getMessage());
    }

    @Test
    void testUpdate() {
        when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto actual = productService.update(productDto);

        assertEquals(productDto, actual);
    }

    @Test
    void testCreate() {
        ProductCreatedDto actual = productService.create(productCreatedDto);

        assertEquals(productCreatedDto, actual);
    }


}
