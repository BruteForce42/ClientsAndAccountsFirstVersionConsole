package com.zatsepin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Основной класс приложения.
 *
 * @author Zatsepin Evgeny
 */
public class ConsoleApp {

    private static final String MAIN_MENU_ITEMS = "Главное меню:\n" +
            "1. Создать клиента.\n" +
            "2. Создать счёт для клиента.\n" +
            "3. Закрыть счёт клиента.\n" +
            "4. Перевести денежные средства.\n" +
            "5. Зачислить денежные средства.\n" +
            "Для выбора пункта меню введите номер пункта и нажмите Enter";

    public static void main(String[] args) {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        OperationHandler handler = new OperationHandler();

        while (true) {
            try {
                System.out.println(MAIN_MENU_ITEMS);

                switch (buffer.readLine()) {
                    case "1" -> handler.createClient(buffer);
                    case "2" -> handler.createAccountForClient(buffer);
                    case "3" -> handler.closeAccountForClient(buffer);
                    case "4" -> handler.transferFunds(buffer);
                    case "5" -> handler.addFunds(buffer);
                    default -> handler.defaultAction();
                }
            } catch (Exception e) {
                System.out.println("Вы ввели некорректное значение!\n" +
                        "Пожалуйста, попробуйте ещё раз.");
            }
        }
    }
}

