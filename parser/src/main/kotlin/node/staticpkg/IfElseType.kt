package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode
import node.dynamic.LiteralValue
import operations.StaticVisitorV1

class IfElseType(
    val ifBranch: List<StaticNode>,
    val boolean: DynamicNode,
    private val elseBranch: List<StaticNode>?
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

    override fun format(version: String, indentLevel: Int): String {
        val indent = "    ".repeat(indentLevel)
        val innerIndent = "".repeat(indentLevel + 1)

        val ifBody = ifBranch.joinToString(separator = "\n") {
            "$innerIndent${it.format(version, indentLevel + 1)}"
        }

        val elseBody = elseBranch?.joinToString(separator = "\n") {
            "$innerIndent${it.format(version, indentLevel + 1)}"
        }

        return if (elseBody == null) {
            "${indent}if (${boolean.format(version)}) {\n" +
                "$ifBody\n" +
                "$indent}"
        } else {
            "${indent}if (${boolean.format(version)}) {\n" +
                "$ifBody\n" +
                "$indent} else {\n" +
                "$elseBody\n" +
                "$indent}"
        }
    }
}
