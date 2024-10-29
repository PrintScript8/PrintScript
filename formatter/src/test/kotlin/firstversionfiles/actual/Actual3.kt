package firstversionfiles.actual

import node.dynamic.DivisionType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.dynamic.VariableType
import node.staticpkg.ExpressionType
import node.staticpkg.StaticNode

class Actual3 {

    private val result = null

    fun getAstList(): List<StaticNode> {
        val assignationTree: StaticNode = ExpressionType(
            VariableType(name = "operation", result),
            MultiplyType(
                SumType(
                    LiteralType(LiteralValue.NumberValue(1)),
                    LiteralType(LiteralValue.NumberValue(2)),
                    result
                ),
                DivisionType(
                    SubtractType(
                        LiteralType(LiteralValue.NumberValue(4)),
                        LiteralType(LiteralValue.NumberValue(5)),
                        result
                    ),
                    LiteralType(LiteralValue.NumberValue(6)),
                    result
                ),
                result
            )
        )

        return listOf(assignationTree)
    }
}
