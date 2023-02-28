package by.tms.shop.services.impl;

import by.tms.shop.dto.CategoryDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.exceptions.NotFoundException;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.CategoryMapper;
import by.tms.shop.repositories.CategoryRepository;
import by.tms.shop.services.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService implements CrudService<CategoryDto> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        if(categoryDto != null) {
            categoryRepository.save(categoryMapper.toEntity(categoryDto));
        } else {
            throw new NotFoundException("Категория не найдена.");
        }

        return categoryDto;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена."));
    }

    @Override
    public CategoryDto deleteById(Long id) {
        CategoryDto categoryDto = findById(id);
        categoryRepository.deleteById(id);

        return categoryDto;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.toEntity(categoryDto));

        return categoryDto;
    }
}
