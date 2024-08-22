package formatter

import node.staticpkg.StaticNode
import visitor.StaticFormatterVisitor

class FormatterImpl : Formatter {

    private val visitor = StaticFormatterVisitor(this)
    private val output: MutableList<String> = mutableListOf()

    override fun execute(list: List<StaticNode>): String {
        for (node in list) {
            node.visit(visitor)
            output.add(visitor.getOutput() + ";")
        }
        return getOutput()
    }

    private fun getOutput(): String {
        return output.joinToString("\n")
    }
}
