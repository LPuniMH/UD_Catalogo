UrbanDelivery - Microservicio de Catálogo

Estudiantes: Luis Ahumada, Maximiliano Valenzuela, Litzy Zuñiga

Descripción del proyecto:
Este es el catálogo de productos de UrbanDelivery. La gracia de este microservicio es que está conectado con el de promociones, así que cuando pides el precio final de un plato, va y le pregunta al otro servicio si hay algún descuento activo para aplicarlo.

Funcionalidades implementadas:
- guardar productos nuevos en el catálogo con su precio base
- conectarse mediante webclient al microservicio de promociones
- calcular y devolver el precio final de un producto aplicando el descuento correspondiente

Pasos para ejecutar:
1. Primero tienes que tener corriendo el microservicio de Promociones en el puerto 8084, si no, este va a tirar error al querer conectarse.
2. tener mysql corriendo.
3. correr la aplicación. acá se usa jpa update así que la bd catalogo_db se crea automáticamente.
4. el servicio corre en el puerto 8083. 
5. probar creando un producto y luego usando el endpoint de "/{id}/precio-final" para ver como consume al otro servicio.
