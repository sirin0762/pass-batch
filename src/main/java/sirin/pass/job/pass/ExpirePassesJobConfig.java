package sirin.pass.job.pass;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import sirin.pass.repository.pass.PassEntity;
import sirin.pass.repository.pass.PassStatus;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

@EnableBatchProcessing
@RequiredArgsConstructor
public class ExpirePassesJobConfig {

    private final int CHUNK_SIZE = 5;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job expirePassesJob() {
        return this.jobBuilderFactory.get("expirePassesJob")
            .start(expirePassesStep())
            .build();
    }

    @Bean
    public Step expirePassesStep() {
        return this.stepBuilderFactory.get("expirePassesStep")
            .<PassEntity, PassEntity> chunk(CHUNK_SIZE)
            .reader(expirePassItemReader())
            .processor(expirePassesItemProcessor())
            .writer(expirePassesItemWriter())
            .build();
    }

    @Bean
    @StepScope
    private JpaCursorItemReader<PassEntity> expirePassItemReader() {
        return new JpaCursorItemReaderBuilder<PassEntity>()
            .name("expirePassItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select p from PassEntity p where p.status = :status and p.endedAt <= :endedAt")
            .parameterValues(Map.of("status", PassStatus.IN_PROGRESS, "endedAt", LocalDateTime.now()))
            .build();
    }

    @Bean
    private ItemProcessor<PassEntity, PassEntity> expirePassesItemProcessor() {
        return passEntity -> {
            passEntity.setStatus(PassStatus.EXPIRED);
            passEntity.setExpiredAt(LocalDateTime.now());
            return passEntity;
        };
    }

    @Bean
    private ItemWriter<? super PassEntity> expirePassesItemWriter() {
        return new JpaItemWriterBuilder<PassEntity>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

}
