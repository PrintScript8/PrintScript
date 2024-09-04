package lexer

import rule.AssignationRule
import rule.BooleanRule
import rule.CloseParenthesisRule
import rule.DeclarationRule
import rule.DivideOperation
import rule.EndingRule
import rule.IdentifierRule
import rule.MinusOperation
import rule.ModifierRule
import rule.MultiplyOperation
import rule.NativeMethodRule
import rule.NumberLiteralRule
import rule.OpenParenthesisRule
import rule.OperationRule
import rule.ParenthesisRule
import rule.PlusOperation
import rule.StringLiteralRule
import rule.TypeIdRule
import rule.WhiteSpaceRule

class LexerProvider {

    private val ogRules = listOf(
        ModifierRule(),
        NativeMethodRule(),
        TypeIdRule(),
        WhiteSpaceRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignationRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation)),
        ParenthesisRule(listOf(OpenParenthesisRule, CloseParenthesisRule))
    )

    private val rulesV2 = listOf(
        ModifierRule(),
        NativeMethodRule(),
        TypeIdRule(),
        WhiteSpaceRule(),
        BooleanRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignationRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation)),
        ParenthesisRule(listOf(OpenParenthesisRule, CloseParenthesisRule))
    )

    fun getLexer(version: String): Lexer {
        val rules = when (version) {
            "1.0" -> ogRules
            "1.1" -> rulesV2
            else -> throw IllegalArgumentException("Invalid version")
        }
        return LexerImpl(rules)
    }
}
