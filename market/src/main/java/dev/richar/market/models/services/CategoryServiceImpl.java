package dev.richar.market.models.services;

import dev.richar.market.models.dao.CategoryDao;
import dev.richar.market.models.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private final CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryDao.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Category with id {0} not found", id)));
    }

    @Override
    public Category save(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public void delete(Integer id) {
        categoryDao.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Category with id {0} not found", id)));
        categoryDao.deleteById(id);
    }

    @Override
    public Category update(Category category, Integer id) {
        Category category1 = categoryDao.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat
                        .format("Category with id {0} not found", id)));

        category1.setName(category.getName());
        category1.setDescription(category.getDescription());
        category1.setStatus(category.getStatus());
        return categoryDao.save(category1);
    }



}
