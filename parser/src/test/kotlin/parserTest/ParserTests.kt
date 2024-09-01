import node.PrimType
import node.dynamic.BooleanType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.elements.Parser
import parser.elements.ParserProvider
import token.Assignment
import token.BooleanLiteral
import token.CloseParenthesis
import token.Declaration
import token.Ending
import token.Identifier
import token.Minus
import token.Modifier
import token.NativeMethod
import token.NumberLiteral
import token.OpenParenthesis
import token.Plus
import token.Position
import token.StringLiteral
import token.Token
import token.TokenImpl
import token.TypeId
import java.lang.IllegalArgumentException

class ParserTests {

    private val parserProvider: ParserProvider = ParserProvider()
    private val parser: Parser = parserProvider.getParser("1.0")
    private val parser2: Parser = parserProvider.getParser("1.1")

    @Test
    fun testDeclaration() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
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
    fun testAssignation() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "1", Position(1, 1, 1)),
            TokenImpl(Plus, "+", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "2", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
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
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(OpenParenthesis, "(", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "1", Position(1, 1, 1)),
            TokenImpl(Plus, "+", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "2", Position(1, 1, 1)),
            TokenImpl(CloseParenthesis, ")", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
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
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "1", Position(1, 1, 1)),
            TokenImpl(Plus, "+", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "2", Position(1, 1, 1)),
            TokenImpl(Minus, "-", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "3", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
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
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(StringLiteral, "Tomi", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
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
    fun printLnTest() {
        val tokenList: List<Token> = listOf(
            TokenImpl(NativeMethod, "printLn", Position(1, 1, 1)),
            TokenImpl(OpenParenthesis, "(", Position(1, 1, 1)),
            TokenImpl(StringLiteral, "Hello World!", Position(1, 1, 1)),
            TokenImpl(CloseParenthesis, ")", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser.parse(tokenList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: PrintLnType = parseList[0] as PrintLnType
        Assertions.assertTrue(headNode.argument is LiteralType)
        val stringNode: LiteralType = headNode.argument as LiteralType
        Assertions.assertEquals("Hello World!", (stringNode.result as LiteralValue.StringValue).string)
    }

    @Test
    fun testBooleanDeclaration() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "boolean", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        val parseList: List<StaticNode> = parser2.parse(tokenList)
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
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "boolean", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(BooleanLiteral, "true", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1)),
        )
        val parseList: List<StaticNode> = parser2.parse(tokenList)
        Assertions.assertEquals(1, parseList.size)
        val headNode: StaticNode = parseList[0]
        Assertions.assertTrue(headNode is AssignationType)
        val assignNode: AssignationType = headNode as AssignationType
        val leftNode: DeclarationType = headNode.declaration
        Assertions.assertEquals("let", leftNode.modifier.value)
        Assertions.assertEquals(true, leftNode.modifier.canModify)
        Assertions.assertEquals("name", leftNode.name)
        Assertions.assertEquals(PrimType.BOOLEAN, leftNode.type.type)

        Assertions.assertTrue(assignNode.value is BooleanType)
    }

    @Test
    fun missingEndingSemicolon() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingTypeInDeclaration() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun invalidAssignationWithoutRightHandSide() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun invalidTokenSequence() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingClosingParenthesisInNativeMethod() {
        val tokenList: List<Token> = listOf(
            TokenImpl(NativeMethod, "printLn", Position(1, 1, 1)),
            TokenImpl(StringLiteral, "Hello World!", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingExpressionTest() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Ending, ";", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun expressionBeforePrintTest() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(NativeMethod, "printLn", Position(1, 1, 1)),
            TokenImpl(StringLiteral, "Hello World!", Position(1, 1, 1)),
            TokenImpl(CloseParenthesis, ")", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingArgumentsInMethod() {
        val tokenList: List<Token> = listOf(
            TokenImpl(NativeMethod, "printLn", Position(1, 1, 1)),
            TokenImpl(CloseParenthesis, ")", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingParenthesisAndArgumentInMethod() {
        val tokenList: List<Token> = listOf(
            TokenImpl(NativeMethod, "printLn", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun onlyClosedParenthesis() {
        val tokenList: List<Token> = listOf(
            TokenImpl(NativeMethod, "printLn", Position(1, 1, 1)),
            TokenImpl(CloseParenthesis, ")", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingOpenParenthesis() {
        val tokenList: List<Token> = listOf(
            TokenImpl(NativeMethod, "printLn", Position(1, 1, 1)),
            TokenImpl(StringLiteral, "Hello World!", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun testMissingArgumentsInAssignation() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(Declaration, ":", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingPreviousArgsInAssignation() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Assignment, "=", Position(1, 1, 1)),
            TokenImpl(Assignment, "1", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun missingPreviousArgsInDeclaration() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(NumberLiteral, "1", Position(1, 1, 1)),
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }

    @Test
    fun incorrectDeclarationTest() {
        val tokenList: List<Token> = listOf(
            TokenImpl(Modifier, "let", Position(1, 1, 1)),
            TokenImpl(Identifier, "name", Position(1, 1, 1)),
            TokenImpl(TypeId, "String", Position(1, 1, 1)),
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parser.parse(tokenList)
        }
    }
}
