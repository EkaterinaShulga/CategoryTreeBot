package com.github.EkaterinaShulga.CategoryTreeBot.service.Impl;

import com.github.EkaterinaShulga.CategoryTreeBot.service.CategoryService;
import com.github.EkaterinaShulga.CategoryTreeBot.service.SubCategoryService;
import com.github.EkaterinaShulga.CategoryTreeBot.service.TableService;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.github.EkaterinaShulga.CategoryTreeBot.constants.BotButtonEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final TableService tableService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @PostConstruct
    public void init() throws IOException {
        String json = Files.readString(Paths.get("update.json"));
        Update update = BotUtils.parseUpdate(json);
        telegramBot.setUpdatesListener(this);
    }

    /**
     * the method processes all incoming messages from the bot,<br>
     * if the message is entered correctly, it passes <br>
     * it to the {@code messageHandler(update)} method, if not, <br>
     * the bot returns a message that the information was entered incorrectly
     *
     * @param updates - update from the bot
     * @return - int
     */

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            try {
                messageHandler(update);
            } catch (Exception e) {
                log.info(ATTENTION_MESSAGE.getMessage() + e);
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        ATTENTION_MESSAGE.getMessage()));
            }
        });
        log.info("returned an answer" + UpdatesListener.CONFIRMED_UPDATES_ALL);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * accepts and processes response from
     * buttons of menu and send answer to
     * the user (what command he needs to
     * send)
     *
     * @param update - update from the bot
     */

    public void messageHandler(Update update) throws IOException {
        log.info("messageHandler - BotUpdatesListener");
        if (update.callbackQuery() != null) {
            Long chatId = update.callbackQuery().message().chat().id();
            if (update.callbackQuery().data().equals(BUTTON_COMMAND_VIEW_TREE.getMessage())) {
                try {
                    telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                            BUTTON_VIEW_CATEGORY.getMessage()));
                } catch (NullPointerException e) {
                    log.info(ATTENTION_MESSAGE.getMessage() + e);
                }
            }
            if (update.callbackQuery().data().equals(BUTTON_COMMAND_ADD_CATEGORY.getMessage())) {
                try {
                    telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                            BUTTON_EXAMPLE_FOR_NEW_CATEGORY.getMessage()));
                } catch (NullPointerException e) {
                    log.info(ATTENTION_MESSAGE.getMessage() + e);
                }
            }
            if (update.callbackQuery().data().equals(BUTTON_COMMAND_ADD_SUBCATEGORY.getMessage())) {
                try {
                    telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                            BUTTON_EXAMPLE_FOR_NEW_SUBCATEGORY.getMessage()));
                } catch (NullPointerException e) {
                    log.info(ATTENTION_MESSAGE.getMessage() + e);
                }
            }
            if (update.callbackQuery().data().equals(BUTTON_COMMAND_REMOVE_CATEGORY.getMessage())) {
                try {
                    telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                            BUTTON_EXAMPLE_FOR_REMOVE_CATEGORY.getMessage()));
                } catch (NullPointerException e) {
                    log.info(ATTENTION_MESSAGE.getMessage() + e);
                }
            }
            if (update.callbackQuery().data().equals(BUTTON_COMMAND_HELP.getMessage())) {
                try {
                    telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                            BUTTON_EXAMPLE_HELP.getMessage()));
                } catch (NullPointerException e) {
                    log.info(ATTENTION_MESSAGE.getMessage() + e);
                }
            }
        } else {
            checkAnswer(update);
        }
    }

    /**
     * accepts and processes commands from
     * user and calls the disired method
     *
     * @param update - update from the bot
     */
    public void checkAnswer(Update update) throws IOException {
        log.info("checkAnswer - BotUpdatesListener");
        Long chatId = update.message().chat().id();
        String inputText = update.message().text();
        if (inputText != null) {
            if (inputText.equals(START.getMessage())) {
                sendStartMenu(update);
            }
            if (inputText.contains(ADD_ELEMENT.getMessage())) {
                categoryService.addCategory(update);
            }
            if (inputText.contains(ADD_SUB_ELEMENT.getMessage())) {
                subCategoryService.addSubCategory(update);
            }
            if (inputText.contains(VIEW_TREE.getMessage())) {
                subCategoryService.allTree(update);

            }
            if (inputText.contains(REMOVE_ELEMENT.getMessage())) {
                categoryService.removeCategoryByTitle(update);
            }
            if (inputText.contains(HELP.getMessage())) {
                informationAboutAllFunctions(update);
            }
        }
    }

    /**
     * method returns a welcome message and
     * menu to the user
     *
     * @param update - update from the bot
     */
    public void sendStartMenu(Update update) {
        log.info("sendStartMenu - BotUpdatesListener");
        try {
            String greetingMessage = HELLO_MESSAGE.getMessage() + update.message().chat().username()
                    + BUTTON_START_MESSAGE.getMessage();
            telegramBot.execute(new SendMessage(update.message().chat().id(), greetingMessage)
                    .replyMarkup(tableService.startMenuButtons()));
        } catch (NullPointerException e) {
            log.info(ATTENTION_MESSAGE.getMessage() + e);
        }
    }

    /**
     * method returns a list of commands and
     * information about all functions of bot
     *
     * @param update - update from the bot
     */

    public void informationAboutAllFunctions(Update update) {
        log.info("informationAboutAllFunctions - BotUpdatesListener");
        try {
            String greetingMessage = INFORMATION_MESSAGE_FUNCTIONS_MENU.getMessage();
            telegramBot.execute(new SendMessage(update.message().chat().id(), greetingMessage));
        } catch (NullPointerException e) {
            log.info(ATTENTION_MESSAGE.getMessage() + e);
        }

    }


}
