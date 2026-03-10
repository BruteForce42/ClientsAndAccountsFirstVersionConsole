package com.zatsepin;

import java.util.UUID;

/**
 * Клиент банка.
 *
 * @author Zatsepin Evgeny
 */
public class Client {

    /**
     * Идентификатор клиента.
     */
    private final UUID id;
    /**
     * ФИО.
     */
    private String name;
    /**
     * Номер телефона.
     */
    private String phoneNumber;
    /**
     * ИНН.
     */
    private String inn;
    /**
     * Адрес места жительства.
     */
    private String address;

    private Client(Builder builder) {
        this.id = UUID.randomUUID();
        this.name = builder.name;
        this.phoneNumber = builder.phoneNumber;
        this.inn = builder.inn;
        this.address = builder.address;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Построитель для объекта Client.
     */
    public static class Builder {
        private String name;
        private String phoneNumber;
        private String inn;
        private String address;

        public void name(String name) {
            this.name = name;
        }

        public void phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void inn(String inn) {
            this.inn = inn;
        }

        public void address(String address) {
            this.address = address;
        }

        public Client build() {
            return new Client(this);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", inn='" + inn + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
