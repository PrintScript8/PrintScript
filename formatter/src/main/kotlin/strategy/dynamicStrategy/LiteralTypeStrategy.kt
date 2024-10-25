package strategy.dynamicStrategy

import json.FormattingRules
import node.dynamic.LiteralType
import strategy.FormatStrategy
import java.io.Writer

class LiteralTypeStrategy : FormatStrategy<LiteralType> {

    override fun apply(node: LiteralType, rules: FormattingRules, writer: Writer) {
        writer.write(node.result.toString())
    }
}
