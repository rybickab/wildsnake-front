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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WildsnakeFeederApplication.class)
@ActiveProfiles("test")
public class WildsnakeFeederApplicationTests {
	private static final String deleteSql = "DELETE FROM product";


	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() throws Exception{
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
}
