package sirin.pass.job.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import sirin.pass.utils.LocalDateTimeUtils;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MakeStatisticsJobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameter = new JobParametersBuilder()
            .addString("from", LocalDateTimeUtils.format(LocalDateTime.now()))
            .addString("to", LocalDateTimeUtils.format(LocalDateTime.now().plusDays(7)))
            .toJobParameters();

        jobLauncher.run(job, jobParameter);
    }

}
