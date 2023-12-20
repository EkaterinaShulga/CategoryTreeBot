package com.github.EkaterinaShulga.CategoryTreeBot.service;

import com.pengrad.telegrambot.model.Update;

public interface SubCategoryService {

    void addSubCategory(Update update);

    void allTree(Update update);
}
