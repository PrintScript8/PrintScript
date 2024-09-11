
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.elements.ParserInterface
import parser.elements.ParserProvider
import token.Assignment
import token.Boolean
import token.CloseParenthesis
import token.Declaration
import token.Ending
import token.Identifier
import token.Minus
import token.Modifier
import token.NumberLiteral
import token.OpenParenthesis
import token.Plus
import token.Position
import token.StringLiteral
import token.Token
import token.TypeId
import java.lang.IllegalArgumentException

class ParserInterfaceTests {

    @Test
    fun testDeclaration() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is DeclarationType)
        val node: DeclarationType = headNode as DeclarationType
        Assertions.assertEquals("let", node.modifier.value)
        Assertions.assertEquals(true, node.modifier.canModify)
        Assertions.assertEquals("name", node.name)
        Assertions.assertEquals(PrimType.STRING, node.type.type)
    }

    @Test
    fun testConstDeclarationError() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "const", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun testConstDeclaration() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "const", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is DeclarationType)
        val node: DeclarationType = headNode as DeclarationType
        Assertions.assertEquals("const", node.modifier.value)
        Assertions.assertEquals(false, node.modifier.canModify)
        Assertions.assertEquals("name", node.name)
        Assertions.assertEquals(PrimType.STRING, node.type.type)
    }

    @Test
    fun testAssignation() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NumberLiteral, "1", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(NumberLiteral, "2", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let", leftNode.modifier.value)
        Assertions.assertEquals(true, leftNode.modifier.canModify)
        Assertions.assertEquals("name", leftNode.name)
        Assertions.assertEquals(PrimType.STRING, leftNode.type.type)

        Assertions.assertTrue(assignNode.value is SumType)
        val sumNode: SumType = headNode.value as SumType
        Assertions.assertEquals(1.0, (sumNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(2.0, (sumNode.right.result as LiteralValue.NumberValue).number)
    }

    @Test
    fun testAssignationWithParenthesis() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(NumberLiteral, "1", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(NumberLiteral, "2", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let", leftNode.modifier.value)
        Assertions.assertEquals(true, leftNode.modifier.canModify)
        Assertions.assertEquals("name", leftNode.name)
        Assertions.assertEquals(PrimType.STRING, leftNode.type.type)

        Assertions.assertTrue(assignNode.value is SumType)
        val sumNode: SumType = headNode.value as SumType
        Assertions.assertEquals(1.0, (sumNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(2.0, (sumNode.right.result as LiteralValue.NumberValue).number)
    }

    @Test
    fun testComplexAssignation() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NumberLiteral, "1", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(NumberLiteral, "2", Position(1, 1, 1)),
            Token(Minus, "-", Position(1, 1, 1)),
            Token(NumberLiteral, "3", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let", leftNode.modifier.value)
        Assertions.assertEquals(true, leftNode.modifier.canModify)
        Assertions.assertEquals("name", leftNode.name)
        Assertions.assertEquals(PrimType.STRING, leftNode.type.type)

        Assertions.assertTrue(assignNode.value is SumType)
        val sumNode: SumType = headNode.value as SumType
        Assertions.assertTrue(sumNode.right is SubtractType)
        val subNode: SubtractType = sumNode.right as SubtractType
        Assertions.assertEquals(1.0, (sumNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(2.0, (subNode.left.result as LiteralValue.NumberValue).number)
        Assertions.assertEquals(3.0, (subNode.right.result as LiteralValue.NumberValue).number)
    }

    @Test
    fun multipleSentence() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(StringLiteral, "Tomi", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(2, parseList.size)
        val declarationNode: StaticNode = parseList[0]
        Assertions.assertTrue(declarationNode is DeclarationType)
        val node: DeclarationType = declarationNode as DeclarationType
        Assertions.assertEquals("let", node.modifier.value)
        Assertions.assertEquals(true, node.modifier.canModify)
        Assertions.assertEquals("name", node.name)
        Assertions.assertEquals(PrimType.STRING, node.type.type)
        val expressionNode: StaticNode = parseList[1]
        Assertions.assertTrue(expressionNode is ExpressionType)
        val node2: ExpressionType = expressionNode as ExpressionType
        Assertions.assertEquals("name", node2.variable.name)
        Assertions.assertTrue(node2.value is LiteralType)
        val stringNode: LiteralType = node2.value as LiteralType
        Assertions.assertEquals("Tomi", (stringNode.result as LiteralValue.StringValue).string)
    }

    @Test
    fun testBooleanDeclaration() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "boolean", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface2: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface2.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is DeclarationType)
        val node: DeclarationType = headNode as DeclarationType
        Assertions.assertEquals("let", node.modifier.value)
        Assertions.assertEquals(true, node.modifier.canModify)
        Assertions.assertEquals("name", node.name)
        Assertions.assertEquals(PrimType.BOOLEAN, node.type.type)
    }

    @Test
    fun testBooleanAssignation() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "boolean", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Boolean, "true", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface2: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface2.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let", leftNode.modifier.value)
        Assertions.assertEquals(true, leftNode.modifier.canModify)
        Assertions.assertEquals("name", leftNode.name)
        Assertions.assertEquals(PrimType.BOOLEAN, leftNode.type.type)

        Assertions.assertTrue(assignNode.value is LiteralType)
    }

    @Test
    fun missingEndingSemicolon() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun missingTypeInDeclaration() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun invalidAssignationWithoutRightHandSide() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun invalidTokenSequence() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun missingExpressionTest() {
        val tokenList: List<Token> = listOf(
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun testMissingArgumentsInAssignation() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun missingPreviousArgsInAssignation() {
        val tokenList: List<Token> = listOf(
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(Assignment, "1", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun missingPreviousArgsInDeclaration() {
        val tokenList: List<Token> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(NumberLiteral, "1", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun incorrectDeclarationTest() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(TypeId, "String", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }
}
