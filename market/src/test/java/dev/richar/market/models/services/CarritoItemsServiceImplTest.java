package dev.richar.market.models.services;

import dev.richar.market.models.dao.CarritoItemsDao;
import dev.richar.market.models.dto.ProductsConsultaDTO;
import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.CarritoItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarritoItemsServiceImplTest {


    @Mock
    private CarritoItemsDao carritoItemsDao;

    @Mock
    private ICarritoService carritoService;

    @Mock
    private IProductsService productsService;

    @InjectMocks
    private CarritoItemsServiceImpl carritoItemsService;

    private CarritoItems carritoItems;
    private Carrito carrito;

    @BeforeEach
    void setUp() {
        carritoItems = new CarritoItems();
        carritoItems.setIdCarritoItem(1);
        carritoItems.setCantidad(2);
        carritoItems.setIdProducto(101);
        carritoItems.setIdCarrito(1);

        carrito = new Carrito();
        carrito.setIdCarrito(1);
        carrito.setIdUsuario(1);
    }

    @Test
    void testFindAll() {
        when(carritoItemsDao.findAll()).thenReturn(Collections.singletonList(carritoItems));

        List<CarritoItems> result = carritoItemsService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carritoItemsDao, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carritoItemsDao.findById(1)).thenReturn(Optional.of(carritoItems));

        CarritoItems result = carritoItemsService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getIdCarritoItem());
        verify(carritoItemsDao, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(carritoItemsDao.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoItemsService.findById(1);
        });

        String expectedMessage = MessageFormat.format("CarritoItems with id {0} not found", 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(carritoItemsDao, times(1)).findById(1);
    }

    @Test
    void testSave() {
        when(carritoItemsDao.save(carritoItems)).thenReturn(carritoItems);

        CarritoItems result = carritoItemsService.save(carritoItems);

        assertNotNull(result);
        assertEquals(1, result.getIdCarritoItem());
        verify(carritoItemsDao, times(1)).save(carritoItems);
    }

    @Test
    void testDelete() {
        when(carritoItemsDao.findById(1)).thenReturn(Optional.of(carritoItems));
        doNothing().when(carritoItemsDao).deleteById(1);

        carritoItemsService.delete(1);

        verify(carritoItemsDao, times(1)).findById(1);
        verify(carritoItemsDao, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NotFound() {
        when(carritoItemsDao.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoItemsService.delete(1);
        });

        String expectedMessage = MessageFormat.format("CarritoItems with id {0} not found", 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(carritoItemsDao, times(1)).findById(1);
        verify(carritoItemsDao, never()).deleteById(1);
    }

    @Test
    void testUpdate() {
        CarritoItems updatedCarritoItems = new CarritoItems();
        updatedCarritoItems.setCantidad(5);

        when(carritoItemsDao.findById(1)).thenReturn(Optional.of(carritoItems));
        when(carritoItemsDao.save(carritoItems)).thenReturn(carritoItems);

        CarritoItems result = carritoItemsService.update(updatedCarritoItems, 1);

        assertNotNull(result);
        assertEquals(5, result.getCantidad());
        verify(carritoItemsDao, times(1)).findById(1);
        verify(carritoItemsDao, times(1)).save(carritoItems);
    }

    @Test
    void testUpdate_NotFound() {
        CarritoItems updatedCarritoItems = new CarritoItems();
        updatedCarritoItems.setCantidad(5);

        when(carritoItemsDao.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoItemsService.update(updatedCarritoItems, 1);
        });

        String expectedMessage = MessageFormat.format("CarritoItems with id {0} not found", 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(carritoItemsDao, times(1)).findById(1);
        verify(carritoItemsDao, never()).save(any());
    }

    @Test
    void testAddItemToUserCart() {
        when(carritoService.findByIdOptional(1)).thenReturn(Optional.of(carrito));
        when(carritoItemsDao.save(any(CarritoItems.class))).thenReturn(carritoItems);

        CarritoItems result = carritoItemsService.addItemToUserCart(1, carritoItems);

        assertNotNull(result);
        assertEquals(1, result.getIdCarritoItem());
        verify(carritoService, times(1)).findByIdOptional(1);
        verify(carritoItemsDao, times(1)).save(any(CarritoItems.class));
    }

}