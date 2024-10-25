package formatter

import json.FormattingRules
import json.parseJsonRules
import node.Node
import node.dynamic.LiteralType
import node.staticpkg.*
import strategy.*
import java.io.StringWriter

class Formatter(private val rules: String) {
    private val strategies: Map<Class<out Node>, FormatStrategy<out Node>> = mapOf(
        DeclarationType::class.java to DeclarationStrategy(),
        AssignationType::class.java to AssignationStrategy(),
    )

    private val parsedRules: FormattingRules = parseJsonRules(rules)

    fun format(node: Node): String {
        val writer = StringWriter()
        val strategy = strategies[node::class.java] ?: throw IllegalArgumentException("No strategy for node type ${node::class.java}")
        (strategy as FormatStrategy<Node>).apply(node, parsedRules, writer)
        return writer.toString()
    }
}
