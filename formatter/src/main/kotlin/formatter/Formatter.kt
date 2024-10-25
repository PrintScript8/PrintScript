package formatter

import json.FormattingRules
import json.parseJsonRules
import node.Node
import node.staticpkg.*
import strategy.*
import strategy.staticStrategy.AssignationStrategy
import strategy.staticStrategy.DeclarationStrategy
import java.io.StringWriter

class Formatter(private val rules: String) {
    private val strategies: Map<Class<out Node>, FormatStrategy<out Node>> = mapOf(
        DeclarationType::class.java to DeclarationStrategy(),
        AssignationType::class.java to AssignationStrategy(),
    )

    private val parsedRules: FormattingRules = parseJsonRules(rules)

    fun format(nodes: Iterator<StaticNode>): String {
        val writer = StringWriter()
        nodes.forEach { node ->
            val strategy = strategies[node.javaClass] as? FormatStrategy<StaticNode>
            strategy?.apply(node, parsedRules, writer)
        }
        return writer.toString()
    }
}