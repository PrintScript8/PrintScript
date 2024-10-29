package firstversionfiles.actual

import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode

class Actual4 {

    fun getAstList(): List<StaticNode> {
        val assignationTree: StaticNode =
            AssignationType(
                DeclarationType(
                    ModifierType("let", false),
                    IdentifierType(PrimType.STRING),
                    "ugly_test"
                ),
                LiteralType(LiteralValue.StringValue("ugly test"))
            )

        val printLn: StaticNode = PrintLnType(
            LiteralType(LiteralValue.StringValue("ugly test"))
        )

        return listOf(assignationTree, printLn)
    }
}
