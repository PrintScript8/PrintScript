package parsertest

import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.ReadEnvType
import node.dynamic.ReadInputType
import node.dynamic.SumType
import node.staticpkg.AssignationType
import node.staticpkg.ExpressionType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.ParserInterface
import parser.elements.ParserProvider
import token.Assignment
import token.CloseParenthesis
import token.Declaration
import token.Ending
import token.Identifier
import token.Modifier
import token.NativeMethod
import token.OpenParenthesis
import token.Plus
import token.Position
import token.StringLiteral
import token.Token
import token.TypeId
import java.lang.IllegalArgumentException

class MethodsTests {

    @Test
    fun printLnTest() {
        val tokenList: List<Token> = listOf(
            Token(NativeMethod, "println", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "Hello World!", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: PrintLnType = parseList[0] as PrintLnType
        Assertions.assertTrue(headNode.argument is LiteralType)
        val stringNode: LiteralType = headNode.argument as LiteralType
        Assertions.assertEquals("Hello World!", (stringNode.result as LiteralValue.StringValue).string)
    }

    @Test
    fun missingClosingParenthesisInNativeMethod() {
        val tokenList: List<Token> = listOf(
            Token(NativeMethod, "println", Position(1, 1, 1)),
            Token(StringLiteral, "Hello World!", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun expressionBeforePrintTest() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(NativeMethod, "println", Position(1, 1, 1)),
            Token(StringLiteral, "Hello World!", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun missingArgumentsInMethod() {
        val tokenList: List<Token> = listOf(
            Token(NativeMethod, "println", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun missingParenthesisAndArgumentInMethod() {
        val tokenList: List<Token> = listOf(
            Token(NativeMethod, "println", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun onlyClosedParenthesis() {
        val tokenList: List<Token> = listOf(
            Token(NativeMethod, "println", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1))
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun missingOpenParenthesis() {
        val tokenList: List<Token> = listOf(
            Token(NativeMethod, "println", Position(1, 1, 1)),
            Token(StringLiteral, "Hello World!", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.0")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun readEnvAssignationTest() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "string", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readEnv", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "myName", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: AssignationType = parseList[0] as AssignationType
        val readEnvNode: ReadEnvType = headNode.value as ReadEnvType
        Assertions.assertTrue(readEnvNode.argument is LiteralType)
    }

    @Test
    fun readInputAssignationTest() {
        val tokenList: List<Token> = listOf(
            Token(Modifier, "let", Position(1, 1, 1)),
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(TypeId, "string", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readInput", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "myName", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: AssignationType = parseList[0] as AssignationType
        val readInputType: ReadInputType = headNode.value as ReadInputType
        Assertions.assertTrue(readInputType.argument is LiteralType)
    }

    @Test
    fun readEnvExpressionTest() {
        val tokenList: List<Token> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readEnv", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "myName", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: ExpressionType = parseList[0] as ExpressionType
        val readEnvNode: ReadEnvType = headNode.value as ReadEnvType
        Assertions.assertTrue(readEnvNode.argument is LiteralType)
    }

    @Test
    fun readInputExpressionTest() {
        val tokenList: List<Token> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readInput", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "myName", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: ExpressionType = parseList[0] as ExpressionType
        val readInputType: ReadInputType = headNode.value as ReadInputType
        Assertions.assertTrue(readInputType.argument is LiteralType)
    }

    @Test
    fun complexReadEnvTest() {
        val tokenList: List<Token> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readEnv", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "my", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(StringLiteral, "name", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: ExpressionType = parseList[0] as ExpressionType
        val readEnvNode: ReadEnvType = headNode.value as ReadEnvType
        Assertions.assertTrue(readEnvNode.argument is SumType)
    }

    @Test
    fun complexReadInputTest() {
        val tokenList: List<Token> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readInput", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "my", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(StringLiteral, "name", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        val parseList: List<StaticNode> = parserInterface.iterator().asSequence().toList()
        Assertions.assertEquals(1, parseList.size)
        val headNode: ExpressionType = parseList[0] as ExpressionType
        val readInputType: ReadInputType = headNode.value as ReadInputType
        Assertions.assertTrue(readInputType.argument is SumType)
    }

    @Test
    fun readEnvWithoutAssignTest() {
        val tokenList: List<Token> = listOf(
            Token(NativeMethod, "readEnv", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "my", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(StringLiteral, "name", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun readInputWithoutParenthesisTest() {
        val tokenList: List<Token> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readInput", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "my", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(StringLiteral, "name", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }

    @Test
    fun readInputWithoutEndingTest() {
        val tokenList: List<Token> = listOf(
            Token(Identifier, "name", Position(1, 1, 1)),
            Token(Assignment, "=", Position(1, 1, 1)),
            Token(NativeMethod, "readInput", Position(1, 1, 1)),
            Token(OpenParenthesis, "(", Position(1, 1, 1)),
            Token(StringLiteral, "my", Position(1, 1, 1)),
            Token(Plus, "+", Position(1, 1, 1)),
            Token(StringLiteral, "name", Position(1, 1, 1)),
            Token(CloseParenthesis, ")", Position(1, 1, 1)),
        )
        val parserProvider = ParserProvider(tokenList.iterator())
        val parserInterface: ParserInterface = parserProvider.getParser("1.1")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            parserInterface.parse()
        }
    }
}
