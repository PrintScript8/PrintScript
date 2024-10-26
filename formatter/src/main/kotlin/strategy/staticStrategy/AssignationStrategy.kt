package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.AssignationType
import strategy.provider.DynamicStrategyProvider
import strategy.FormatStrategy
import strategy.dynamicStrategy.utils.StrategyUtil
import java.io.Writer

class AssignationStrategy : FormatStrategy<AssignationType> {

    private val declarationStrategy = DeclarationStrategy()
    private val dynamicStrategyProvider = DynamicStrategyProvider()

    override fun apply(node: AssignationType, rules: FormattingRules, writer: Writer) {
        declarationStrategy.apply(node.declaration, rules, writer, addSemicolon = false)
        StrategyUtil.applyDynamicStrategy(node.value, rules, writer, dynamicStrategyProvider)
    }
}
