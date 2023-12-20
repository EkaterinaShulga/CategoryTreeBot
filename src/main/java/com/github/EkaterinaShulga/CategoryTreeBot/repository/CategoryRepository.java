package com.github.EkaterinaShulga.CategoryTreeBot.repository;

import com.github.EkaterinaShulga.CategoryTreeBot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByTitle(String title);


}

