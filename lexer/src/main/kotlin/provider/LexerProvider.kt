package provider

import lexer.Lexer
import lexer.LexerImpl
import rule.TokenRule
import rule.basic.EndingRule
import rule.basic.IdentifierRule
import rule.basic.ModifierRule
import rule.basic.TypeIdRule
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

class LexerProvider {

    private fun commonRules(): List<TokenRule> = listOf(
        WhiteSpaceRule(),
        ModifierRule(),
        PrintlnRule(),
        ReadInputRule(),
        ReadEnvRule(),
        TypeIdRule(),
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
        IfRule(),
        ElseRule(),
        BraceRule(listOf(OpenBraceRule, CloseBraceRule)),
        BooleanLiteralRule()
    ) + commonRules()

    fun getLexer(version: String): Lexer {
        val rules = when (version) {
            "1.0" -> rulesV1
            "1.1" -> rulesV2
            else -> throw IllegalArgumentException("Invalid version")
        }
        return LexerImpl(rules)
    }
}
