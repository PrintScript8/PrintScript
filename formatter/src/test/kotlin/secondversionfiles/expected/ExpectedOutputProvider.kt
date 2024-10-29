package secondversionfiles.expected

import utils.PathFactory

class ExpectedOutputProvider {

    private val pathFactory = PathFactory("src/test/kotlin/secondversionfiles")

    fun provideExpectedOutput(number: Int): String {
        return pathFactory.providePath(number, "expected")
    }
}
