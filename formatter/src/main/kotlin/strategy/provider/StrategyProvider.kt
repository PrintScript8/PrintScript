package strategy.provider

import node.Node
import node.staticpkg.*
import strategy.FormatStrategy
import strategy.staticStrategy.*

class StrategyProvider {

    fun provideStrategy(version: String) : Map<Class<out Node>, FormatStrategy<out Node>> {
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