package tech.allegro.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import tech.allegro.domain.Product;
import tech.allegro.io.twitter.domain.Twitt;

import java.math.BigDecimal;

public class ProductItemProcessor implements ItemProcessor<String, Product> {
    private final static String dummyUrl = "http://imageStatic/photo";
    private final static int PRODUCT_NAME_BEGIN_INDEX = 0;
    private final static int PRODUCT_NAME_MAX_SIZE = 19;

    @Override
    public Product process(String item) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Twitt twitt = objectMapper.readValue(item, Twitt.class);

        return new Product(
                twitt.getText().substring(PRODUCT_NAME_BEGIN_INDEX, PRODUCT_NAME_MAX_SIZE),
                twitt.getText(),
                dummyUrl,
                BigDecimal.ONE
        );
    }
}
