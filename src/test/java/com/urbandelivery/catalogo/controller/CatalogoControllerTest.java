package com.urbandelivery.catalogo.controller;

import com.urbandelivery.catalogo.entity.Catalogo;
import com.urbandelivery.catalogo.service.CatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogoControllerTest {

    @Mock
    private CatalogoService catalogoService;

    @InjectMocks
    private CatalogoController catalogoController;

    private Catalogo producto;

    @BeforeEach
    void setUp() {
        producto = new Catalogo();
        producto.setId(1L);
        producto.setNombre("Hamburguesa Doble");
        producto.setPrecioBase(10000.0);
        producto.setPromocionId(1L);
        producto.setEmpresaId(1L);
    }

    @Test
    void testCrearProducto() {
        when(catalogoService.guardarProducto(any(Catalogo.class))).thenReturn(producto);

        EntityModel<Catalogo> result = catalogoController.crearProducto(producto);

        assertNotNull(result);
        assertEquals("Hamburguesa Doble", result.getContent().getNombre());
        verify(catalogoService, times(1)).guardarProducto(producto);
    }

    @Test
    void testObtenerProducto() {
        when(catalogoService.obtenerPorId(1L)).thenReturn(producto);

        EntityModel<Catalogo> result = catalogoController.obtenerProducto(1L);

        assertNotNull(result);
        assertEquals(10000.0, result.getContent().getPrecioBase());
        verify(catalogoService, times(1)).obtenerPorId(1L);
    }

    @Test
    void testVerPrecioFinal() {
        when(catalogoService.obtenerPorId(1L)).thenReturn(producto);
        when(catalogoService.obtenerPrecioFinal(producto)).thenReturn(8000.0);

        Double precioFinal = catalogoController.verPrecioFinal(1L);

        assertEquals(8000.0, precioFinal);
        verify(catalogoService, times(1)).obtenerPorId(1L);
        verify(catalogoService, times(1)).obtenerPrecioFinal(producto);
    }
}