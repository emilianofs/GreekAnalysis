package adts;

public interface QueuePriorityIntegerI {
	void enqueue(Object elem, int prio);
	void unqueue();
	boolean isEmpty();
	Object first();
	Object priority();
	void init();
}
