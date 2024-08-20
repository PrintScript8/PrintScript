package formatter

import node.staticpkg.StaticNode
import visitor.FormatterVisitor

class FormatterImpl : Formatter {

    private val visitor = FormatterVisitor(this)
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
