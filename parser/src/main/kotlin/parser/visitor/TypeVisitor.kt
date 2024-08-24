package parser.visitor

import node.PrimType
import node.dynamic.*
import node.staticpkg.*
import operations.DynamicVisitor
import operations.StaticVisitor
import type.LiteralType
import type.LiteralValue

class TypeVisitor : DynamicVisitor, StaticVisitor{

    override fun acceptSum(node: SumType) {
        val leftResult = node.left.result
        val rightResult = node.right.result

        if ((leftResult !is LiteralValue.NumberValue && leftResult !is LiteralValue.StringValue) ||
            (rightResult !is LiteralValue.NumberValue && rightResult !is LiteralValue.StringValue)) {
            throw IllegalArgumentException("Both operands of a sum must be numbers or strings")
        }
    }

    override fun acceptSubtract(node: SubtractType) {
        val leftResult = node.left.result
        val rightResult = node.right.result

        if (leftResult !is LiteralValue.NumberValue || rightResult !is LiteralValue.NumberValue) {
            throw IllegalArgumentException("Both operands of a subtraction must be numbers")
        }
    }

    override fun acceptMultiply(node: MultiplyType) {
        val leftResult = node.left.result
        val rightResult = node.right.result

        if (leftResult !is LiteralValue.NumberValue || rightResult !is LiteralValue.NumberValue) {
            throw IllegalArgumentException("Both operands of a multiplication must be numbers")
        }
    }

    override fun acceptDivision(node: DivisionType) {
        val leftResult = node.left.result
        val rightResult = node.right.result

        if (leftResult !is LiteralValue.NumberValue || rightResult !is LiteralValue.NumberValue) {
            throw IllegalArgumentException("Both operands of a division must be numbers")
        }
    }

    override fun acceptLiteral(node: LiteralType) {
        // El tipo literal ya está correcto en su creación
    }

    override fun acceptVariable(node: VariableType) {
        // No es posible parsear variables segun su tipo en esta instancia ya que no se tiene memoria de su tipo
    }



    override fun acceptAssignation(node: AssignationType) {
        val declarationType = node.declaration.type.type
        val valueType = node.value.result

        // Verificar que el tipo de la declaración sea compatible con el valor asignado
        if ((declarationType == PrimType.STRING && valueType is LiteralValue.StringValue) ||
            (declarationType == PrimType.NUMBER && valueType is LiteralValue.NumberValue)) {
            // Los tipos estan bien, no hay que hacer nada
        }
        else{
            throw IllegalArgumentException("Types to assign don't match")
        }
    }

    override fun acceptModifier(node: ModifierType) {
        // No tiene un tipo por lo tanto no hace nada
    }

    override fun acceptDeclaration(node: DeclarationType) {
        TODO("Not yet implemented")
    }

    override fun acceptIdentifier(node: IdentifierType) {
        TODO("Not yet implemented")
    }

    override fun acceptPrintLn(node: PrintLnType) {
        // No tiene un tipo por lo tanto no hace nada
    }

    override fun acceptExpression(node: ExpressionType) {
        // Verificar que el tipo de la variable sea compatible con el valor asignado
        if (node.variable.result?.javaClass != node.value.result?.javaClass) {
            throw IllegalArgumentException("Type mismatch in expression")
        }
    }
}