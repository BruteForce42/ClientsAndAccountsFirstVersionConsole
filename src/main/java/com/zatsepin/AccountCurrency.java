package com.zatsepin;

/**
 * Перечисление допустимых типов валюты.
 *
 * @author Zatsepin Evgeny
 */
public enum AccountCurrency {
    RUR(810, "Рубли"),
    USD(840, "Доллары"),
    EUR(978, "Евро");

    private final int code;
    private final String description;

    AccountCurrency(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Возвращает валюту по её описанию.
     *
     * @param description описание
     * @return статус
     */
    public static AccountCurrency getCurrencyByDescription(String description) {
        for (AccountCurrency currency : values()) {
            if (currency.getDescription().equals(description)) {
                return currency;
            }
        }

        throw new IllegalArgumentException("Неверное значение валюты счёта " + description);
    }
}
