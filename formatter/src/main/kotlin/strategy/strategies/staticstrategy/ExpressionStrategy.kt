package strategy.strategies.staticstrategy

import json.FormattingRules
import node.staticpkg.ExpressionType
import strategy.formatstrategy.FormatStrategy
import strategy.provider.DynamicStrategyProvider
import strategy.strategies.dynamicstrategy.VariableStrategy
import strategy.strategies.dynamicstrategy.utils.StrategyUtil
import java.io.Writer

class ExpressionStrategy : FormatStrategy<ExpressionType> {

    private val variableStrategy = VariableStrategy()
    private val dynamicStrategyProvider = DynamicStrategyProvider()

    override fun apply(node: ExpressionType, rules: FormattingRules, writer: Writer) {
        variableStrategy.apply(node.variable, rules, writer)
        StrategyUtil.applyDynamicStrategy(node.value, rules, writer, dynamicStrategyProvider)
    }
}
