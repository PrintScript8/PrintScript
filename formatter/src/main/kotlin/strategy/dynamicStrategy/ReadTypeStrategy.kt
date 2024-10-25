package strategy.dynamicStrategy

import json.FormattingRules
import node.dynamic.DynamicNode
import node.dynamic.ReadEnvType
import node.dynamic.ReadInputType
import strategy.FormatStrategy
import strategy.dynamicStrategy.utils.DynamicStrategyFactory
import java.io.Writer

class ReadTypeStrategy: FormatStrategy<DynamicNode> {

    private val dynamicStrategyFactory = DynamicStrategyFactory()

    override fun apply(node: DynamicNode, rules: FormattingRules, writer: Writer) {

        when (node) {
            is ReadInputType -> {
                writer.write("readInput(")
                writeOperation(node.argument, rules, writer)
                writer.write(")")
            }
            is ReadEnvType -> {
                writer.write("readEnv(")
                writeOperation(node.argument, rules, writer)
                writer.write(")")
            }
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }

    private fun writeOperation(argument: DynamicNode, rules: FormattingRules, writer: Writer) {
        val argumentStrategy = dynamicStrategyFactory.getStrategy(argument)
        argumentStrategy.apply(argument, rules, writer)
    }
}