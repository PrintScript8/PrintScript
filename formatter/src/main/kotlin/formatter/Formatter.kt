package formatter

import json.FormattingRules
import node.Node
import node.staticpkg.*
import strategy.*
import java.io.StringWriter

class Formatter(private val rules: FormattingRules, private val strategies: Map<Class<out Node>, FormatStrategy<out Node>>) {

    fun format(nodes: Iterator<StaticNode>): String {
        val writer = StringWriter()

        processFirstNode(nodes, writer)

        nodes.forEach { node ->
            val strategy = strategies[node.javaClass] as? FormatStrategy<StaticNode>
                ?: throw IllegalArgumentException("Unsupported node type: ${node.javaClass} for version ${rules.version}")
            strategy.apply(node, rules, writer)
            writeNewLine(nodes, writer)
        }
        rules.indentation = 0
        return writer.toString()
    }

    private fun processFirstNode(nodes: Iterator<StaticNode>, writer: StringWriter) {
        if (nodes.hasNext()) {
            val firstNode = nodes.next()
            if (firstNode is PrintLnType) {
                printLnAsFirstNode(firstNode, writer)
            } else {
                val strategy = strategies[firstNode.javaClass] as? FormatStrategy<StaticNode>
                if (strategy == null) {
                    throw IllegalArgumentException("Unsupported node type: ${firstNode.javaClass} for version ${rules.version}")
                }
                strategy?.apply(firstNode, rules, writer)
            }
        }
        writeNewLine(nodes, writer)
    }

    private fun writeNewLine(nodes: Iterator<StaticNode> , writer: StringWriter) {
        if (nodes.hasNext()) {
            writer.write("\n")
        }
    }

    private fun printLnAsFirstNode(node: PrintLnType, writer: StringWriter) {
        val originalRuleNumber = rules.newlineBeforePrintln
        rules.newlineBeforePrintln = 0
        val strategy = strategies[node.javaClass] as? FormatStrategy<StaticNode>
        strategy?.apply(node, rules, writer)
        rules.newlineBeforePrintln = originalRuleNumber
    }
}