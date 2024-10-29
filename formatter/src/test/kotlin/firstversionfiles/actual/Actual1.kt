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

class Actual1 {

    fun getAstList(): List<StaticNode> {
        val assignationTree: StaticNode =
            AssignationType(
                DeclarationType(
                    ModifierType("let", false),
                    IdentifierType(PrimType.STRING),
                    "name"
                ),
                LiteralType(LiteralValue.StringValue("Tom"))
            )

        val assignationTree2: StaticNode =
            AssignationType(
                DeclarationType(
                    ModifierType("let", true),
                    IdentifierType(PrimType.NUMBER),
                    "i"
                ),
                LiteralType(LiteralValue.NumberValue(0))
            )

        val println: StaticNode = PrintLnType(
            LiteralType(LiteralValue.NumberValue(2))
        )

        return listOf(assignationTree, assignationTree2, println)
    }
}
