package type

import node.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NumberTypeTest {

    @Test
    fun testNumberTypeCreation() {
        val numberType = NumberType(5)
        assertNotNull(numberType)
    }

    @Test
    fun testNumberTypeResultType() {
        val numberType = NumberType(5)
        val children = listOf<Node>() // Assuming Node is a valid class
        assert(numberType.apply(children) is NumberType)
    }

    @Test
    fun testNumberTypePrint() {
        val numberType = NumberType(5)
        val children = listOf<Node>() // Assuming Node is a valid class
        val expectedOutput = "5" // Replace with actual expected output
        assertEquals(expectedOutput, numberType.print(children))
    }
}