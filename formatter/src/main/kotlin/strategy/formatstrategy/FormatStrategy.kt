package strategy.formatstrategy

import json.FormattingRules
import node.Node
import java.io.Writer

interface FormatStrategy<N : Node> {
    fun apply(node: N, rules: FormattingRules, writer: Writer)
}
