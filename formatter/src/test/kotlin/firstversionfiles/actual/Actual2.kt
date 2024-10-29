package firstversionfiles.actual

import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode

class Actual2 {

    fun getAstList(): List<StaticNode> {
        val assignationTree: StaticNode =
            AssignationType(
                DeclarationType(
                    ModifierType("let", false),
                    IdentifierType(PrimType.BOOLEAN),
                    "a"
                ),
                LiteralType(LiteralValue.BooleanValue(true))
            )

        val assignationTree2: StaticNode =
            AssignationType(
                DeclarationType(
                    ModifierType("let", true),
                    IdentifierType(PrimType.BOOLEAN),
                    "b"
                ),
                LiteralType(LiteralValue.BooleanValue(false))
            )

        val printLnA: StaticNode = PrintLnType(
            VariableType("a", null)
        )

        val printLnB: StaticNode = PrintLnType(
            VariableType("b", null)
        )

        return listOf(assignationTree, assignationTree2, printLnA, printLnB)
    }
}
