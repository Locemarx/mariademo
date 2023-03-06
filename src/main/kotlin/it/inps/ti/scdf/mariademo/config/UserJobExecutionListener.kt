package it.inps.ti.scdf.mariademo.config

import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener


class UserJobExecutionListener : JobExecutionListener {

    var logger = LoggerFactory.getLogger(UserJobExecutionListener::class.java)

    override fun beforeJob(jobExecution: JobExecution) {
        logger.info("esecuzione avviata")
    }

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            logger.info("esecuzione completata")
        } else if (jobExecution.status == BatchStatus.FAILED) {
            logger.info("esecuzione fallita")
        }
    }
}