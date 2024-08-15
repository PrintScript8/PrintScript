package operations

import node.dynamic.DivisionType
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import type.LiteralType

interface DynamicVisitor {

    fun acceptSum(node: SumType)
    fun acceptSubtract(node: SubtractType)
    fun acceptMultiply(node: MultiplyType)
    fun acceptDivision(node: DivisionType)
    fun acceptLiteral(node: LiteralType)
}