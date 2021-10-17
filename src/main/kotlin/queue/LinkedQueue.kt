package queue

class LinkedQueue<T> {
    private var size = 0
    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    private inner class Node<T> constructor(
        internal var previous: Node<T>?, internal var element: T, internal var next: Node<T>?
    )

    fun isEmpty() = (size == 0)

    // Function that adds an element at the end of the queue
    fun enqueue(element: T) {
        val oldTail = tail

        val newTail = Node(oldTail, element, null)

        tail = newTail

        if (isEmpty()) {
            head = newTail
        } else {
            oldTail!!.next = newTail
        }

        size++
    }

    // Remove at the first position
    fun dequeue(): T {
        if (isEmpty()) throw Exception("QueueUnderflowException")

        val oldHead = head!!

        head = oldHead.next

        if (oldHead.next == null) {
            tail = null
        } else {
            oldHead.next = null
        }

        size--

        return oldHead.element
    }

    fun front() {
        // ...
    }

    fun rear() {
        // ...
    }
}
