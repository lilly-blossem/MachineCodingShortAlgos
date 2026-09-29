import java.util.HashMap;
import java.util.Map;

public class LruCache {
    static class Node{
        int val;
        int key;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            next = null;
            prev = null;
        }
    }
    Node head;
    Node tail;
    Map<Integer, Node> cache;
    int capacity;

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        head = new Node(-1,-1);
        tail = new Node(-1,-1);
        head.prev = tail;
        tail.next = head;
    }
    public void put(int key, int val){
        if(cache.containsKey(key)){
            Node node = cache.get(key);
            node.val  = val;
            moveToFront(node);
            return ;
        }
        if(cache.size()>=capacity){
            removeLastNode();
        }
        Node curr = new Node(key, val);
        insertAtFront(curr);
        cache.put(key, curr);
    }
    public int get(int key){
        if(cache.containsKey(key)){
            Node node = cache.get(key);
            moveToFront(node);
            return node.val;
        }
        return -1;
    }
    private void moveToFront(Node node){
        // detach node from its current position
        node.prev.next = node.next;
        node.next.prev = node.prev;
        // insert at front
        insertAtFront(node);
    }

    private void removeLastNode(){
        // tail.next is the least recently used node
        Node lru = tail.next;
        cache.remove(lru.key);
        tail.next = lru.next;
        lru.next.prev = tail;
    }

    private void insertAtFront(Node node){
        // insert between head.prev and head
        node.next = head;
        node.prev = head.prev;
        head.prev.next = node;
        head.prev = node;
    }
}
