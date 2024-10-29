package secondversionfiles.rule

import utils.PathFactory

class RuleProvider {

    private val pathFactory = PathFactory("src/test/kotlin/secondversionfiles")

    fun provideRules(number: Int): String {
        return pathFactory.providePath(number, "rule")
    }
}
