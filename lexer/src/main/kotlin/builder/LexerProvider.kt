package builder

import lexer.Lexer
import rule.AssignationRule
import rule.BoolRule
import rule.BraceRule
import rule.CloseBraceRule
import rule.CloseParenthesisRule
import rule.DeclarationRule
import rule.DivideOperation
import rule.ElseRule
import rule.EndingRule
import rule.IdentifierRule
import rule.IfRule
import rule.MinusOperation
import rule.ModifierRule
import rule.MultiplyOperation
import rule.NativeMethodRule
import rule.NumberLiteralRule
import rule.OpenBraceRule
import rule.OpenParenthesisRule
import rule.OperationRule
import rule.ParenthesisRule
import rule.PlusOperation
import rule.StringLiteralRule
import rule.TokenRule
import rule.TypeIdRule
import rule.WhiteSpaceRule

class LexerProvider {

    private val rulesV1: List<TokenRule> = listOf(
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

    private val rulesV2: List<TokenRule> = listOf(
        WhiteSpaceRule(),
        IfRule(),
        ElseRule(),
        BraceRule(listOf(OpenBraceRule, CloseBraceRule)),
        BoolRule()
    ) + rulesV1

    fun getLexerV1(): Lexer {
        return Lexer(rulesV1)
    }

    fun getLexerV2(): Lexer {
        return Lexer(rulesV2)
    }
}
