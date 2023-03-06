package it.inps.ti.scdf.mariademo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.task.configuration.EnableTask

@SpringBootApplication
@EnableTask
class MariademoApplication

fun main(args: Array<String>) {
	runApplication<MariademoApplication>(*args)
	}
