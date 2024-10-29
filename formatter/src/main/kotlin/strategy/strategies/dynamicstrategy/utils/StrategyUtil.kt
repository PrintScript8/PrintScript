package strategy.strategies.dynamicstrategy.utils

import json.FormattingRules
import node.dynamic.DynamicNode
import strategy.formatstrategy.FormatStrategy
import strategy.provider.DynamicStrategyProvider
import java.io.Writer

object StrategyUtil {

    fun applyDynamicStrategy(
        node: DynamicNode,
        rules: FormattingRules,
        writer: Writer,
        dynamicStrategyProvider: DynamicStrategyProvider
    ) {
        if (rules.spaceAroundEquals) {
            writer.write(" = ")
        } else {
            writer.write("=")
        }

        val dynamicStrategy = dynamicStrategyProvider.getStrategy(node, rules.version) as FormatStrategy<DynamicNode>
        dynamicStrategy.apply(node, rules, writer)
        writer.write(";")
    }
}
