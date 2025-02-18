package dev.richar.market.models.services;

import dev.richar.market.models.dao.CategoryDao;
import dev.richar.market.models.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setDescription("Electronic items");
        category.setStatus(true);
    }

    @Test
    public void testFindAll() {
        when(categoryDao.findAll()).thenReturn(Arrays.asList(category));
        List<Category> categories = categoryService.findAll();
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Electronics", categories.get(0).getName());


        verify(categoryDao, times(1)).findAll();
    }

    @Test
    public void testFindById_ExistingId() {

        when(categoryDao.findById(1)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.findById(1);

        assertNotNull(foundCategory);
        assertEquals("Electronics", foundCategory.getName());

        verify(categoryDao, times(1)).findById(1);
    }

    @Test
    public void testFindById_NonExistingId() {
        // Configuración del mock
        when(categoryDao.findById(2)).thenReturn(Optional.empty());
        // Ejecución y verificación
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.findById(2);
        });
        assertEquals("Category with id 2 not found", exception.getMessage());

        // Verificar que el método findById del DAO fue llamado una vez con el argumento 2
        verify(categoryDao, times(1)).findById(2);
    }

    @Test
    public void testSave() {
        when(categoryDao.save(category)).thenReturn(category);

        Category savedCategory = categoryService.save(category);

        assertNotNull(savedCategory);
        assertEquals("Electronics", savedCategory.getName());

        // Verificar que el método save del DAO fue llamado una vez con la categoría
        verify(categoryDao, times(1)).save(category);
    }

    @Test
    public void testDelete_ExistingId() {
        when(categoryDao.findById(1)).thenReturn(Optional.of(category));
        doNothing().when(categoryDao).deleteById(1);

        categoryService.delete(1);

        verify(categoryDao, times(1)).deleteById(1);
    }

    @Test
    public void testDelete_NonExistingId() {

        when(categoryDao.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.delete(2);
        });

        assertEquals("Category with id 2 not found", exception.getMessage());

        verify(categoryDao, times(1)).findById(2);
    }

    @Test
    public void testUpdate_ExistingId() {

        when(categoryDao.findById(1)).thenReturn(Optional.of(category));
        when(categoryDao.save(category)).thenReturn(category);

        Category updatedCategory = new Category();
        updatedCategory.setName("Home Appliances");
        updatedCategory.setDescription("Appliances for home use");
        updatedCategory.setStatus(false);


        Category result = categoryService.update(updatedCategory, 1);

        assertNotNull(result);
        assertEquals("Home Appliances", result.getName());
        assertEquals("Appliances for home use", result.getDescription());
        assertFalse(result.getStatus());

        verify(categoryDao, times(1)).findById(1);
        verify(categoryDao, times(1)).save(category);
    }

    @Test
    public void testUpdate_NonExistingId() {

        when(categoryDao.findById(2)).thenReturn(Optional.empty());

        Category updatedCategory = new Category();
        updatedCategory.setName("Home Appliances");
        updatedCategory.setDescription("Appliances for home use");
        updatedCategory.setStatus(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.update(updatedCategory, 2);
        });

        assertEquals("Category with id 2 not found", exception.getMessage());

        verify(categoryDao, times(1)).findById(2);
    }
}
