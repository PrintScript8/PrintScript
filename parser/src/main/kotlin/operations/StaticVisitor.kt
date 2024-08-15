package operations

import node.staticpkg.*

interface StaticVisitor {

    fun acceptNone(node: NoneType)
    fun acceptAssignation(node: AssignationType)
    fun acceptModifier(node: ModifierType)
    fun acceptDeclaration(node: DeclarationType)
    fun acceptIdentifier(node: IdentifierType)
}