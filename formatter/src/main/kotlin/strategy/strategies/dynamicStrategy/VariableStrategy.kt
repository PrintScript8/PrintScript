package strategy.strategies.dynamicStrategy

import json.FormattingRules
import node.dynamic.VariableType
import strategy.formatstrategy.FormatStrategy
import java.io.Writer

class VariableStrategy : FormatStrategy<VariableType> {
    override fun apply(node: VariableType, rules: FormattingRules, writer: Writer) {
        writer.write(node.name)
    }
}
