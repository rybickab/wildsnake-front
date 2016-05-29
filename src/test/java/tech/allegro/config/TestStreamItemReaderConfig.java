package tech.allegro.config;


import org.springframework.batch.item.ItemStreamReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.allegro.io.twitter.domain.Twitt;

@Configuration
@Profile({"test"})
public class TestStreamItemReaderConfig {

    @Bean
    public ItemStreamReader<Twitt> twittReader() {
        return new TestTwittItemReader();
    }
}
