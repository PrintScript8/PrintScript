package strategy.provider

import dynamicstrategy.LiteralTypeStrategy
import node.dynamic.DivisionType
import node.dynamic.DynamicNode
import node.dynamic.LiteralType
import node.dynamic.MultiplyType
import node.dynamic.ReadEnvType
import node.dynamic.ReadInputType
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.dynamic.VariableType
import strategy.formatstrategy.FormatStrategy
import strategy.strategies.dynamicstrategy.OperationStrategy
import strategy.strategies.dynamicstrategy.ReadTypeStrategy
import strategy.strategies.dynamicstrategy.VariableStrategy

class DynamicStrategyProvider {

    fun getStrategy(node: DynamicNode, version: String): FormatStrategy<DynamicNode> {
        return when (node) {
            is LiteralType -> LiteralTypeStrategy() as FormatStrategy<DynamicNode>
            is SumType, is SubtractType,
            is MultiplyType, is DivisionType -> OperationStrategy()
            is ReadInputType, is ReadEnvType -> {
                require(version == "1.1") {
                    "ReadType method not supported for version $version"
                }
                ReadTypeStrategy()
            }
            is VariableType -> VariableStrategy() as FormatStrategy<DynamicNode>
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }
}
