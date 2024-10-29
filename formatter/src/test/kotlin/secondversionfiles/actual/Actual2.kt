package secondversionfiles.actual

import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.IfElseType
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

        val printLnA: StaticNode = PrintLnType(
            LiteralType(LiteralValue.StringValue("a is true"))
        )

        val printLnB: StaticNode = PrintLnType(
            LiteralType(LiteralValue.StringValue("a is false"))
        )

        val ifElse: StaticNode = IfElseType(
            listOf(printLnA),
            VariableType("a", null),
            listOf(printLnB)
        )

        return listOf(assignationTree, ifElse)
    }
}
