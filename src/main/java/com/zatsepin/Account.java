package com.zatsepin;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Аналитический счёт.
 *
 * @author Zatsepin Evgeny
 */
public class Account {

    /**
     * Идентификатор счёта.
     */
    private final UUID id;
    /**
     * Идентификатор клиента для которого открыт счёт.
     */
    private final UUID clientId;
    /**
     * Номер счёта.
     */
    private String accountNumber;
    /**
     * Сумма средств на счёте.
     */
    private BigDecimal amount;
    /**
     * Статус счёта.
     */
    private AccountStatus status;
    /**
     * БИК.
     */
    private String bic;
    /**
     * Тип валюты счёта.
     */
    private AccountCurrency currency;

    private Account(Builder builder) {
        this.id = UUID.randomUUID();
        this.clientId = builder.clientId;
        this.accountNumber = builder.accountNumber;
        this.amount = BigDecimal.ZERO;
        this.status = AccountStatus.OPEN;
        this.bic = builder.bic;
        this.currency = builder.currency;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public AccountCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(AccountCurrency currency) {
        this.currency = currency;
    }

    public static class Builder {
        private UUID clientId;
        private String accountNumber;
        private String bic;
        private AccountCurrency currency;

        public void clientId(UUID clientId) {
            this.clientId = clientId;
        }

        public void accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public void bic(String bic) {
            this.bic = bic;
        }

        public void currency(AccountCurrency currency) {
            this.currency = currency;
        }

        public Account build() {
            return new Account(this);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", bic='" + bic + '\'' +
                ", currency=" + currency +
                '}';
    }
}
