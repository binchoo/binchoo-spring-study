package org.binchoo.study.spring.resttemplate.common;

/**
 * Created by iuliana.cosmina on 4/2/17.
 */
public class Guitar {
    private String brand =" Martin";

    public String play(){
        return "G C G C Am D7";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
