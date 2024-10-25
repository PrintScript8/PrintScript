package strategy.dynamicStrategy

import node.dynamic.DynamicNode
import node.dynamic.LiteralType
import strategy.FormatStrategy

class DynamicStrategyFactory {

    fun getStrategy(node: DynamicNode): FormatStrategy<DynamicNode> {
        return when (node) {
            is LiteralType -> LiteralTypeStrategy() as FormatStrategy<DynamicNode>
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }

}