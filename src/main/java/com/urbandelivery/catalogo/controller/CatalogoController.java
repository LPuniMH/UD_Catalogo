package com.urbandelivery.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.urbandelivery.catalogo.entity.Catalogo;
import com.urbandelivery.catalogo.service.CatalogoService;

@RestController
@RequestMapping("/api/v0/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService service;

    @PostMapping
    public void crearProducto(@RequestBody Catalogo producto) {
        service.guardarProducto(producto);
    }

    @GetMapping("/{id}/precio-final")
    public Double verPrecioFinal(@PathVariable Long id) {
        Catalogo producto = service.obtenerPorId(id);
        return service.obtenerPrecioFinal(producto);
    }
}