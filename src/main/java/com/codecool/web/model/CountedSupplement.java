package com.codecool.web.model;

public final class CountedSupplement extends Supplement {

    public int counter;

    public CountedSupplement(int id, String name, String supplement_type, String img, int price, boolean premium, int counter) {
        super(id, name, supplement_type, img, price, premium);
        this.counter = counter;
    }
}
