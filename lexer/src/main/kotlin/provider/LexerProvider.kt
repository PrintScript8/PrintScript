package provider

import lexer.Lexer
import lexer.LexerInterface
import reader.ReaderInterface
import rule.TokenRule
import rule.basic.ConstRule
import rule.basic.EndingRule
import rule.basic.IdentifierRule
import rule.basic.LetRule
import rule.basic.WhiteSpaceRule
import rule.control.BraceRule
import rule.control.CloseBraceRule
import rule.control.CloseParenthesisRule
import rule.control.ElseRule
import rule.control.IfRule
import rule.control.OpenBraceRule
import rule.control.OpenParenthesisRule
import rule.control.ParenthesisRule
import rule.expression.AssignationRule
import rule.expression.DeclarationRule
import rule.inherent.PrintlnRule
import rule.inherent.ReadEnvRule
import rule.inherent.ReadInputRule
import rule.literal.BooleanLiteralRule
import rule.literal.NumberLiteralRule
import rule.literal.StringLiteralRule
import rule.operation.DivideOperation
import rule.operation.MinusOperation
import rule.operation.MultiplyOperation
import rule.operation.OperationRule
import rule.operation.PlusOperation
import rule.typeid.BooleanIdRule
import rule.typeid.NumberIdRule
import rule.typeid.StringIdRule

class LexerProvider(private val reader: ReaderInterface) {

    private fun commonRules(): List<TokenRule> = listOf(
        WhiteSpaceRule(),
        LetRule(),
        PrintlnRule(),
        StringIdRule(),
        NumberIdRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignationRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation)),
        ParenthesisRule(listOf(OpenParenthesisRule, CloseParenthesisRule))
    )

    private val rulesV1: List<TokenRule> = commonRules()

    private val rulesV2: List<TokenRule> = listOf(
        BooleanIdRule(),
        IfRule(),
        ElseRule(),
        ConstRule(),
        ReadInputRule(),
        ReadEnvRule(),
        BraceRule(listOf(OpenBraceRule, CloseBraceRule)),
        BooleanLiteralRule()
    ) + commonRules()

    fun getLexer(version: String): LexerInterface {
        val rules = when (version) {
            "1.0" -> rulesV1
            "1.1" -> rulesV2
            else -> throw IllegalArgumentException("Invalid version")
        }
        return Lexer(rules, reader)
    }
}
