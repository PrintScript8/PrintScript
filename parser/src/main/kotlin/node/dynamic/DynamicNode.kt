package node.dynamic

import node.Node
import node.TypeValue
import operations.DynamicVisitorV1

sealed interface DynamicNode : Node {
    var result: LiteralValue?
    fun visit(visitor: DynamicVisitorV1)
    fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): TypeValue
}
