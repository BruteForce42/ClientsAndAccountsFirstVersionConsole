package com.zatsepin;

/**
 * Перечисление возможных статусов аналитического счёта.
 *
 * @author Zatsepin Evgeny
 */
public enum AccountStatus {
    OPEN(1, "Открыт"),
    CLOSE(0, "Закрыт");

    private final int code;
    private final String description;

    AccountStatus(int code, String description) {
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
     * Возвращает статус по его описанию.
     *
     * @param description описание
     * @return статус
     */
    public static AccountStatus getStatusByDescription(String description) {
        for (AccountStatus status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Неверное значение статуса счёта " + description);
    }
}
