package strategy.dynamicStrategy

import node.dynamic.*
import strategy.FormatStrategy

class DynamicStrategyFactory {

    fun getStrategy(node: DynamicNode): FormatStrategy<DynamicNode> {
        return when (node) {
            is LiteralType -> LiteralTypeStrategy() as FormatStrategy<DynamicNode>
            is SumType, is SubtractType,
            is MultiplyType, is DivisionType -> OperationStrategy() as FormatStrategy<DynamicNode>
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }

}