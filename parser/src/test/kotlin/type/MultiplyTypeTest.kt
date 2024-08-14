package type

import node.AST
import node.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MultiplyTypeTest {

    @Test
    fun testSumTypeCreation() {
        val multiplyType = MultiplyType()
        assertNotNull(multiplyType)
    }

    @Test
    fun testNumberResult() {
        val multiplyType = MultiplyType()
        val children = listOf<Node>(AST(NumberType(5), listOf()), AST(NumberType(5), listOf())) // Assuming Node is a valid class
        assert(multiplyType.apply(children) is NumberType)
        assert((multiplyType.apply(children) as NumberType).print(listOf()) == "25")
    }

    @Test
    fun testSumTypePrint() {
        val multiplyType = MultiplyType()
        val children = listOf<Node>(AST(StringType("5"), listOf()), AST(StringType("5"), listOf()),
            AST(StringType("5"), listOf())) // Assuming Node is a valid class
        val expectedOutput = "5 * 5 * 5"
        assertEquals(expectedOutput, multiplyType.print(children))
    }
}