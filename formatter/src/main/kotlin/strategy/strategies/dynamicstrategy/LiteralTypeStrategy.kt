package dynamicstrategy

import json.FormattingRules
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import strategy.formatstrategy.FormatStrategy
import java.io.Writer

class LiteralTypeStrategy : FormatStrategy<LiteralType> {

    override fun apply(node: LiteralType, rules: FormattingRules, writer: Writer) {

        when (node.result) {
            is LiteralValue.StringValue -> writer.write("\"${node.result}\"")
            is LiteralValue.NumberValue -> writer.write(node.result.toString())
            is LiteralValue.BooleanValue -> writer.write(node.result.toString())
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }
}
