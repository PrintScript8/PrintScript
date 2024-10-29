package json

data class FormattingRules(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundEquals: Boolean,
    var newlineBeforePrintln: Int,
    var indentation: Int = 0,
    val version: String
)
