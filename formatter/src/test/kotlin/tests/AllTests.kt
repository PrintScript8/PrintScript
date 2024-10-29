package tests

import kotlin.test.Test

class AllTests {

    @Test
    fun `run first and second version`() {
        FirstVersionTest().runAllTests()
        SecondVersionTest().runAllTests()
        InvalidJsonTest().runAllTests()
    }
}
