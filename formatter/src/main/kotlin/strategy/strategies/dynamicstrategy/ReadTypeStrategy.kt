package strategy.strategies.dynamicstrategy

import json.FormattingRules
import node.dynamic.DynamicNode
import node.dynamic.ReadEnvType
import node.dynamic.ReadInputType
import strategy.formatstrategy.FormatStrategy
import strategy.provider.DynamicStrategyProvider
import java.io.Writer

class ReadTypeStrategy : FormatStrategy<DynamicNode> {

    private val dynamicStrategyProvider = DynamicStrategyProvider()

    override fun apply(node: DynamicNode, rules: FormattingRules, writer: Writer) {

        require(rules.version == "1.1") {
            "ReadType method not supported for version ${rules.version}"
        }

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
        val argumentStrategy = dynamicStrategyProvider.getStrategy(argument, rules.version)
        argumentStrategy.apply(argument, rules, writer)
    }
}
