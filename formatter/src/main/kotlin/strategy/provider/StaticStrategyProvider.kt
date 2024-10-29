package strategy.provider

import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IfElseType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import strategy.formatstrategy.FormatStrategy
import strategy.strategies.staticstrategy.AssignationStrategy
import strategy.strategies.staticstrategy.DeclarationStrategy
import strategy.strategies.staticstrategy.ExpressionStrategy
import strategy.strategies.staticstrategy.IfElseStrategy
import strategy.strategies.staticstrategy.PrintLnStrategy

class StaticStrategyProvider {

    fun getStrategy(node: StaticNode, version: String): FormatStrategy<StaticNode> {
        return when (node) {
            is DeclarationType -> DeclarationStrategy()
            is AssignationType -> AssignationStrategy()
            is PrintLnType -> PrintLnStrategy()
            is ExpressionType -> ExpressionStrategy()
            is IfElseType -> {
                require(version == "1.1") {
                    "IfElseType method not supported for version $version"
                }
                IfElseStrategy()
            }
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }
}
