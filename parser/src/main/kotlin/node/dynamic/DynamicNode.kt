package node.dynamic

import node.Node
import node.TypeValue

sealed interface DynamicNode : Node {
    var result: LiteralValue?
    fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): TypeValue
}
