package formatter

import json.FormattingRules
import json.parseJsonRules
import node.Node
import node.staticpkg.*
import strategy.*
import strategy.staticStrategy.AssignationStrategy
import strategy.staticStrategy.DeclarationStrategy
import strategy.staticStrategy.PrintLnStrategy
import java.io.StringWriter

class Formatter(rules: String) {

    private val strategies: Map<Class<out Node>, FormatStrategy<out Node>> = mapOf(
        DeclarationType::class.java to DeclarationStrategy(),
        AssignationType::class.java to AssignationStrategy(),
        PrintLnType::class.java to PrintLnStrategy(),
    )

    private val parsedRules: FormattingRules = parseJsonRules(rules)

    fun format(nodes: Iterator<StaticNode>): String {
        val writer = StringWriter()

        if (nodes.hasNext()) {
            val firstNode = nodes.next()
            if (firstNode is PrintLnType) {
                printLnAsFirstNode(firstNode, writer)
            } else {
                val strategy = strategies[firstNode.javaClass] as? FormatStrategy<StaticNode>
                strategy?.apply(firstNode, parsedRules, writer)
            }
        }

        nodes.forEach { node ->
            val strategy = strategies[node.javaClass] as? FormatStrategy<StaticNode>
            strategy?.apply(node, parsedRules, writer)
        }
        return writer.toString()
    }

    private fun printLnAsFirstNode(node: PrintLnType, writer: StringWriter) {
        val originalRuleNumber = parsedRules.newlineBeforePrintln
        parsedRules.newlineBeforePrintln = 0
        val strategy = strategies[node.javaClass] as? FormatStrategy<StaticNode>
        strategy?.apply(node, parsedRules, writer)
        parsedRules.newlineBeforePrintln = originalRuleNumber
    }
}