import org.example.lexer.LexerImplementation
import org.example.token.Token
import org.example.token.TokenType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class LexerTests {


    @Test
    fun testToken1(){
        val lexer = LexerImplementation()
        //lexer.setFile(File("C:\\Users\\hilul\\projects\\ingSis\\PrintScript\\lexer\\src\\test\\testfiles\\sample1.txt"))
        lexer.setFile(File("C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\lexer\\src\\test\\testfiles\\sample1.txt"))

        val tokens:List<Token> = lexer.getTokens();

        val typesList: List<TokenType> = listOf(
            TokenType.KEYWORD, TokenType.IDENTIFIER_VAR, TokenType.ASSIGNATION, TokenType.IDENTIFIER_TYPE, TokenType.ASSIGNATION,
            TokenType.STRING_LITERAL, TokenType.ENDING, TokenType.KEYWORD, TokenType.IDENTIFIER_VAR, TokenType.ASSIGNATION,
            TokenType.IDENTIFIER_TYPE, TokenType.ASSIGNATION, TokenType.STRING_LITERAL, TokenType.ENDING, TokenType.NATIVE_METHOD,
            TokenType.IDENTIFIER_VAR, TokenType.OPERAND, TokenType.STRING_LITERAL, TokenType.OPERAND, TokenType.IDENTIFIER_VAR,
            TokenType.PARENTHESIS, TokenType.ENDING);

        var i = 0;
        Assertions.assertFalse(tokens.isEmpty())
        for (tk:Token in tokens){
            Assertions.assertEquals(typesList[i], tk.getType())
            i += 1;
        }
    }

    @Test
    fun testToken2(){
        val lexer = LexerImplementation()
        //lexer.setFile(File("C:\\Users\\hilul\\projects\\ingSis\\PrintScript\\lexer\\src\\test\\testfiles\\sample2.txt"))
        lexer.setFile(File("C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\lexer\\src\\test\\testfiles\\sample2.txt"))

        val tokens:List<Token> = lexer.getTokens();

        val typesList: List<TokenType> = listOf(
            TokenType.KEYWORD, TokenType.IDENTIFIER_VAR, TokenType.ASSIGNATION, TokenType.IDENTIFIER_TYPE, TokenType.ASSIGNATION, TokenType.NUMBER_LITERAL,
            TokenType.ENDING, TokenType.KEYWORD, TokenType.IDENTIFIER_VAR, TokenType.ASSIGNATION, TokenType.IDENTIFIER_TYPE, TokenType.ASSIGNATION,
            TokenType.NUMBER_LITERAL, TokenType.ENDING, TokenType.KEYWORD, TokenType.IDENTIFIER_VAR, TokenType.ASSIGNATION, TokenType.IDENTIFIER_TYPE,
            TokenType.ASSIGNATION, TokenType.IDENTIFIER_VAR, TokenType.OPERAND, TokenType.IDENTIFIER_VAR, TokenType.ENDING, TokenType.NATIVE_METHOD
            , TokenType.STRING_LITERAL, TokenType.OPERAND, TokenType.IDENTIFIER_VAR, TokenType.PARENTHESIS, TokenType.ENDING)


            var i = 0;
        for (tk:Token in tokens){
            Assertions.assertEquals(typesList[i], tk.getType())
            i +=1
        }
    }

    @Test
    fun testToken3(){
        val lexer = LexerImplementation()
        //lexer.setFile(File("C:\\Users\\hilul\\projects\\ingSis\\PrintScript\\lexer\\src\\test\\testfiles\\sample3.txt"))
        lexer.setFile(File("C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\lexer\\src\\test\\testfiles\\sample3.txt"))

        val tokens:List<Token> = lexer.getTokens();

        val typesList: List<TokenType> = listOf(
            TokenType.KEYWORD, // let
            TokenType.IDENTIFIER_VAR, // a
            TokenType.ASSIGNATION, // :
            TokenType.IDENTIFIER_TYPE, // number
            TokenType.ASSIGNATION, // =
            TokenType.NUMBER_LITERAL, // 12
            TokenType.ENDING, // ;

            TokenType.KEYWORD, // let
            TokenType.IDENTIFIER_VAR, // b
            TokenType.ASSIGNATION, // :
            TokenType.IDENTIFIER_TYPE, // number
            TokenType.ASSIGNATION, // =
            TokenType.NUMBER_LITERAL, // 4
            TokenType.ENDING, // ;

            TokenType.IDENTIFIER_VAR, // a
            TokenType.ASSIGNATION, // =
            TokenType.IDENTIFIER_VAR, // a
            TokenType.OPERAND, // /
            TokenType.IDENTIFIER_VAR, // b
            TokenType.ENDING, // ;

            TokenType.NATIVE_METHOD, // println(
            TokenType.STRING_LITERAL, // "Result: "
            TokenType.OPERAND, // +
            TokenType.IDENTIFIER_VAR, // a
            TokenType.PARENTHESIS, // )
            TokenType.ENDING // ;
        )

        var i = 0;
        for (tk:Token in tokens){
            Assertions.assertEquals(typesList[i], tk.getType())
            i +=1
        }
    }
}