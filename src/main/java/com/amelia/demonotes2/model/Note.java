package com.amelia.demonotes2.model;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String title;
    @NotBlank(message = "El contenido no puede estar vacío")
    @Size(min = 10, max = 500)
    private String content;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.EAGER) // que las cargue cuando sea necesario
    @JoinColumn(name = "category_id")
    private Category category;


    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now(); // se asigna automáticamente a la fecha actual.
    }
    public void setDate(LocalDate date) {
        this.createdAt = date;
    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDate.now();
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {

        this.content = content;
        this.updatedAt = LocalDate.now();
    }

    public LocalDate getDate() {
        return createdAt  == null ? LocalDate.now() : createdAt;
    }
    public LocalDate getUpdatedAt() {
        return updatedAt;}
}
