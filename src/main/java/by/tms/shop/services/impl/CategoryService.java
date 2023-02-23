package by.tms.shop.services.impl;

import by.tms.shop.dto.CategoryDto;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.CategoryMapper;
import by.tms.shop.repositories.CategoryRepository;
import by.tms.shop.services.CrudService;
import lombok.AllArgsConstructor;
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
        categoryRepository.save(categoryMapper.toEntity(categoryDto));

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
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.toEntity(categoryDto));

        return categoryDto;
    }
}
