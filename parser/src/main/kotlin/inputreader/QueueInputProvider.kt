package inputreader

import java.util.Queue

class QueueInputProvider(private val messages: Queue<String>) : InputProvider {
    override fun input(name: String?): String? {
        return messages.poll()
    }
}
