package it.inps.ti.scdf.mariademo.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.task.configuration.EnableTask
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
class BatchConfiguration {

    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    fun jdbcReader(dataSource: DataSource): ItemReader<String> {
        return JdbcCursorItemReaderBuilder<String>()
            .dataSource(dataSource)
            .name("NomeCognomeReader")
            .sql("SELECT ITEM FROM NomeCognome")
            .rowMapper { rs, _ -> rs.getString("ITEM") }
            .build()
    }

    @Bean
    fun jdbcWriter(dataSource: DataSource): ItemWriter<User> {
        return JdbcBatchItemWriterBuilder<User>()
            .beanMapped()
            .dataSource(dataSource)
            .sql("INSERT INTO User (Nome, Cognome) VALUES (:nome, :cognome)")
            .build()
    }

    @Bean
    fun userProcessor(): ItemProcessor<String, User> = UserProcessor()

    @Bean
    fun stepMariaDB(jobRepository: JobRepository, transactionManager : PlatformTransactionManager): Step {
        return StepBuilder("stepMariaDB", jobRepository)
            .chunk<String, User>(10, transactionManager)
            .reader(jdbcReader(dataSource))
            .processor(userProcessor())
            .writer(jdbcWriter(dataSource))
            .build()
    }

    @Bean
    fun jobMariaDB(jobRepository: JobRepository, stepMariaDB: Step): Job {
        return JobBuilder("jobMariaDB", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(UserJobExecutionListener())
            .flow(stepMariaDB)
            .end()
            .build()
    }

}

