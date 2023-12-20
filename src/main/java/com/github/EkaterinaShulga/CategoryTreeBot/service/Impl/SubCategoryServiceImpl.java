package com.github.EkaterinaShulga.CategoryTreeBot.service.Impl;

import com.github.EkaterinaShulga.CategoryTreeBot.entity.Category;
import com.github.EkaterinaShulga.CategoryTreeBot.entity.SubCategory;
import com.github.EkaterinaShulga.CategoryTreeBot.repository.CategoryRepository;
import com.github.EkaterinaShulga.CategoryTreeBot.repository.SubCategoryRepository;
import com.github.EkaterinaShulga.CategoryTreeBot.service.SubCategoryService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.EkaterinaShulga.CategoryTreeBot.constants.BotButtonEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final TelegramBot telegramBot;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;


    /**
     * adds a SubCategory in the database
     * sends answer for user
     * (add subCategory/this subCategory is already in the database/
     * not found parent category)
     *
     * @param update - update from the bot
     */

    @Override
    public void addSubCategory(Update update) {
        log.info("addSubCategory - SubCategoryServiceImpl");
        long chatId = update.message().chat().id();
        String text = update.message().text();
        int index = text.indexOf("-");
        String categoryTitle = text.substring(15, index).trim();
        String title = text.substring(index + 1, text.length()).trim();
        List<String> list = new ArrayList<>();
        SubCategory subCategory = new SubCategory();
        Category category = categoryRepository.findByTitle(categoryTitle);
        if (category == null) {
            telegramBot.execute(new SendMessage(chatId,
                    INFORMATION_MESSAGE_CATEGORY_NOT_FOUND1.getMessage() + title +
                            INFORMATION_MESSAGE_CATEGORY_NOT_FOUND2.getMessage() + categoryTitle));
        }
        if (category != null) {
            List<SubCategory> subCategory1 = subCategoryRepository.findAllSubcategoriesByCategoryId(category.getId());
            for (SubCategory sub : subCategory1) {
                list.add(sub.getTitle());
            }
            if (!list.contains(title)) {
                subCategory.setTitle(title);
                subCategory.setCategory(category);
                subCategoryRepository.save(subCategory);
                telegramBot.execute(new SendMessage(chatId,
                        INFORMATION_MESSAGE_SUBCATEGORY_SAVED1.getMessage() + title +
                                INFORMATION_MESSAGE_SUBCATEGORY_SAVED2.getMessage() + categoryTitle));
            } else {
                telegramBot.execute(new SendMessage(chatId,
                        INFORMATION_MESSAGE_SUBCATEGORY_NOT_SAVE1.getMessage() + categoryTitle +
                                INFORMATION_MESSAGE_SUBCATEGORY_NOT_SAVE2.getMessage() + title));
            }
        }
    }

    /**
     * sends all information about categories and
     * subCategories from database
     * sends answer for user
     * (tree category/not found information)
     *
     * @param update - update from the bot
     */
    @Override
    public void allTree(Update update) {
        long chatId = update.message().chat().id();
        log.info("allTree - SubCategoryServiceImpl");
        List<Category> listCategory = categoryRepository.findAll();
        Map<String, List<SubCategory>> map = new HashMap<>();
        if (listCategory.isEmpty()) {
            telegramBot.execute(new SendMessage(chatId, INFORMATION_MESSAGE_DATABASE_IS_EMPTY.getMessage()));
        } else {
            for (Category c : listCategory) {
                List<SubCategory> subCat = subCategoryRepository.findAllSubcategoriesByCategoryId(c.getId());
                if (subCat == null) {
                    map.put(c.getTitle(), null);
                } else {
                    map.put(c.getTitle(), subCat);
                }
            }
            telegramBot.execute(new SendMessage(chatId, " " + printMap(map)));
        }
    }

    /**
     * for structured output of information
     * to the user
     *
     * @param map - with categories and subcategories
     * @return List
     */

    public List<String> printMap(Map<String, List<SubCategory>> map) {
        log.info("printMap - SubCategoryServiceImpl");
        List<String> list = new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(key + " = " + map.get(key) + "\n");
        }
        return list;

    }
}
