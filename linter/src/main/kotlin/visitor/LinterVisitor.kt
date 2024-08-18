package visitor

import node.dynamic.*
import node.staticpkg.*
import operations.DynamicVisitor
import operations.StaticVisitor
import rules.argument.ArgumentRule
import rules.format.FormatRule
import type.LiteralType

class LinterVisitor(var argumentRules: ArgumentRule, var formatRules: FormatRule) :  StaticVisitor {

    override fun acceptNone(node: NoneType) {
        TODO("Not yet implemented")
    }

    override fun acceptAssignation(node: AssignationType) {
        TODO("Not yet implemented")
    }

    override fun acceptModifier(node: ModifierType) {
        TODO("Not yet implemented")
    }

    override fun acceptDeclaration(node: DeclarationType) {
        TODO("Not yet implemented")
    }

    override fun acceptIdentifier(node: IdentifierType) {
        TODO("Not yet implemented")
    }

    override fun acceptPrintLn(node: PrintLnType) {
        TODO("Not yet implemented")
    }

    override fun acceptExpression(node: ExpressionType) {
        TODO("Not yet implemented")
    }
}