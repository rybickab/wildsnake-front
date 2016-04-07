package tech.allegro.config;


import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import tech.allegro.domain.Product;
import tech.allegro.io.twitter.config.TwitterAccessProperties;
import tech.allegro.io.twitter.domain.Twitt;
import tech.allegro.io.twitter.reader.TwitterStreamItemReader;
import tech.allegro.mapper.ProductItemProcessor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private ItemStreamReader<Twitt> itemStreamReader;

    // tag::readerwriterprocessor[]

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product> writer() {
        JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<Product>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
        writer.setSql("INSERT INTO product (name, description, image_url, price) VALUES (:name, :description, :image_url, :price)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::listener[]

    @Bean
    public JobExecutionListener listener() {
        return new JobProductImportExecutionListener(new JdbcTemplate(dataSource));
    }

    // end::listener[]

    // tag::jobstep[]
    @Bean
    public Job importProductsJob() {
        return jobBuilderFactory.get("importProductsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(consumeTwitterStream())
                .end()
                .build();
    }

    @Bean
    public Step consumeTwitterStream() {
        return stepBuilderFactory.get("consumeTwitterStream")
                .<Twitt,Product>chunk(10)
                .reader(itemStreamReader)
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
}
