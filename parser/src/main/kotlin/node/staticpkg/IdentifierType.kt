package node.staticpkg

import node.PrimType
import operations.StaticVisitor

// Usa un PrimType que dice el tipo de variable
class IdentifierType(val type:PrimType): StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptIdentifier(this)
    }

    override fun toString(): String {
        return "IdentifierType($type)"
    }
}