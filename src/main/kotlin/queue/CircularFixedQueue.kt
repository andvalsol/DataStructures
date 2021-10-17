package queue

class CircularFixedQueue<T> {
    private var capacity = -1
    private var front = -1
    private var rear = -1
    private val elements: Array<Any?>

    constructor(capacity: Int) {
        this.capacity = capacity
        this.elements = arrayOfNulls(capacity)
    }

    fun isFull() = ((rear + 1) % capacity == front)

    fun isEmpty() = (rear == -1 && front == -1)

    // Add at the end of the array
    fun enqueue(element: T) {
        if (isFull()) throw Exception("QueueOverflowException")
        else if (isEmpty()) {
            elements[0] = element
            rear = 0
            front = 0
        } else {
            rear = (rear + 1) % capacity
            elements[rear] = element
        }
    }

    /*fun front(): T {
        // ...
    }

    fun rear(): T {
        // ...
    } */

    fun dequeue(): T {
        if (isEmpty()) throw Exception("QueueUnderflowException")

        val element = elements[front] as T

        elements[front] = null

        if (front == rear) {
            front = -1
            rear = -1
        } else {
            front = (front + 1) % capacity
        }

        return element
    }
}
