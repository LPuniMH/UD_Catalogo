package com.urbandelivery.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.urbandelivery.catalogo.entity.Catalogo;
import com.urbandelivery.catalogo.service.CatalogoService;

@RestController
@RequestMapping("/api/v0/catalogo")
@Tag(name = "Catálogo", description = "Gestión de platos y cálculo de precios con el microservicio de Promociones")
public class CatalogoController {

    @Autowired
    private CatalogoService service;

    @PostMapping
    @Operation(summary = "Crear plato", description = "Guarda un producto nuevo en el catálogo")
    public EntityModel<Catalogo> crearProducto(@RequestBody Catalogo producto) {
        Catalogo guardado = service.guardarProducto(producto);
        return addLinks(guardado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver detalle de plato", description = "Muestra la info base de un producto por ID")
    public EntityModel<Catalogo> obtenerProducto(@Parameter(description = "ID del producto") @PathVariable Long id) {
        Catalogo producto = service.obtenerPorId(id);
        return addLinks(producto);
    }

    @GetMapping("/{id}/precio-final")
    @Operation(summary = "Calcular Precio Final", description = "Se conecta al MS Promociones para calcular el precio final con descuento")
    public Double verPrecioFinal(@Parameter(description = "ID del producto") @PathVariable Long id) {
        Catalogo producto = service.obtenerPorId(id);
        return service.obtenerPrecioFinal(producto);
    }

    private EntityModel<Catalogo> addLinks(Catalogo producto) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CatalogoController.class)
                .obtenerProducto(producto.getId())).withSelfRel();
        return EntityModel.of(producto, selfLink);
    }
}