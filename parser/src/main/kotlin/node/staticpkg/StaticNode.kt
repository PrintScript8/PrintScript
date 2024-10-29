package node.staticpkg

import node.Node
import node.TypeValue

sealed interface StaticNode : Node {
    fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): StaticResult
}
