package com.urbandelivery.catalogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.urbandelivery.catalogo.dto.PromocionDTO;
import com.urbandelivery.catalogo.entity.Catalogo;
import com.urbandelivery.catalogo.repository.CatalogoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogoService {

    @Autowired
    private CatalogoRepository repository;

    @Autowired
    private WebClient webClient;

    public void guardarProducto(Catalogo producto) {
        repository.save(producto);
    }

    public Double obtenerPrecioFinal(Catalogo producto) {
        PromocionDTO promo = webClient.get()
                                .uri("/api/v0/promociones/{id}", producto.getPromocionId())
                                .retrieve()
                                .bodyToMono(PromocionDTO.class)
                                .block();

        if (promo != null && promo.getActiva()) {
            Double descuento = producto.getPrecioBase() * (promo.getPorcentajeDescuento() / 100);
            return producto.getPrecioBase() - descuento;
        }
        
        return producto.getPrecioBase();
    }

    public Catalogo obtenerPorId(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
}