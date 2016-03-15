package tech.allegro.domain;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final String description;
    private final String image_url;
    private final BigDecimal price;

    public Product(String name, String description, String image_url, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", price=" + price +
                '}';
    }
}
