package adts;

public class QueuePriorityInteger implements QueuePriorityIntegerI {

	class PriorityNode{
		Object key;
		int priority;
		PriorityNode next;
	}
	
	PriorityNode maxPriority = null;
	
	@Override
	public void enqueue(Object elem, int prio) {
		PriorityNode newNode = new PriorityNode();
		newNode.key = elem;
		newNode.priority = prio;
		
		if(maxPriority == null || prio > maxPriority.priority){
			newNode.next = maxPriority;
			maxPriority = newNode;
		}else{
			PriorityNode aux = new PriorityNode();
			aux = maxPriority;
			
			while(aux.next != null && aux.next.priority >= prio){
				aux = aux.next;
			}
		
			newNode.next = aux.next;
			aux.next = newNode;
		}
	}

	@Override
	public void unqueue() {
		maxPriority = maxPriority.next;

	}

	@Override
	public boolean isEmpty() {
		return(maxPriority == null);
	}

	@Override
	public Object first() {
		return maxPriority.key;
	}

	@Override
	public Object priority() {
		return maxPriority.priority;
	}

	@Override
	public void init() {
		maxPriority = null;
	}

}
