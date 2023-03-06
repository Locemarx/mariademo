package it.inps.ti.scdf.mariademo.config

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class UserProcessor : ItemProcessor<String, User> {

    var logger = LoggerFactory.getLogger(UserProcessor::class.java)

    override fun process(nome_cognome: String): User {
        logger.info("USER $nome_cognome")
        val split = nome_cognome.split(" ")
        return User(split[0], split[1])
    }


}