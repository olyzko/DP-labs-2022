package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Phone implements Serializable, Comparable<Phone> {

    private String id;
    private String name;
    private String surname;
    private String address;
    private int cardNumber;
    private int credit;
    private int debet;
    private int cityMinutes;
    private int interMinutes;

    public Phone(String id, String name, String surname, String address, int cardNumber, int credit, int debet, int cityMinutes, int interMinutes) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cardNumber = cardNumber;
        this.credit = credit;
        this.debet = debet;
        this.cityMinutes = cityMinutes;
        this.interMinutes = interMinutes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDebet() { return debet; }

    public void setDebet(int debet) { this.debet = debet; }

    public int getCityMinutes() { return cityMinutes; }

    public void setCityMinutes(int cityMinutes) {
        this.cityMinutes = cityMinutes;
    }

    public int getInterMinutes() {
        return interMinutes;
    }

    public void setInterMinutes(int interMinutes) {
        this.interMinutes = interMinutes;
    }



    @Override
    public int compareTo(Phone other) {
        return this.surname.compareTo(other.surname);
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\nsurname: " + surname +
                "\naddress: " + address +
                "\ncardNumber: " + cardNumber +
                "\ncredit: " + credit +
                "\ndebet: " + debet +
                "\ncityMinutes: " + cityMinutes +
                "\ninterMinutes: " + interMinutes;
    }

    public static List<Phone> getList() {
        return new ArrayList<>() {
            {
                add(new Phone("01", "Max", "Veres",  "Kyiv",           12549632, 50000, 25500, 100, 105));
                add(new Phone("02", "Mykhailo", "Olyzko",      "Kyiv",           59611422, 15000, 14000, 25, 0));
                add(new Phone("03", "Danylo",  "Andreev", "Kharkiv",           84255001, 8000, 3400, 18, 15));
                add(new Phone("04", "Nazar",   "Hrynko",        "Mariupol",      63632526, 8500, 2500, 35, 60));
                add(new Phone("05", "Vlad",  "Shupliakov",    "Mykolaiv", 44115296, 25000, 35000, 85, 32));
            }
        };
    }
}