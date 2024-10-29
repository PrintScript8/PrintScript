package utils

import node.staticpkg.StaticNode

class AstProvider {

    private val firstVersionAstListProvider = firstversionfiles.actual.AstListProvider()
    private val secondVersionAstListProvider = secondversionfiles.actual.AstListProvider()

    fun providerAstList(fileNumber: Int, version: String): List<StaticNode> {

        return when (version) {
            "1.0" -> firstVersionAstListProvider.provideAstList(fileNumber)
            "1.1" -> secondVersionAstListProvider.provideAstList(fileNumber)
            else -> emptyList()
        }
    }
}
