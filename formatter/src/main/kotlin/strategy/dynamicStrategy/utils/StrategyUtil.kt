package strategy.dynamicStrategy.utils

import json.FormattingRules
import node.dynamic.DynamicNode
import java.io.Writer

object StrategyUtil {

    fun applyDynamicStrategy(node: DynamicNode, rules: FormattingRules, writer: Writer, dynamicStrategyFactory: DynamicStrategyFactory) {
        if (rules.spaceAroundEquals) {
            writer.write(" = ")
        } else {
            writer.write("=")
        }

        val dynamicStrategy = dynamicStrategyFactory.getStrategy(node)
        dynamicStrategy.apply(node, rules, writer)
        writer.write(";")
    }
}