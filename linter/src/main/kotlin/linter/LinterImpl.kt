package linter

import node.staticpkg.StaticNode
import rules.argument.ArgumentRule
import rules.argument.LiteralRule
import rules.format.CamelCaseRule
import rules.format.FormatRule
import visitor.LinterVisitor

class LinterImpl : Linter {

    private val linterVisitor: LinterVisitor = LinterVisitor(LiteralRule(), CamelCaseRule())

    override fun lint(list: List<StaticNode>) {
        for (node in list) {
            node.visit(linterVisitor)
        }
    }

    fun setFormatRules(newFormatRules: FormatRule) {
        linterVisitor.formatRules = newFormatRules
    }

    fun setArgumentRules(newPrintRules: ArgumentRule) {
        linterVisitor.argumentRules = newPrintRules

    }
}