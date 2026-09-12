package com.tienda.repository;
 

import com.tienda.domain.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    //se crea una consulta derivada para recuperar las productos activas

    public List<Producto> findByActivoTrue();

    //consulta derivada que recupera la lista de productos de un ragn ode precios, ordenado por precio ascendentemente 
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioInf, double precioSup);

    //consulta JPQL que recupera la lista de productos de un ragn ode precios, ordenado por precio ascendentemente 
    @Query(value="SELECT p FROM Producto p Where p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(double precioInf, double precioSup);

    @Query(nativeQuery=true,
            value="SELECT * FROM producto p Where p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(double precioInf, double precioSup);

    //Practica #2 - consulta AMPLIADA (JPQL): recupera los productos de un rango de precios
    //usando la asociacion con Categoria, unicamente los activos y con existencias disponibles,
    //ordenados por la descripcion de la categoria y luego por el precio ascendentemente
    @Query(value="SELECT p FROM Producto p JOIN p.categoria c "
            + "WHERE p.precio BETWEEN :precioInf AND :precioSup "
            + "AND p.activo = true AND p.existencias > 0 "
            + "ORDER BY c.descripcion ASC, p.precio ASC")
    public List<Producto> consultaAmpliada(double precioInf, double precioSup);


}