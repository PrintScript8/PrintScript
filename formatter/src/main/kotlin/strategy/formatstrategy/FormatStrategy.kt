package strategy.formatstrategy

import json.FormattingRules
import node.Node
import java.io.Writer

interface FormatStrategy<out N : Node> {
    fun apply(node: @UnsafeVariance N, rules: FormattingRules, writer: Writer)
}
