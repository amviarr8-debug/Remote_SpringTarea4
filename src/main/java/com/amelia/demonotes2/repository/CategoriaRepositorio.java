package com.amelia.demonotes2.repository;

import com.amelia.demonotes2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;




    public interface CategoriaRepositorio extends JpaRepository<Category, Long> {
        // Aquí podremos añadir métodos de búsqueda personalizados si los necesitamos más adelante.
        // Por ejemplo: Categoria findByNombre(String nombre);
    }



