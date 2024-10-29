package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode

class PrintLnType(val argument: DynamicNode) : StaticNode {

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        val output = argument.execute(valueMap, version)
        return StaticResult(valueMap, listOf(output.value.toString()))
    }

    override fun toString(): String {
        return "PrintLnType(argument='$argument')"
    }
}
