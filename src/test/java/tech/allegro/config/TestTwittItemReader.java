package tech.allegro.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import tech.allegro.io.twitter.domain.Twitt;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestTwittItemReader implements ItemStreamReader<Twitt> {
    private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

    @Override
    public Twitt read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        ObjectMapper objectMapper = new ObjectMapper();
        String object = queue.poll();
        if (Objects.nonNull(object)) {
            return objectMapper.readValue(object, Twitt.class);
        }
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            queue.add(objectMapper.writeValueAsString(new Twitt("Szybki wąż za 50 USD legalny")));
            queue.add(objectMapper.writeValueAsString(new Twitt("Wolny wąż za 20 USD tez legalny")));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }
}
