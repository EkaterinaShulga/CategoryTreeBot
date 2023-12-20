package com.github.EkaterinaShulga.CategoryTreeBot.constants;

public enum BotButtonEnum {

    /**
     * contains with text
     * (for the buttons of menu and
     * text for answers/information for users )
     */
    HELLO_MESSAGE(" Hello! @"),
    ATTENTION_MESSAGE("Внимание! Информация введена не корректно"),
    INFORMATION_MESSAGE_FUNCTIONS_MENU(
            " Вам доступны следующие команды:\n" +
                    "\n" +
                    "/viewTree - отображает все имеющиеся категории и подкатегории; \n" +
                    "\n" +
                    "/addElement <название элемента>  - добавляет категорию, если ее нет;\n" +
                    "\n" +
                    "/addSubElement <название элемента><название подкатегории> \n" +
                    "добавляет подкатегорию в категорию(если она есть); \n" +
                    "\n" +
                    "/removeElement <название категории> - удаляет категорию(если она есть) и все ее подкатегории."

    ),
    INFORMATION_MESSAGE_CATEGORY_IN_DATABASE(" такая категория уже есть в базе"),
    INFORMATION_MESSAGE_DATABASE_IS_EMPTY(" информации нет"),
    INFORMATION_MESSAGE_CATEGORY_NOT_FOUND(" не нашел категорию:  "),
    INFORMATION_MESSAGE_CATEGORY_NOT_FOUND1(" не могу сохранить в базе подкатегорию "),
    INFORMATION_MESSAGE_CATEGORY_NOT_FOUND2(" потому что нет категории "),
    INFORMATION_MESSAGE_SUBCATEGORY_NOT_SAVE1(" у категории  "),
    INFORMATION_MESSAGE_SUBCATEGORY_NOT_SAVE2(" уже есть подкатегория  "),

    INFORMATION_MESSAGE_CATEGORY_SAVED(" добавил категорию - "),
    INFORMATION_MESSAGE_SUBCATEGORY_SAVED1(" добавил подкатегорию - "),
    INFORMATION_MESSAGE_SUBCATEGORY_SAVED2(" для категории  - "),
    INFORMATION_MESSAGE_CATEGORY_DELETE(" удалена категория и ее подкатегории  - "),

    BUTTON_START_MESSAGE(" выберите нужную команду "),
    BUTTON_COMMAND_VIEW_TREE("просмотр всех категорий "),
    BUTTON_COMMAND_ADD_CATEGORY(" добавить категорию "),
    BUTTON_COMMAND_ADD_SUBCATEGORY(" добавить подкатегорию "),
    BUTTON_COMMAND_REMOVE_CATEGORY(" удалить категорию "),
    BUTTON_COMMAND_HELP(" помощь "),
    BUTTON_VIEW_CATEGORY(" для просмотра всех категорий введите команду:\n" +
            "/viewTree"),
    BUTTON_EXAMPLE_FOR_NEW_CATEGORY(" чтобы добавить категорию\n" +
            "введите команду: \n" +
            "\n" +
            " /addElement верхняя одежда "),

    BUTTON_EXAMPLE_FOR_NEW_SUBCATEGORY(" чтобы добавить подкатегорию\n" +
            "введите команду:\n" +
            "\n" +
            " /addSubElement верхняя одежда-пальто "),

    BUTTON_EXAMPLE_FOR_REMOVE_CATEGORY(" чтобы удалить категорию\n" +
            "введите команду: \n" +
            "\n" +
            " /removeElement верхняя одежда "),
    BUTTON_EXAMPLE_HELP(" для ознакомления с функционалом меню \n" +
            "введите команду /help"),

    START("/start"),
    ADD_ELEMENT("/addElement"),
    ADD_SUB_ELEMENT("/addSubElement"),
    VIEW_TREE("/viewTree"),
    REMOVE_ELEMENT("/removeElement"),
    HELP("/help");


    private final String message;

    BotButtonEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
