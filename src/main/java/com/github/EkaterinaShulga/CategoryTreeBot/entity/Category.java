package com.github.EkaterinaShulga.CategoryTreeBot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 *  Category -  the parent category entered into the database
 */

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<SubCategory> subCategories;


    @Override
    public String toString() {
        return
                "" + title + ":" + '\n' +
                        "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(title, category.title) && Objects.equals(subCategories, category.subCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subCategories);
    }
}
