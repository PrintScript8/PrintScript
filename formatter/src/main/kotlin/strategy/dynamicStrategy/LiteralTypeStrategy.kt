package strategy.dynamicStrategy

import json.FormattingRules
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import strategy.FormatStrategy
import java.io.Writer

class LiteralTypeStrategy : FormatStrategy<LiteralType> {

    override fun apply(node: LiteralType, rules: FormattingRules, writer: Writer) {

        when (node.result) {
            is LiteralValue.StringValue -> writer.write("\"${node.result.toString()}\"")
            is LiteralValue.NumberValue -> writer.write(node.result.toString())
            is LiteralValue.BooleanValue -> writer.write(node.result.toString())
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }
}
