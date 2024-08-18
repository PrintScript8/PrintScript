package operations

import node.staticpkg.*

interface StaticVisitor {

    fun acceptNone(node: NoneType)
    fun acceptAssignation(node: AssignationType)
    fun acceptModifier(node: ModifierType)
    fun acceptDeclaration(node: DeclarationType)
    fun acceptIdentifier(node: IdentifierType)
    fun acceptPrintLn(node: PrintLnType)
    fun acceptExpression(node: ExpressionType)
}