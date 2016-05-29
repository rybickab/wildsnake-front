package tech.allegro.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import tech.allegro.domain.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JobProductImportExecutionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobProductImportExecutionListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobProductImportExecutionListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            List<Product> results = jdbcTemplate.query("SELECT name, description, image_url, price FROM product", new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int row) throws SQLException {
                    return new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4));
                }
            });

            for (Product product : results) {
                log.info("Found <" + product + "> in the database.");
            }

        }
    }
}
