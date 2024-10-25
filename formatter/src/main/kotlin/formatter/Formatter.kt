package formatter

import json.FormattingRules
import json.parseJsonRules
import node.Node
import node.staticpkg.*
import strategy.*
import strategy.staticStrategy.*
import java.io.StringWriter

class Formatter(rules: String) {

    private val strategies: Map<Class<out Node>, FormatStrategy<out Node>> = mapOf(
        DeclarationType::class.java to DeclarationStrategy(),
        AssignationType::class.java to AssignationStrategy(),
        PrintLnType::class.java to PrintLnStrategy(),
        ExpressionType::class.java to ExpressionStrategy(),
        IfElseType::class.java to IfElseStrategy(),
    )

    private val parsedRules: FormattingRules = parseJsonRules(rules)

    fun format(nodes: Iterator<StaticNode>): String {
        val writer = StringWriter()

        processFirstNode(nodes, writer)

        nodes.forEach { node ->
            val strategy = strategies[node.javaClass] as? FormatStrategy<StaticNode>
            strategy?.apply(node, parsedRules, writer)
            writeNewLine(nodes, writer)
        }
        return writer.toString()
    }

    private fun processFirstNode(nodes: Iterator<StaticNode>, writer: StringWriter) {
        if (nodes.hasNext()) {
            val firstNode = nodes.next()
            if (firstNode is PrintLnType) {
                printLnAsFirstNode(firstNode, writer)
            } else {
                val strategy = strategies[firstNode.javaClass] as? FormatStrategy<StaticNode>
                strategy?.apply(firstNode, parsedRules, writer)
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
        val originalRuleNumber = parsedRules.newlineBeforePrintln
        parsedRules.newlineBeforePrintln = 0
        val strategy = strategies[node.javaClass] as? FormatStrategy<StaticNode>
        strategy?.apply(node, parsedRules, writer)
        parsedRules.newlineBeforePrintln = originalRuleNumber
    }

    fun increaseIndentation() {
        parsedRules.indentation++
    }

    fun decreaseIndentation() {
        parsedRules.indentation--
    }
}