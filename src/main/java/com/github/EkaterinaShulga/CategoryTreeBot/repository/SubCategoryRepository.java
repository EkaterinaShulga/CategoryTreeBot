package com.github.EkaterinaShulga.CategoryTreeBot.repository;

import com.github.EkaterinaShulga.CategoryTreeBot.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    List<SubCategory> findAllSubcategoriesByCategoryId(int id);

}
