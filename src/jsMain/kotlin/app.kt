import dev.fritz2.binding.Handler
import dev.fritz2.binding.RootStore
import dev.fritz2.binding.const
import dev.fritz2.binding.handledBy
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount
import dev.fritz2.dom.values
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.map

data class YodaData(
    val input: String = "",
    val yodaSpeak: String = "Curious, you are"
)

object YodaStore : RootStore<YodaData>(YodaData()) {
    val translate: Handler<Unit> = handle { yodaData ->
        yodaData.copy(yodaSpeak = askYoda(yodaData.input))
    }

    val updateInput: Handler<String> = handle { yodaData, newInput ->
        yodaData.copy(input = newInput)
    }
}

fun main() {
    render {
        div {
            div {
                label {
                    text("Please enter some text:")
                }
                input("text-input") {
                    placeholder = const("Please enter some text:")
                    type = const("text")

                    changes.values() handledBy YodaStore.updateInput
                }
            }
            button {
                text("Translate to Yoda!")
                clicks handledBy YodaStore.translate
            }
            p {
                YodaStore.data.map { it.yodaSpeak }.bind()
            }
        }
    }.mount("target")
}

suspend fun askYoda(input: String) =
    window.fetch("https://api.funtranslations.com/translate/yoda.json?text=$input")
        .await()
        .json()
        .await()
        .unsafeCast<YodaResponse>()
        .contents
        .translated

data class YodaResponse(
    val contents: YodaContents
)

data class YodaContents(
    val translated: String
)