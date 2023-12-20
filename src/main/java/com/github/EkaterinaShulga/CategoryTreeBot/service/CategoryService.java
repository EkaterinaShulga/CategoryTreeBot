package com.github.EkaterinaShulga.CategoryTreeBot.service;

import com.pengrad.telegrambot.model.Update;

public interface CategoryService {

    void addCategory(Update update);

    void removeCategoryByTitle(Update update);

}
