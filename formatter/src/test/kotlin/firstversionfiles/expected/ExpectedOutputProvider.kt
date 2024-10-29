package firstversionfiles.expected

import utils.PathFactory

class ExpectedOutputProvider {

    private val pathFactory = PathFactory("src/test/kotlin/firstversionfiles")

    fun provideExpectedOutput(number: Int): String {
        return pathFactory.providePath(number, "expected")
    }
}
