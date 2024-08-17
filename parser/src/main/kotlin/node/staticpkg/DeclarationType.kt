package node.staticpkg

import operations.StaticVisitor

class DeclarationType(val modifier: ModifierType, val type: IdentifierType, val name: String) : StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptDeclaration(this)
    }
}