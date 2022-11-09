package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }

    @Override
    public List<CategoryDto> list() {
        List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());

        return CategoryDto.of(categories);

//        if (CollectionUtils.isEmpty(categories)) {
//            categories.forEach(e ->  {
//                CategoryDto category = new CategoryDto();
//                category.setId(e.getId());
//                category.setCategoryName(e.getCategoryName());
//
//                categoryDtoList.add(category);
//            });
//            // of 로 가능
//        }
    }

    @Override
    public boolean add(String categoryName) {
        // 추가하면 됨
        // 동일한 카테고리 중복 체크

        Category category = Category.builder()
                .categoryName(categoryName)
                .usingYn(true)
                // 정책 - 정렬을 할때 큰값.작은값 정해야함 -> 0으로 추가 + 값 많음
                .sortValue(0)
                .build();
        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean update(CategoryInput parameter) {

        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());
            categoryRepository.save(category);

        }
        return true;
    }

    @Override
    public boolean del(long id) {
        // JPA 문법으로 간단히 나타내기
        categoryRepository.deleteById(id);
        return true;
    }
}
