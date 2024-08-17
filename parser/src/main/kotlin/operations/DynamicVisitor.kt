package operations

import node.dynamic.*
import type.LiteralType

interface DynamicVisitor {

    fun acceptSum(node: SumType)
    fun acceptSubtract(node: SubtractType)
    fun acceptMultiply(node: MultiplyType)
    fun acceptDivision(node: DivisionType)
    fun acceptLiteral(node: LiteralType)
    fun acceptVariable(node: VariableType)
}