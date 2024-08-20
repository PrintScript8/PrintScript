package rules.argument

import node.staticpkg.PrintLnType
import error.Error

//Pensando como el chess, las reglas se podrian llegar a anidar entre si (por eso se llama Rule, es unica)
interface ArgumentRule {
    fun analyzeArguments(printLnType: PrintLnType): List<Error>
}