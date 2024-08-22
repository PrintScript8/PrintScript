package parser.visitor

import node.dynamic.*
import node.staticpkg.*
import operations.DynamicVisitor
import operations.StaticVisitor
import type.LiteralType

class typeVisitor : DynamicVisitor, StaticVisitor{

    override fun acceptSum(node: SumType) {
        TODO("Not yet implemented")
    }

    override fun acceptSubtract(node: SubtractType) {
        TODO("Not yet implemented")
    }

    override fun acceptMultiply(node: MultiplyType) {
        TODO("Not yet implemented")
    }

    override fun acceptDivision(node: DivisionType) {
        TODO("Not yet implemented")
    }

    override fun acceptLiteral(node: LiteralType) {
        TODO("Not yet implemented")
    }

    override fun acceptVariable(node: VariableType) {
        TODO("Not yet implemented")
    }

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