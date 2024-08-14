package type

import node.AST
import node.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DivisionTypeTest {

    @Test
    fun testSumTypeCreation() {
        val divisionType = DivisionType()
        assertNotNull(divisionType)
    }

    @Test
    fun testNumberResult() {
        val divisionType = DivisionType()
        val children = listOf<Node>(AST(NumberType(5), listOf()), AST(NumberType(5), listOf())) // Assuming Node is a valid class
        assert(divisionType.apply(children) is NumberType)
        assert((divisionType.apply(children) as NumberType).print(listOf()) == "1")
    }

    @Test
    fun testSumTypePrint() {
        val divisionType = DivisionType()
        val children = listOf<Node>(AST(StringType("5"), listOf()), AST(StringType("5"), listOf()),
            AST(StringType("5"), listOf())) // Assuming Node is a valid class
        val expectedOutput = "5 / 5 / 5"
        assertEquals(expectedOutput, divisionType.print(children))
    }
}