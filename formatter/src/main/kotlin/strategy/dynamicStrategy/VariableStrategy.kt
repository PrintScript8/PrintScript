package strategy.dynamicStrategy

import json.FormattingRules
import node.dynamic.VariableType
import strategy.FormatStrategy
import java.io.Writer

class VariableStrategy: FormatStrategy<VariableType> {
    override fun apply(node: VariableType, rules: FormattingRules, writer: Writer) {
        writer.write(node.name)
    }
}