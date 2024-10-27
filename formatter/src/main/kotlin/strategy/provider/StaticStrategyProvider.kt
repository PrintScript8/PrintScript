package strategy.provider

import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IfElseType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import strategy.formatstrategy.FormatStrategy
import strategy.strategies.staticStrategy.AssignationStrategy
import strategy.strategies.staticStrategy.DeclarationStrategy
import strategy.strategies.staticStrategy.ExpressionStrategy
import strategy.strategies.staticStrategy.IfElseStrategy
import strategy.strategies.staticStrategy.PrintLnStrategy

class StaticStrategyProvider {

    fun getStrategy(node: StaticNode, version: String): FormatStrategy<StaticNode> {
        return when (node) {
            is DeclarationType -> DeclarationStrategy() as FormatStrategy<StaticNode>
            is AssignationType -> AssignationStrategy() as FormatStrategy<StaticNode>
            is PrintLnType -> PrintLnStrategy() as FormatStrategy<StaticNode>
            is ExpressionType -> ExpressionStrategy() as FormatStrategy<StaticNode>
            is IfElseType -> {
                if (version == "1.0") {
                    throw IllegalArgumentException("If-Else structure is not supported in version 1.1")
                }
                IfElseStrategy() as FormatStrategy<StaticNode>
            }
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }
}