package visitor

import interpreter.InterpreterImpl
import node.dynamic.*
import node.staticpkg.*
import operations.DynamicVisitor
import operations.StaticVisitor
import type.LiteralType

class StaticInterpreterVisitor(private val interpreter: InterpreterImpl) : StaticVisitor {

    private val dynamicVisitor: DynamicVisitor  = DynamicInterpreterVisitor(interpreter)

    override fun acceptAssignation(node: AssignationType) {
        if (!interpreter.checkValue(node.declaration.name)) {
            node.value.visit(dynamicVisitor)
            interpreter.addValue(node.declaration.name, Pair(node.declaration.modifier.canModify, node.value.result))
        } else {
            throw Exception("Cannot modify a constant")
        }
    }

    override fun acceptDeclaration(node: DeclarationType) {
        if (interpreter.checkValue(node.name) && node.modifier.canModify) {
            interpreter.addValue(node.name, Pair(node.modifier.canModify, null))
        } else {
            throw Exception("Missing modifier!")
        }
    }

    override fun acceptIdentifier(node: IdentifierType) {
        error("Cannot accept identifier")
    }

    override fun acceptPrintLn(node: PrintLnType) {
        node.argument.visit(dynamicVisitor)
        println(node.argument.result)
    }

    override fun acceptExpression(node: ExpressionType) {
        node.variable.visit(dynamicVisitor)
        node.value.visit(dynamicVisitor)
        val sameType = interpreter.getValue(node.variable.name).second!!.javaClass == node.value.result!!.javaClass
        val modifiable = interpreter.checkValue(node.variable.name) || node.variable.canModify
        if (modifiable && sameType) {
            interpreter.addValue(node.variable.name, Pair(node.variable.canModify, node.value.result))
        } else {
            throw Exception("Cannot modify a constant")
        }
    }

    override fun acceptModifier(node: ModifierType) {
        error("Cannot accept modifier")
    }
}