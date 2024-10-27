package strategy.strategies.staticStrategy

import json.FormattingRules
import node.staticpkg.ModifierType
import strategy.formatstrategy.FormatStrategy
import java.io.Writer

class ModifierStrategy : FormatStrategy<ModifierType> {

    override fun apply(node: ModifierType, rules: FormattingRules, writer: Writer) {
        writer.write(node.value)
    }
}
