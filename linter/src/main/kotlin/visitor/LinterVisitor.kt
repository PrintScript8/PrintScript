package visitor

import error.Error
import linter.LinterImpl
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import operations.StaticVisitor
import rules.argument.ArgumentRule
import rules.format.FormatRule

class LinterVisitor(
    var argumentRules: ArgumentRule,
    var formatRules: FormatRule,
    private val linter: LinterImpl
) : StaticVisitor {

    override fun acceptAssignation(node: AssignationType) {
        node.declaration.visit(this)
    }

    override fun acceptModifier(node: ModifierType) {
        return
    }

    override fun acceptDeclaration(node: DeclarationType) {
        val errors: List<Error> = formatRules.analyzeFormat(node)
        linter.logError(errors)
    }

    override fun acceptIdentifier(node: IdentifierType) {
        return
    }

    override fun acceptPrintLn(node: PrintLnType) {
        val errors: List<Error> = argumentRules.analyzeArguments(node)
        linter.logError(errors)
    }

    override fun acceptExpression(node: ExpressionType) {
        return
    }
}
