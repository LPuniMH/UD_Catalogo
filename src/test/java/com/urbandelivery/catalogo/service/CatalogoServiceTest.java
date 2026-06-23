package com.urbandelivery.catalogo.service;

import com.urbandelivery.catalogo.dto.PromocionDTO;
import com.urbandelivery.catalogo.entity.Catalogo;
import com.urbandelivery.catalogo.repository.CatalogoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogoServiceTest {

    @Mock
    private CatalogoRepository repository;

    @Mock
    private WebClient webClient;
    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec uriSpec;
    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec headersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CatalogoService service;

    private Catalogo producto;

    @BeforeEach
    void setUp() {
        producto = new Catalogo();
        producto.setId(1L);
        producto.setPrecioBase(10000.0);
        producto.setPromocionId(1L);
    }

    @Test
    void testGuardarProducto() {
        when(repository.save(any(Catalogo.class))).thenReturn(producto);
        
        Catalogo resultado = service.guardarProducto(producto);
        
        assertNotNull(resultado);
        assertEquals(10000.0, resultado.getPrecioBase());
        verify(repository, times(1)).save(producto);
    }

    @Test
    void testObtenerPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(producto));
        
        Catalogo resultado = service.obtenerPorId(1L);
        
        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testObtenerPrecioFinal_ConDescuentoActivo() {
        PromocionDTO promoFalsa = new PromocionDTO();
        promoFalsa.setActiva(true);
        promoFalsa.setPorcentajeDescuento(20.0);

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString(), anyLong())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PromocionDTO.class)).thenReturn(Mono.just(promoFalsa));

        Double precioFinal = service.obtenerPrecioFinal(producto);

        assertEquals(8000.0, precioFinal);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testObtenerPrecioFinal_SinDescuento() {
        PromocionDTO promoInactiva = new PromocionDTO();
        promoInactiva.setActiva(false);

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString(), anyLong())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PromocionDTO.class)).thenReturn(Mono.just(promoInactiva));

        Double precioFinal = service.obtenerPrecioFinal(producto);

        assertEquals(10000.0, precioFinal);
    }
}