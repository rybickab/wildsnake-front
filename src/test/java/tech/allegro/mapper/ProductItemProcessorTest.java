package tech.allegro.mapper;

import org.junit.Before;
import org.junit.Test;
import tech.allegro.domain.Product;
import tech.allegro.io.twitter.domain.Twitt;
import tech.allegro.mapper.exception.ProcessingException;

import static org.junit.Assert.*;

public class ProductItemProcessorTest {
    private ProductItemProcessor productItemProcessor;

    @Before
    public void setUp() throws Exception {
        productItemProcessor = new ProductItemProcessor();

    }

    @Test
    public void shouldProcessTwitt() throws Exception {
        // Given
        Twitt twitt = new Twitt("texttexttexttexttexttexttexttexttexttext");

        // When
        Product result = productItemProcessor.process(twitt);

        // Then
        assertEquals(result.getName(), "texttexttexttexttex");
        assertEquals(result.getDescription(), "texttexttexttexttexttexttexttexttexttext");
    }

    @Test
    public void shouldProcessShortTwitt() throws Exception {
        // Given
        Twitt twitt = new Twitt("short twitt");

        // When
        Product result = productItemProcessor.process(twitt);

        // Then
        assertEquals(result.getName(), "short twitt");
        assertEquals(result.getDescription(), "short twitt");
    }

    @Test(expected = ProcessingException.class)
    public void shouldThrowExceptionOnNullTwitt() throws Exception {
        // Given
        Twitt twitt = new Twitt(null);

        // When
        productItemProcessor.process(twitt);
    }

    @Test(expected = ProcessingException.class)
    public void shouldThrowExceptionOnEmptyTwitt() throws Exception {
        // Given
        Twitt twitt = new Twitt("");

        // When
        productItemProcessor.process(twitt);
    }
}