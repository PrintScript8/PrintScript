package strategy.provider

import node.Node
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IfElseType
import node.staticpkg.PrintLnType
import strategy.formatstrategy.FormatStrategy
import strategy.strategies.staticStrategy.AssignationStrategy
import strategy.strategies.staticStrategy.DeclarationStrategy
import strategy.strategies.staticStrategy.ExpressionStrategy
import strategy.strategies.staticStrategy.IfElseStrategy
import strategy.strategies.staticStrategy.PrintLnStrategy

class StrategyProvider {

    fun provideStrategy(version: String): Map<Class<out Node>, FormatStrategy<out Node>> {
        return when (version) {
            "1.0" -> mapOf(
                PrintLnType::class.java to PrintLnStrategy(),
                AssignationType::class.java to AssignationStrategy(),
                ExpressionType::class.java to ExpressionStrategy(),
                DeclarationType::class.java to DeclarationStrategy(),
            )
            "1.1" -> mapOf(
                IfElseType::class.java to IfElseStrategy()
            ) + provideStrategy("1.0")
            else -> throw IllegalArgumentException("Unsupported version")
        }
    }
}
