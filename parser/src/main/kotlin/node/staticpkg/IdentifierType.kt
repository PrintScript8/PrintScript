package node.staticpkg

import operations.StaticVisitor

class IdentifierType : StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptIdentifier(this)
    }
}
