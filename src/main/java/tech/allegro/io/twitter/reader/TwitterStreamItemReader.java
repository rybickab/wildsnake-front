package tech.allegro.io.twitter.reader;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import tech.allegro.io.twitter.config.TwitterAccessProperties;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterStreamItemReader<T extends String> implements ItemStreamReader<T> {
    private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
    private StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
    private Client client;
    private final TwitterAccessProperties accessProperties;

    public TwitterStreamItemReader(TwitterAccessProperties accessProperties) {
        this.accessProperties = accessProperties;
        System.out.println(accessProperties.getAccessToken());
        System.out.println(accessProperties.getAccessTokenSecret());
        System.out.println(accessProperties.getConsumerKey());
        System.out.println(accessProperties.getConsumerSecret());
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return (T) queue.take();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        endpoint.trackTerms(Lists.newArrayList("snake"));
        Authentication auth = new OAuth1(accessProperties.getConsumerKey(),
                accessProperties.getConsumerSecret(),
                accessProperties.getAccessToken(),
                accessProperties.getAccessTokenSecret());

        client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        client.connect();
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
    }

    @Override
    public void close() throws ItemStreamException {
        client.stop();
    }
}
