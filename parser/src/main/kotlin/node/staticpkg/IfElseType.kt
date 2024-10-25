package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode
import node.dynamic.LiteralValue
import operations.StaticVisitorV1

class IfElseType(
    val ifBranch: List<StaticNode>,
    val boolean: DynamicNode,
    val elseBranch: List<StaticNode>?
) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        TODO("Not yet implemented")
    }

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): StaticResult {
        require(version != "1.0") { "IfElse is not supported in version 1.0" }
        val (value, _) = boolean.execute(valueMap, version)
        require(value is LiteralValue.BooleanValue) { "IfElse condition must be a boolean" }
        return if (value.boolean) {
            iterateChildren(ifBranch, valueMap, version)
        } else {
            iterateChildren(elseBranch, valueMap, version)
        }
    }

    private fun iterateChildren(
        children: List<StaticNode>?,
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        if (children == null) return StaticResult(valueMap, listOf())
        val output = mutableListOf<String>()
        var valueMapCopy = valueMap
        for (child in children) {
            val (value, list) = child.execute(valueMap, version)
            output.addAll(list)
            valueMapCopy = valueMapCopy.plus(value)
        }
        return StaticResult(valueMapCopy, output)
    }
}
