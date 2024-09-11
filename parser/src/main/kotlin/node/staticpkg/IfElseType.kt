package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode
import node.dynamic.LiteralValue
import operations.StaticVisitorV1

class IfElseType(
    private val ifBranch: StaticNode,
    val boolean: DynamicNode,
    private val elseBranch: StaticNode?
) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        TODO("Not yet implemented")
    }

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): StaticResult {
        require(version != "1.0") { "IfElse is not supported in version 1.0" }
        val (value, _) = boolean.execute(valueMap, version)
        require(value is LiteralValue.BooleanValue) { "IfElse condition must be a boolean" }
        return if (value.boolean) {
            ifBranch.execute(valueMap, version)
        } else {
            elseBranch?.execute(valueMap, version) ?: StaticResult(valueMap, listOf())
        }
    }

    override fun format(version: String): String {
        val ifBody: String = ifBranch.format(version)
        val elseBody: String? = elseBranch?.format(version)
        return if (elseBody == null) {
            "if (${boolean.format(version)}) {\n" +
                "    $ifBody\n" +
                "}"
        } else {
            "if (${boolean.format(version)}) {\n" +
                "    $ifBody\n" +
                "} else {\n" +
                "    $elseBody\n" +
                "}"
        }
    }
}
