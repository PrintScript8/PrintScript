package linter

import node.staticpkg.StaticNode
import rules.argument.ArgumentRule
import rules.argument.LiteralRule
import rules.format.CamelCaseRule
import rules.format.FormatRule
import visitor.LinterVisitor
import error.Error

class LinterImpl : Linter {

    private val linterVisitor: LinterVisitor = LinterVisitor(LiteralRule(), CamelCaseRule(), this)
    private val log: MutableList<Error> = mutableListOf()

    override fun lint(list: List<StaticNode>): List<Error> {
        for (node in list) {
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