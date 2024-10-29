package secondversionfiles.actual

import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.ReadEnvType
import node.dynamic.ReadInputType
import node.dynamic.VariableType
import node.staticpkg.ExpressionType
import node.staticpkg.StaticNode

class Actual1 {

    private val result = null

    fun getAstList(): List<StaticNode> {

        val readInput = ReadInputType(
            LiteralType(
                LiteralValue.StringValue(
                    "Enter a number"
                )
            ),
            result
        )

        val readEnv = ReadEnvType(
            LiteralType(
                LiteralValue.StringValue(
                    "DATABASE_URL"
                )
            ),
            result
        )

        val firstExp: StaticNode = ExpressionType(
            VariableType(name = "input", result),
            readInput
        )

        val secondExp: StaticNode = ExpressionType(
            VariableType(name = "env", result),
            readEnv
        )

        return listOf(firstExp, secondExp)
    }
}
