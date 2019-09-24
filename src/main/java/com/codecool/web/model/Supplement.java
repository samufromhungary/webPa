package com.codecool.web.model;

import java.util.Objects;

public class Supplement extends AbstractModel {

    public final String name;
    private final String supplement_type;
    private final String img;
    private final int price;
    private final boolean premium;

    public Supplement(int id, String name, String supplement_type, String img, int price, boolean premium){
        super(id);
        this.name = name;
        this.supplement_type = supplement_type;
        this.img = img;
        this.price = price;
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public String getSupplement_type() {
        return supplement_type;
    }

    public String getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }

    public boolean isPremium() {
        return premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Supplement supplement = (Supplement) o;
        return Objects.equals(name, supplement.name) &&
            Objects.equals(supplement_type, supplement.supplement_type) &&
            Objects.equals(img, supplement.img) &&
            Objects.equals(price, supplement.price) &&
            Objects.equals(premium, supplement.premium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, supplement_type, img, price, premium);
    }
}





//
//    id serial unique primary key,
//    name text unique not null,
//    supplement_type proteins,
//    img text unique not null,
//    price int not null,
//    premium boolean default false
