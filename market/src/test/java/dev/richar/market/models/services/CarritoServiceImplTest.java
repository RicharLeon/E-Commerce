package dev.richar.market.models.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dev.richar.market.models.dao.CarritoDao;
import dev.richar.market.models.dao.CarritoItemsDao;
import dev.richar.market.models.dao.OurUsersDao;
import dev.richar.market.models.dto.CarritoItemsConsultaDTO;
import dev.richar.market.models.entity.Carrito;
import dev.richar.market.models.entity.OurUsers;
import dev.richar.market.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceImplTest {

    @Mock
    private CarritoDao carritoDao;

    @Mock
    private OurUsersDao ourUsersDao;

    @Mock
    private CarritoItemsDao carritoItemsService;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    private Carrito carrito;
    private OurUsers ourUsers;



    @Test
    void testFindAll() {
        when(carritoDao.findAll()).thenReturn(Collections.singletonList(carrito));

        List<Carrito> result = carritoService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carritoDao, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carritoDao.findByIdUsuario(1)).thenReturn(Optional.of(carrito));

        Carrito result = carritoService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getIdCarrito());
        verify(carritoDao, times(1)).findByIdUsuario(1);
    }

    @Test
    void testFindById_NotFound() {
        when(carritoDao.findByIdUsuario(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoService.findById(1);
        });

        String expectedMessage = MessageFormat.format("Carrito with id {0} not found", 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(carritoDao, times(1)).findByIdUsuario(1);
    }

    @Test
    void testFindByIdOptional() {
        when(carritoDao.findByIdUsuario(1)).thenReturn(Optional.of(carrito));

        Optional<Carrito> result = carritoService.findByIdOptional(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getIdCarrito());
        verify(carritoDao, times(1)).findByIdUsuario(1);
    }

    @Test
    void testSave() {
        when(carritoDao.save(carrito)).thenReturn(carrito);

        Carrito result = carritoService.save(carrito);

        assertNotNull(result);
        assertEquals(1, result.getIdCarrito());
        verify(carritoDao, times(1)).save(carrito);
    }

    @Test
    void testDelete() {
        when(carritoDao.findById(1)).thenReturn(Optional.of(carrito));
        doNothing().when(carritoDao).deleteById(1);

        carritoService.delete(1);

        verify(carritoDao, times(1)).findById(1);
        verify(carritoDao, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NotFound() {
        when(carritoDao.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoService.delete(1);
        });

        String expectedMessage = MessageFormat.format("Carrito with id {0} not found", 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(carritoDao, times(1)).findById(1);
        verify(carritoDao, never()).deleteById(1);
    }

    @Test
    void testUpdate() {
        Carrito updatedCarrito = new Carrito();
        updatedCarrito.setIdCarrito(1);
        updatedCarrito.setIdUsuario(2);

        when(carritoDao.findById(1)).thenReturn(Optional.of(carrito));
        when(carritoDao.save(carrito)).thenReturn(updatedCarrito);

        Carrito result = carritoService.update(updatedCarrito, 1);

        assertNotNull(result);
        assertEquals(2, result.getIdUsuario());
        verify(carritoDao, times(1)).findById(1);
        verify(carritoDao, times(1)).save(carrito);
    }

    @Test
    void testUpdate_NotFound() {
        Carrito updatedCarrito = new Carrito();
        updatedCarrito.setIdCarrito(1);
        updatedCarrito.setIdUsuario(2);

        when(carritoDao.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carritoService.update(updatedCarrito, 1);
        });

        String expectedMessage = MessageFormat.format("Carrito with id {0} not found", 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(carritoDao, times(1)).findById(1);
        verify(carritoDao, never()).save(any());
    }

}