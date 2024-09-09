package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode
import operations.StaticVisitorV1

class PrintLnType(val argument: DynamicNode) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        visitor.acceptPrintLn(this)
    }

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        val output = argument.execute(valueMap, version)
        return StaticResult(valueMap, listOf(output.value.toString()))
    }

    override fun format(version: String): String {
        return "println(${argument.format(version)});"
    }

    override fun toString(): String {
        return "PrintLnType(argument='$argument')"
    }
}
