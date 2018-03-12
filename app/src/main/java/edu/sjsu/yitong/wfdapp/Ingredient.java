package edu.sjsu.yitong.wfdapp;

import java.io.Serializable;

/**
 * Created by yitong on 3/10/18.
 */

public class Ingredient implements Serializable{
    String name;
    String unit;
    int amount;

    public Ingredient(String description) {
        int i1 = description.indexOf('(');
        this.name = description.substring(0, i1).toLowerCase().trim();
        int i2 = description.substring(i1).indexOf(' ') + i1;
        this.amount = Integer.parseInt(description.substring(i1+1, i2).trim());
        this.unit = description.substring(i2, description.length()-1).toLowerCase().trim();
    }

    @Override
    public String toString() {
        return this.name + " (" + this.amount + " " + this.unit + ")";
    }
}
