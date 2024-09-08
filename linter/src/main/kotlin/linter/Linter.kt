package linter

import error.Error
import node.staticpkg.StaticNode
import rules.argument.ArgumentRule
import rules.format.FormatRule

interface Linter {
    fun lint(iterator: Iterator<StaticNode>): List<Error>
    fun setFormatRules(newFormatRules: FormatRule)
    fun setArgumentRules(newPrintRules: ArgumentRule)
}
