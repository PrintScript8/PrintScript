package strategy.provider

import node.dynamic.*
import strategy.dynamicStrategy.LiteralTypeStrategy
import strategy.formatstrategy.FormatStrategy
import strategy.strategies.dynamicStrategy.OperationStrategy
import strategy.strategies.dynamicStrategy.ReadTypeStrategy
import strategy.strategies.dynamicStrategy.VariableStrategy

class DynamicStrategyProvider {
    
    fun getStrategy(node: DynamicNode, version: String): FormatStrategy<DynamicNode> {
        return when (node) {
            is LiteralType -> LiteralTypeStrategy() as FormatStrategy<DynamicNode>
            is SumType, is SubtractType,
            is MultiplyType, is DivisionType -> OperationStrategy()
            is ReadInputType, is ReadEnvType -> {
                if (version == "1.1") {
                    ReadTypeStrategy()
                } else {
                    throw IllegalArgumentException("ReadType method not supported in version 1.1")
                }
            }
            is VariableType -> VariableStrategy() as FormatStrategy<DynamicNode>
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }
}