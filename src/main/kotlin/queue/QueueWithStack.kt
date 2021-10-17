package queue

class QueueWithStack {
    var size = 0

    private val popStack = ArrayDeque<Int>()
    private val pushStack = ArrayDeque<Int>()

    fun add(element: Int) {
        pushStack.addLast(element)
        size++
    }

    fun pop(): Int {
        if (popStack.isNotEmpty()) {
            size--

            return popStack.removeLast()
        } else {
            return if (pushStack.isNotEmpty()) {
                while(pushStack.isNotEmpty()) {
                    val item = pushStack.removeLast()

                    popStack.add(item)
                }

                size--

                popStack.removeLast()
            } else {
                -1
            }
        }
    }
}
