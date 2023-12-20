package com.github.EkaterinaShulga.CategoryTreeBot.service.Impl;

import com.github.EkaterinaShulga.CategoryTreeBot.entity.Category;
import com.github.EkaterinaShulga.CategoryTreeBot.repository.CategoryRepository;
import com.github.EkaterinaShulga.CategoryTreeBot.service.CategoryService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.github.EkaterinaShulga.CategoryTreeBot.constants.BotButtonEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final TelegramBot telegramBot;


    /**
     * adds Category in the database
     * sends answer for user
     * (add category/this category is already in the database)
     *
     * @param update - update from the bot
     */
    @Override
    public void addCategory(Update update) {
        log.info("addCategory - CategoryServiceImpl");
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String title = text.substring(12, text.length()).trim();
        Category category1 = categoryRepository.findByTitle(title);
        Category category = new Category();
        category.setTitle(title);
        if (category1 == null) {
            categoryRepository.save(category);
            telegramBot.execute(new SendMessage(chatId, INFORMATION_MESSAGE_CATEGORY_SAVED.getMessage() + title));
        } else {
            telegramBot.execute(new SendMessage(chatId, INFORMATION_MESSAGE_CATEGORY_IN_DATABASE.getMessage()));
        }
    }

    /**
     * deletes Category from the database
     * sends answer for user
     * (delete Category/not found this category)
     *
     * @param update - update from the bot
     */
    @Override
    public void removeCategoryByTitle(Update update) {
        log.info("removeCategoryByTitle - CategoryServiceImpl");
        long chatId = update.message().chat().id();
        String text = update.message().text();
        String title = text.substring(14, text.length()).trim();
        Category category = categoryRepository.findByTitle(title);
        if (category == null) {
            telegramBot.execute(new SendMessage(chatId, INFORMATION_MESSAGE_CATEGORY_NOT_FOUND.getMessage() + title));
        } else {
            categoryRepository.delete(category);
            telegramBot.execute(new SendMessage(chatId, INFORMATION_MESSAGE_CATEGORY_DELETE.getMessage() + title));
        }


    }


}
