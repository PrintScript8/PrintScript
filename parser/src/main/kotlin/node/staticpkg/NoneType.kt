package node.staticpkg

import operations.StaticVisitor

class NoneType: StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptNone(this)
    }
    override fun toString(): String {
        return "NoneType()"
    }
}