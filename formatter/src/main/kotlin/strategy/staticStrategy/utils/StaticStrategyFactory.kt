package strategy.staticStrategy.utils

import node.staticpkg.*
import strategy.FormatStrategy
import strategy.staticStrategy.*

class StaticStrategyFactory {

    fun getStrategy(node: StaticNode): FormatStrategy<StaticNode> {
        return when (node) {
            is DeclarationType -> DeclarationStrategy() as FormatStrategy<StaticNode>
            is AssignationType -> AssignationStrategy() as FormatStrategy<StaticNode>
            is PrintLnType -> PrintLnStrategy() as FormatStrategy<StaticNode>
            is ExpressionType -> ExpressionStrategy() as FormatStrategy<StaticNode>
            is IfElseType -> IfElseStrategy() as FormatStrategy<StaticNode>
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }

}