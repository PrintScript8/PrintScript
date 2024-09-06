package node.staticpkg

import node.Node
import node.TypeValue
import operations.StaticVisitorV1

sealed interface StaticNode : Node {
    fun visit(visitor: StaticVisitorV1)
    fun run(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): StaticResult
}
