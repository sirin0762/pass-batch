package sirin.pass.job.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import sirin.pass.repository.booking.BookingEntity;
import sirin.pass.repository.booking.BookingStatus;
import sirin.pass.repository.notification.NotificationEntity;
import sirin.pass.repository.notification.NotificationEvent;
import sirin.pass.repository.notification.NotificationModelMapper;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SendNotificationBeforeClassConfig {
    private final int CHUNK_SIZE = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final SendNotificationItemWriter sendNotificationItemWriter;

    @Bean
    public Job sendNotificationBeforeClassJob() {
        return this.jobBuilderFactory.get("sendNotificationBeforeClassJob")
            .start(addNotificationStep())
            .next(sendNotificationStep())
            .build();
    }


    @Bean
    public Step addNotificationStep() {
        return stepBuilderFactory.get("addNotificationStep")
            .<BookingEntity, NotificationEntity>chunk(CHUNK_SIZE)
            .reader(addNotificationItemReader())
            .processor(addNotificationItemProcessor())
            .writer(addNotificationItemWriter())
            .build();
    }

    @Bean
    public JpaItemWriter<NotificationEntity> addNotificationItemWriter() {
        return new JpaItemWriterBuilder<NotificationEntity>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    public JpaPagingItemReader<BookingEntity> addNotificationItemReader() {
        return new JpaPagingItemReaderBuilder<BookingEntity>()
            .name("addNotificationItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString("select b from BookingEntity b join fetch b.userEntity where b.status = :status and b.startedAt <= :startedAt order by b.bookingSeq")
            .parameterValues(Map.of("status", BookingStatus.READY, "startedAt", LocalDateTime.now().plusMinutes(10)))
            .build();
    }

    @Bean
    public ItemProcessor<BookingEntity, NotificationEntity> addNotificationItemProcessor() {
        return bookingEntity -> NotificationModelMapper.INSTANCE
            .toNotificationEntity(bookingEntity, NotificationEvent.BEFORE_CLASS);
    }

    @Bean
    public Step sendNotificationStep() {
        return this.stepBuilderFactory.get("sendNotificationStep")
            .<NotificationEntity, NotificationEntity>chunk(CHUNK_SIZE)
            .reader(sendNotificationItemReader())
            .writer(sendNotificationItemWriter)
            .taskExecutor(new SimpleAsyncTaskExecutor())
            .build();
    }

    @Bean
    public SynchronizedItemStreamReader<NotificationEntity> sendNotificationItemReader() {
        JpaCursorItemReader<NotificationEntity> itemReader = new JpaCursorItemReaderBuilder<NotificationEntity>()
            .name("sendNotificationItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select n from NotificationEntity n where n.event = :event and sent = :sent order by n.notificationSeq")
            .parameterValues(Map.of("event", NotificationEvent.BEFORE_CLASS, "sent", false))
            .build();

        return new SynchronizedItemStreamReaderBuilder<NotificationEntity>()
            .delegate(itemReader)
            .build();
    }

}
