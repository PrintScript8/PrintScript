package utils

class ExpectedOutputProvider {

    private val firstVersionExpectedProvider = firstversionfiles.expected.ExpectedOutputProvider()
    private val secondVersionExpectedProvider = secondversionfiles.expected.ExpectedOutputProvider()

    fun provideExpectedOutput(number: Int, version: String): String {

        return when (version) {
            "1.0" -> firstVersionExpectedProvider.provideExpectedOutput(number)
            "1.1" -> secondVersionExpectedProvider.provideExpectedOutput(number)
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
