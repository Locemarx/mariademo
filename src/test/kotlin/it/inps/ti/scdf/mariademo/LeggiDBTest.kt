package it.inps.ti.scdf.mariademo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@SpringBootTest
class LeggiDBTest {

    @Autowired
    lateinit var dataSource: DataSource

    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun setup() {
        jdbcTemplate = JdbcTemplate(dataSource)
    }

    @Test
    fun testJobResults() {
        val items = this.jdbcTemplate.query("SELECT ITEM FROM NomeCognome"
        ) { rs, _ -> rs.getString("ITEM") }
        assertThat(items.size).isEqualTo(2)
    }

}