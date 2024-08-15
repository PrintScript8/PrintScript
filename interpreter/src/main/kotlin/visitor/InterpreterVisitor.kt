package visitor

import interpreter.InterpreterImpl
import node.staticpkg.*
import node.dynamic.DivisionType
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import operations.DynamicVisitor
import operations.StaticVisitor
import type.LiteralType
import type.LiteralValue

class InterpreterVisitor(private val interpreter: InterpreterImpl) : StaticVisitor, DynamicVisitor {
    override fun acceptSum(node: SumType) {
        for (child in node.children) {
            child.visit(this)
        }
    }

    override fun acceptSubtract(node: SubtractType) {
        for (child in node.children) {
            child.visit(this)
            if (child.result is LiteralValue.StringValue) {
                error("Cannot subtract strings")
            }
        }
        var result: Number = (node.children[0].result as LiteralValue.NumberValue).number
        for (i in 1 until node.children.size) {
            result = when (result) {
                is Int -> result - (node.children[i].result as LiteralValue.NumberValue).number.toInt()
                is Double -> result - (node.children[i].result as LiteralValue.NumberValue).number.toDouble()
                else -> throw Exception("Unsupported number type")
            }
        }
        node.result = LiteralValue.NumberValue(result)
    }

    override fun acceptMultiply(node: MultiplyType) {
        for (child in node.children) {
            child.visit(this)
            if (child.result is LiteralValue.StringValue) {
                error("Cannot multiply strings")
            }
        }
        var result: Number = (node.children[0].result as LiteralValue.NumberValue).number
        for (i in 1 until node.children.size) {
            result = when (result) {
                is Int -> result * (node.children[i].result as LiteralValue.NumberValue).number.toInt()
                is Double -> result * (node.children[i].result as LiteralValue.NumberValue).number.toDouble()
                else -> throw Exception("Unsupported number type")
            }
        }
        node.result = LiteralValue.NumberValue(result)
    }

    override fun acceptDivision(node: DivisionType) {
        for (child in node.children) {
            child.visit(this)
            if (child.result is LiteralValue.StringValue) {
                error("Cannot divide strings")
            }
        }
        var result: Number = (node.children[0].result as LiteralValue.NumberValue).number
        for (i in 1 until node.children.size) {
            result = when (result) {
                is Int -> result / (node.children[i].result as LiteralValue.NumberValue).number.toInt()
                is Double -> result / (node.children[i].result as LiteralValue.NumberValue).number.toDouble()
                else -> throw Exception("Unsupported number type")
            }
        }
        node.result = LiteralValue.NumberValue(result)
    }

    override fun acceptNone(node: NoneType) {
        error("Cannot accept none")
    }

    override fun acceptAssignation(node: AssignationType) {
        if (node.declaration.modifier.canModify) {
            node.value.visit(this)
            interpreter.addValue(node.declaration.value, Pair(node.declaration.modifier.canModify, node.value.result))
        } else {
            throw Exception("Cannot modify a constant")
        }
    }

    override fun acceptLiteral(node: LiteralType) {
        return
    }

    override fun acceptDeclaration(node: DeclarationType) {
        if (interpreter.checkValue(node.value)) {
            interpreter.addValue(node.value, Pair(node.modifier.canModify, null))
        } else {
            throw Exception("Missing modifier!")
        }
    }

    override fun acceptIdentifier(node: IdentifierType) {
        error("Cannot accept identifier")
    }

    override fun acceptModifier(node: ModifierType) {
        error("Cannot accept modifier")
    }
}