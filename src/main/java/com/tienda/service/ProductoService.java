package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService {

    //Se enlaza el repositorio de Producto
    private final ProductoRepository productoRepository;
    private final FirebaseStorageService firebaseStorageService;

    public ProductoService(ProductoRepository productoRepository, FirebaseStorageService firebaseStorageService) {
        this.productoRepository = productoRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            return productoRepository.findByActivoTrue();
        }

        return productoRepository.findAll();
    }
    // CRUD: Create... Read... Update... Delete...

    //READ
    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);

    }

    //Create / Update
    //si el idProducto dentro de producto esta vacio... se inserta 
    //si el idProducto dentro de producto no esta vacio... se modifica 
    @Transactional
    public void save(Producto producto, MultipartFile imagen) {
        producto = productoRepository.save(producto);
        if (!imagen.isEmpty()) {
            try {
                String ruta = firebaseStorageService.uploadImage(imagen, "producto", producto.getIdProducto());
                producto.setRutaImagen(ruta);
                productoRepository.save(producto);
            } catch (IOException e) {

            }
        }
    }

    //Delete
    @Transactional
    public void delete(Integer idProducto) {
        //Primero se verifica que el registro exista...
        if (!productoRepository.existsById(idProducto)) {
            //el registro no se puede eliminar, pq no existe
            throw new IllegalArgumentException("No se puede eliminar el registro, no existe id " + idProducto);
        }
        try {
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) {
            //se lanza una excepcion pq tiene datos asociados
            throw new IllegalStateException("No se elimina porque tiene datos asociados", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Producto> consultaDerivada(double precioInf, double precioSup) {
        return productoRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);

    }

    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(double precioInf, double precioSup) {
        return productoRepository.consultaJPQL(precioInf, precioSup);

    }

    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(double precioInf, double precioSup) {
        return productoRepository.consultaSQL(precioInf, precioSup);

    }

    //Practica #2 - consulta ampliada
    @Transactional(readOnly = true)
    public List<Producto> consultaAmpliada(double precioInf, double precioSup) {
        return productoRepository.consultaAmpliada(precioInf, precioSup);

    }
}
