package node.staticpkg

import node.dynamic.DynamicNode
import operations.StaticVisitor

class AssignationType(val declaration: DeclarationType, val value: DynamicNode) : StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptAssignation(this)
    }

    override fun toString(): String {
        return "AssignationType(declaration='$declaration', value=$value)"
    }
}
