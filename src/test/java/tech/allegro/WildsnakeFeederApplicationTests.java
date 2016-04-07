package tech.allegro;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.allegro.config.TestTwittItemReader;
import tech.allegro.io.twitter.domain.Twitt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WildsnakeFeederApplication.class)
@ActiveProfiles("test")
public class WildsnakeFeederApplicationTests {
	private static final String deleteSql = "DELETE FROM product";

	@Test
	public void shouldFinishJobSuccessfully() throws Exception{
		//given
		itemStreamReader.addStubTwitt(new Twitt("wolny wąż za 50 USD w pelni legalny"));
		itemStreamReader.addStubTwitt(new Twitt("szybki wąż za 100 USD też legal"));

		//when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		StepExecution firstStepExecution = jobExecution.getStepExecutions().iterator().next();

		//then
		assertThat("job execution status", jobExecution.getExitStatus(), is(ExitStatus.COMPLETED));
	}

	@Before
	public void setup() {
		jdbcTemplate.update(deleteSql);
	}

	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	TestTwittItemReader itemStreamReader;
}
