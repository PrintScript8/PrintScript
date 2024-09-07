package operations

import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType

interface StaticVisitorV1 {
    fun acceptAssignation(node: AssignationType)
    fun acceptModifier(node: ModifierType)
    fun acceptDeclaration(node: DeclarationType)
    fun acceptIdentifier(node: IdentifierType)
    fun acceptPrintLn(node: PrintLnType)
    fun acceptExpression(node: ExpressionType)
}
