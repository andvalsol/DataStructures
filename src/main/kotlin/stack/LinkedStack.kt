package stack


// Implementation of a stack inside a LinkedList
class LinkedStack<T> {
    private var size = 0

    private var head: Node<T>? = null

    private var tail: Node<T>? = null

    private inner class Node<T> constructor(
        internal var previous: Node<T>?, internal var element: T, internal var next: Node<T>?
    )

    fun push(element: T) {
        val oldTail = tail!!

        val node = Node(oldTail, element = element, null)

        if (isEmpty()) {
            head = node
        } else {
            oldTail.next = node
        }

        tail = node

        size++
    }

    fun pop(): T {
        // Because of the nature of the stack, we pop the tail
        if (isEmpty()) throw Exception("StackUnderflowException")


        if (size == 1) {
            val oldTail = tail

            tail = null
            head = null

            return oldTail!!.element
        } else {
            val newTail = tail!!.previous

            tail = newTail

            newTail!!.next = null // Reset the value of the next element
        }

        size--

        return tail!!.element
    }

    fun isEmpty() = (size == 0)

    fun peek(): T {
        // Need to check if there are any items inside the LinkedStack
        if (isEmpty()) throw Exception("No items has been added")

        return tail!!.element // Tail won't be null by now
    }
}
