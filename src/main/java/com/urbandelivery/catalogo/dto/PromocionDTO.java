package com.urbandelivery.catalogo.dto;

import lombok.Data;

@Data
public class PromocionDTO {
    private Long id;
    private Double porcentajeDescuento;
    private Boolean activa;
}