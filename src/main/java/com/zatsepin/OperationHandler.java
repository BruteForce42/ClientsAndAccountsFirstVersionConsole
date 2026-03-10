package com.zatsepin;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Обработчик операций по работе с клиентами и счетами.
 *
 * @author Zatsepin Evgeny
 */
public class OperationHandler {

    private static final String DOT = ". ";

    /**
     * Мапа для связи "ФИО клиента -> объект Client".
     */
    private final Map<String, Client> clientsMap = new HashMap<>();
    /**
     * Мапа для связи "номер счёта -> объект Account".
     */
    private final Map<String, Account> accountsMap = new HashMap<>();
    /**
     * Мапа для связи "ФИО клиента -> список открытых счетов клиента".
     */
    private final Map<String, List<Account>> clientOpenAccountsMap = new HashMap<>();

    /**
     * Создаёт клиента.
     *
     * @param buffer буфер для чтения с консоли
     * @throws IOException при ошибке чтения консольного ввода
     */
    public void createClient(final BufferedReader buffer) throws IOException {
        Client.Builder builder = new Client.Builder();

        System.out.println("Введите ФИО клиента и нажмите Enter");
        builder.name(buffer.readLine());

        System.out.println("Введите номер телефона клиента и нажмите Enter");
        builder.phoneNumber(buffer.readLine());

        System.out.println("Введите ИНН клиента и нажмите Enter");
        builder.inn(buffer.readLine());

        System.out.println("Введите адрес места жительства клиента и нажмите Enter");
        builder.address(buffer.readLine());

        Client client = builder.build();
        clientsMap.put(client.getName(), client);

        System.out.println("Клиент успешно создан!");
    }

    /**
     * Создаёт счёт для клиента и добавляет его в мапу со всеми счетами.
     * Если клиентов нет, то ничего не делает.
     *
     * @param buffer буфер для чтения с консоли
     * @throws IOException при ошибке чтения консольного ввода
     */
    public void createAccountForClient(final BufferedReader buffer) throws IOException {
        if (clientsMap.isEmpty()) {
            System.out.println("Клиентов не найдено!");
            return;
        }

        System.out.println("Список клиентов для создания счёта:");
        int itemCounter = 0;

        for (String clientName : clientsMap.keySet()) {
            System.out.println(++itemCounter + DOT + clientName);
        }
        System.out.println("Для выбора клиента введите его ФИО и нажмите Enter");

        Account.Builder builder = new Account.Builder();
        Client client = clientsMap.get(buffer.readLine());
        builder.clientId(client.getId());

        System.out.println("Введите номер счёта и нажмите Enter");
        builder.accountNumber(buffer.readLine());

        System.out.println("Введите БИК и нажмите Enter");
        builder.bic(buffer.readLine());

        System.out.println("Введите тип валюты, наименование из списка [Рубли, Доллары, Евро] и нажмите Enter");
        builder.currency(AccountCurrency.getCurrencyByDescription(buffer.readLine()));

        Account account = builder.build();
        accountsMap.put(account.getAccountNumber(), account);
        clientOpenAccountsMap.computeIfAbsent(client.getName(), k -> new ArrayList<>()).add(account);

        System.out.println("Счёт успешно создан!");
    }

    /**
     * Закрывает счёт для клиента.
     * Если клиентов с открытыми счетами нет, то ничего не делает.
     *
     * @param buffer буфер для чтения с консоли
     * @throws IOException при ошибке чтения консольного ввода
     */
    public void closeAccountForClient(final BufferedReader buffer) throws IOException {
        if (clientOpenAccountsMap.isEmpty()) {
            System.out.println("Клиентов с открытыми счетами не найдено!");
            return;
        }

        System.out.println("Список клиентов для закрытия счёта:");
        int itemCounter = 0;

        for (String clientName : clientOpenAccountsMap.keySet()) {
            System.out.println(++itemCounter + DOT + clientName);
        }
        System.out.println("Для выбора клиента введите его ФИО и нажмите Enter");

        String clientName = buffer.readLine();
        System.out.println("Список открытых счетов клиента " + clientName + ":");
        itemCounter = 0;

        for (Account account : clientOpenAccountsMap.get(clientName)) {
            System.out.println(++itemCounter + DOT + account.getAccountNumber());
        }
        System.out.println("Для выбора счёта введите номер счёта и нажмите Enter");

        Account closedAccount = accountsMap.get(buffer.readLine());
        closedAccount.setStatus(AccountStatus.CLOSE);
        clientOpenAccountsMap.get(clientName).remove(closedAccount);

        if (clientOpenAccountsMap.get(clientName).isEmpty()) {
            clientOpenAccountsMap.remove(clientName);
        }

        System.out.println("Счёт " + closedAccount.getAccountNumber() + " успешно закрыт!");
    }

    /**
     * Переводит денежные средства с одного счёта на другой.
     *
     * @param buffer буфер для чтения с консоли
     * @throws IOException при ошибке чтения консольного ввода
     */
    public void transferFunds(final BufferedReader buffer) throws IOException {
        if (accountsMap.isEmpty()) {
            System.out.println("Счета не найдены!");
            return;
        }

        System.out.println("Список счетов для выбора счёта отправителя:");
        int itemCounter = 0;

        for (String accountNumber : accountsMap.keySet()) {
            System.out.println(++itemCounter + DOT + accountNumber);
        }
        System.out.println("Для выбора счёта отправителя введите его номер и нажмите Enter");
        String senderAccountNumber = buffer.readLine();

        System.out.println("Введите сумму перевода и нажмите Enter");
        BigDecimal transferSum = new BigDecimal(buffer.readLine());

        System.out.println("Список счетов для выбора счёта получателя:");
        itemCounter = 0;

        for (String accountNumber : accountsMap.keySet()) {
            System.out.println(++itemCounter + DOT + accountNumber);
        }
        System.out.println("Для выбора счёта получателя введите его номер и нажмите Enter");
        String recipientAccountNumber = buffer.readLine();

        Account senderAccount = accountsMap.get(senderAccountNumber);
        senderAccount.setAmount(senderAccount.getAmount().subtract(transferSum));

        Account recipientAccount = accountsMap.get(recipientAccountNumber);
        recipientAccount.setAmount(recipientAccount.getAmount().add(transferSum));

        System.out.println("Денежные средства успешно переведены!");
    }

    /**
     * Зачисляет денежные средства на счёт.
     *
     * @param buffer буфер для чтения с консоли
     * @throws IOException при ошибке чтения консольного ввода
     */
    public void addFunds(final BufferedReader buffer) throws IOException {
        if (accountsMap.isEmpty()) {
            System.out.println("Счета не найдены!");
            return;
        }

        System.out.println("Список счетов для выбора счёта зачисления:");
        int itemCounter = 0;

        for (String accountNumber : accountsMap.keySet()) {
            System.out.println(++itemCounter + DOT + accountNumber);
        }
        System.out.println("Для выбора счёта зачисления введите его номер и нажмите Enter");
        String creditAccountNumber = buffer.readLine();

        System.out.println("Введите сумму зачисления и нажмите Enter");
        BigDecimal creditSum = new BigDecimal(buffer.readLine());

        Account creditAccount = accountsMap.get(creditAccountNumber);
        creditAccount.setAmount(creditAccount.getAmount().add(creditSum));

        System.out.println("Денежные средства успешно зачислены!");
    }

    /**
     * Вовращает ответ, если выбран неверный пункт главного меню.
     */
    public void defaultAction() {
        System.out.println("Вы ввели неверное значение номера пункта меню");
    }
}
