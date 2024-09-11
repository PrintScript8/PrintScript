package linter

import error.Error
import node.dynamic.ReadInputType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.IfElseType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import rules.argument.ArgumentRule
import rules.format.FormatRule

class LinterV2(private val argumentRule: ArgumentRule?, private val formatRule: FormatRule?) : Linter {

    private val log: MutableList<Error> = mutableListOf()

    override fun lint(iterator: Iterator<StaticNode>): List<Error> {
        log.clear()
        if (formatRule == null && argumentRule == null) {
            return log
        }
        while (iterator.hasNext()) {
            val node = iterator.next()
            matchNode(node)
        }
        return log
    }

    private fun matchNode(node: StaticNode) {
        when (node) {
            is DeclarationType -> {
                if (formatRule != null) log.addAll(formatRule.analyzeFormat(node))
            }

            is PrintLnType, is IfElseType -> {
                if (argumentRule != null) log.addAll(argumentRule.analyzeArguments(node))
            }

            is AssignationType -> {
                matchNode(node.declaration)
                if (node.value is ReadInputType && argumentRule != null) {
                    log.addAll(argumentRule.analyzeArguments(node.value))
                }
            }

            is ExpressionType, is IdentifierType, is ModifierType -> {
                return
            }
        }
    }
}
