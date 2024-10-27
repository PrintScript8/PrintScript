package strategy.strategies.staticStrategy

import json.FormattingRules
import node.staticpkg.IdentifierType
import strategy.formatstrategy.FormatStrategy
import java.io.Writer

class IdentifierStrategy : FormatStrategy<IdentifierType> {

    override fun apply(node: IdentifierType, rules: FormattingRules, writer: Writer) {

        writer.write(node.type.toString().lowercase())
    }
}
