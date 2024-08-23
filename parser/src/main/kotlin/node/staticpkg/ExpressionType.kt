package node.staticpkg

import node.dynamic.DynamicNode
import node.dynamic.VariableType
import operations.StaticVisitor

// Sirve para nombre = "valor"
class ExpressionType(val variable: VariableType, val value: DynamicNode) : StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptExpression(this)
    }
}
