package com.tienda.service;

import com.tienda.domain.Categoria;
import com.tienda.repository.CategoriaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoriaService {

    // Se enlaza el repositorio de Categoría.
    private final CategoriaRepository categoriaRepository;
    private final FirebaseStorageService firebaseStorageService;

    public CategoriaService(CategoriaRepository categoriaRepository, FirebaseStorageService firebaseStorageService) {
        this.categoriaRepository = categoriaRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo) {
        if (activo) { // Si se quieren solo las categorías activas.
            return categoriaRepository.findByActivoTrue();
        }

        return categoriaRepository.findAll();
    }

    // READ
    @Transactional(readOnly = true)
    public Optional<Categoria> getCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    //Create / Update
    // Si el idCategoria dentro de categoria está vacio... se inserta.
    // Si el idCategoria dentro de categoria NO está vacío... se modifica.
    @Transactional
    public void save(Categoria categoria, MultipartFile imagen) {
        categoria = categoriaRepository.save(categoria);
        if (!imagen.isEmpty()) { // Nos pasaron una imagen...
            try {
                String ruta = firebaseStorageService
                        .uploadImage(imagen,
                                "categoria",
                                categoria.getIdCategoria());
            } catch (IOException e) {

            }
        }
    }

    // Delete
    @Transactional
    public void delete(Integer idCategoria) {
        // Primero se verifica que el registro exista...
        if (!categoriaRepository.existsById(idCategoria)) {
            // El registro no se puede eliminar... porque no existe...
            throw new IllegalArgumentException("No se puede eliminar el registro, no existe id " + idCategoria);
        }
        try {
            categoriaRepository.deleteById(idCategoria);
        } catch (DataIntegrityViolationException e) {
            // Se lanza una excepción porque la categoría tiene datos asociados.
            throw new IllegalStateException("No se elimina porque tiene datos asociados.", e);
        }
    }
}
