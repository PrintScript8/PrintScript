package node.staticpkg

import node.dynamic.DynamicNode
import operations.StaticVisitor

class PrintLnType(val argument: DynamicNode) : StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptPrintLn(this)
    }

    override fun toString(): String {
        return "PrintLnType(argument='$argument')"
    }
}