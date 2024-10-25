package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.ExpressionType
import strategy.FormatStrategy
import strategy.dynamicStrategy.utils.DynamicStrategyFactory
import strategy.dynamicStrategy.VariableStrategy
import strategy.dynamicStrategy.utils.StrategyUtil
import java.io.Writer

class ExpressionStrategy: FormatStrategy<ExpressionType> {

    private val variableStrategy = VariableStrategy()
    private val dynamicStrategyFactory = DynamicStrategyFactory()

    override fun apply(node: ExpressionType, rules: FormattingRules, writer: Writer) {
        variableStrategy.apply(node.variable, rules, writer)
        StrategyUtil.applyDynamicStrategy(node.value, rules, writer, dynamicStrategyFactory)
    }
}
