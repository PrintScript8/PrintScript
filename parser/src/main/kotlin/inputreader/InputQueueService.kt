package inputreader

import java.util.Queue

object InputQueueService {
    private lateinit var inputQueue: Queue<String>

    fun initialize(queue: Queue<String>) {
        inputQueue = queue
    }

    fun getInput(): String? {
        return inputQueue.poll()
    }
}
