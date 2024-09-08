package linter

import error.Error
import node.staticpkg.StaticNode
import rules.argument.ArgumentRule
import rules.argument.LiteralRule
import rules.format.CamelCaseRule
import rules.format.FormatRule
import visitor.LinterVisitor

class LinterImpl : Linter {

    private val linterVisitor: LinterVisitor = LinterVisitor(LiteralRule(), CamelCaseRule(), this)
    private val log: MutableList<Error> = mutableListOf()

    override fun lint(iterator: Iterator<StaticNode>): List<Error> {
        while (iterator.hasNext()) {
            val node = iterator.next()
            node.visit(linterVisitor)
        }
        return log
    }

    override fun setFormatRules(newFormatRules: FormatRule) {
        linterVisitor.formatRules = newFormatRules
    }

    override fun setArgumentRules(newPrintRules: ArgumentRule) {
        linterVisitor.argumentRules = newPrintRules
    }

    fun logError(errors: List<Error>) {
        log.addAll(errors)
    }
}
