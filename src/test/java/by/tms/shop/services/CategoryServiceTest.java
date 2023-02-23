package by.tms.shop.services;

import by.tms.shop.dto.CategoryDto;
import by.tms.shop.entities.Category;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.CategoryMapper;
import by.tms.shop.repositories.CategoryRepository;
import by.tms.shop.services.impl.CategoryService;
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
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryService categoryService;
    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    public void init() {
        categoryDto = CategoryDto.builder()
                .id(1L)
                .title("test")
                .build();

        category = Category.builder()
                .id(1L)
                .title("test")
                .productList(List.of())
                .build();
    }

    @Test
    void testCreate() {
        CategoryDto actual = categoryService.create(categoryDto);

        assertEquals(categoryDto, actual);
    }

    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryDto> actual = categoryService.findAll();

        assertEquals(List.of(), actual);
    }

    @Test
    void testFindByIdThrowsNotFound() {
        Long id = 1L;
        when(categoryRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Not found"));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.findById(id);
        });

        assertEquals("Not found", thrown.getMessage());
    }

    @Test
    void testUpdate() {
        when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto actual = categoryService.update(categoryDto);

        assertEquals(categoryDto, actual);
    }
}
