package tech.allegro.config;

import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.allegro.io.twitter.config.TwitterAccessProperties;
import tech.allegro.io.twitter.domain.Twitt;
import tech.allegro.io.twitter.reader.TwitterStreamItemReader;

@Configuration
@Profile({"!test"})
public class TwitterStreamItemReaderConfig {

    @Autowired
    TwitterAccessProperties accessProperties;

    @Bean
    public ItemStreamReader<Twitt> twittReader() {
        return new TwitterStreamItemReader(accessProperties);
    }
}
