package type

import node.AST
import node.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SumTypeTest {

    @Test
    fun testSumTypeCreation() {
        val sumType = SumType()
        assertNotNull(sumType)
    }

    @Test
    fun testNumberResult() {
        val sumType = SumType()
        val children = listOf<Node>(AST(NumberType(5), listOf()), AST(NumberType(5), listOf())) // Assuming Node is a valid class
        assert(sumType.apply(children) is NumberType)
        assert((sumType.apply(children) as NumberType).print(listOf()) == "10")
    }

    @Test
    fun testNumberStringResult() {
        val sumType = SumType()
        val children = listOf<Node>(AST(NumberType(5), listOf()), AST(StringType("5"), listOf())) // Assuming Node is a valid class
        assert(sumType.apply(children) is StringType)
        assert((sumType.apply(children) as StringType).print(listOf()) == "55")
    }

    @Test
    fun testStringResult() {
        val sumType = SumType()
        val children = listOf<Node>(AST(StringType("Hello "), listOf()), AST(StringType("World!"), listOf())) // Assuming Node is a valid class
        assert(sumType.apply(children) is StringType)
        assert((sumType.apply(children) as StringType).print(listOf()) == "Hello World!")
    }

    @Test
    fun testSumTypePrint() {
        val sumType = SumType()
        val children = listOf<Node>(AST(StringType("5"), listOf()), AST(StringType("5"), listOf())) // Assuming Node is a valid class
        val expectedOutput = "5 + 5"
        assertEquals(expectedOutput, sumType.print(children))
    }
}