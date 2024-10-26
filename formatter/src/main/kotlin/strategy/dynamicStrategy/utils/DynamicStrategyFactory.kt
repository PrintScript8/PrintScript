package strategy.dynamicStrategy.utils

import node.dynamic.*
import strategy.FormatStrategy
import strategy.dynamicStrategy.LiteralTypeStrategy
import strategy.dynamicStrategy.OperationStrategy
import strategy.dynamicStrategy.ReadTypeStrategy
import strategy.dynamicStrategy.VariableStrategy

class DynamicStrategyFactory {

    fun getStrategy(node: DynamicNode): FormatStrategy<DynamicNode> {
        return when (node) {
            is LiteralType -> LiteralTypeStrategy() as FormatStrategy<DynamicNode>
            is SumType, is SubtractType,
            is MultiplyType, is DivisionType -> OperationStrategy()
            is ReadInputType, is ReadEnvType -> ReadTypeStrategy()
            is VariableType -> VariableStrategy() as FormatStrategy<DynamicNode>
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }

}