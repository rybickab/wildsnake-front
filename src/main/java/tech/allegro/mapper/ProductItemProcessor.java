package tech.allegro.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import tech.allegro.domain.Product;
import tech.allegro.io.twitter.domain.Twitt;
import tech.allegro.mapper.exception.ProcessingException;

import java.math.BigDecimal;

public class ProductItemProcessor implements ItemProcessor<Twitt, Product> {
    private final static String dummyUrl = "http://imageStatic/photo";
    private final static int PRODUCT_NAME_BEGIN_INDEX = 0;
    private final static int PRODUCT_NAME_MAX_SIZE = 19;

    @Override
    public Product process(Twitt twitt) throws Exception {
        if (twitt.getText() == null || twitt.getText().isEmpty()) {
            throw new ProcessingException("Twitt was empty");
        }
        return new Product(
                getName(twitt.getText()),
                twitt.getText(),
                dummyUrl,
                BigDecimal.ONE
        );
    }

    private String getName(String text) {
        if (text.length() > PRODUCT_NAME_MAX_SIZE) {
            return text.substring(PRODUCT_NAME_BEGIN_INDEX, PRODUCT_NAME_MAX_SIZE);
        }
        return text;
    }
}
