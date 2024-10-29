package tests

import provider.FormatterProvider
import kotlin.test.Test
import kotlin.test.assertFailsWith

class InvalidJsonTest {

    @Test
    fun runAllTests() {
        `number higher than 3 in newlineBeforePrintln`()
        `number lower than 0 in newlineBeforePrintln`()
        `missing rules in json`()
        `missing spaceBeforeColon in json`()
        `missing spaceAfterColon in json`()
        `missing spaceAroundEquals in json`()
        `missing newlineBeforePrintln in json`()
    }

    @Test
    fun `number higher than 3 in newlineBeforePrintln`() {
        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 4
            }
        }"""

        assertFailsWith<IllegalArgumentException> {
            FormatterProvider().provideFormatter(rules, "1.0")
        }
    }

    @Test
    fun `number lower than 0 in newlineBeforePrintln`() {
        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": -1
            }
        }"""

        assertFailsWith<IllegalArgumentException> {
            FormatterProvider().provideFormatter(rules, "1.0")
        }
    }

    @Test
    fun `missing rules in json`() {
        val rules = """{}"""

        assertFailsWith<IllegalArgumentException> {
            FormatterProvider().provideFormatter(rules, "1.0")
        }
    }

    @Test
    fun `missing spaceBeforeColon in json`() {
        val rules = """{
        "rules": {
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 2
            }
        }"""

        assertFailsWith<IllegalArgumentException> {
            FormatterProvider().provideFormatter(rules, "1.0")
        }
    }

    @Test
    fun `missing spaceAfterColon in json`() {
        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 2
            }
        }"""

        assertFailsWith<IllegalArgumentException> {
            FormatterProvider().provideFormatter(rules, "1.0")
        }
    }

    @Test
    fun `missing spaceAroundEquals in json`() {
        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "newlineBeforePrintln": 2
            }
        }"""

        assertFailsWith<IllegalArgumentException> {
            FormatterProvider().provideFormatter(rules, "1.0")
        }
    }

    @Test
    fun `missing newlineBeforePrintln in json`() {
        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true
            }
        }"""

        assertFailsWith<IllegalArgumentException> {
            FormatterProvider().provideFormatter(rules, "1.0")
        }
    }
}
