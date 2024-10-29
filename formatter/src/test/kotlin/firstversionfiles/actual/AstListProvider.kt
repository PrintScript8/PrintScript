package firstversionfiles.actual

import node.staticpkg.StaticNode

class AstListProvider {

    fun provideAstList(number: Int): List<StaticNode> {

        return when (number) {
            1 -> Actual1().getAstList()
            2 -> Actual2().getAstList()
            3 -> Actual3().getAstList()
            4 -> Actual4().getAstList()
            else -> emptyList()
        }
    }
}
