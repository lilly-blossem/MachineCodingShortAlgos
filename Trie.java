public class Trie {
    static class Node{
        Node[] child;
        boolean isEndOfWord;

        public Node() {
            child = new Node[26];
            isEndOfWord = false;
        }
    }
    Node root;

    public Trie() {
        this.root = new Node();
    }

    public void insert(String word){
        Node curr = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (curr.child[idx] == null) {
                curr.child[idx] = new Node();
            }
            curr = curr.child[idx];
        }
        curr.isEndOfWord = true;
    }

    public boolean searchWord(String word){
        Node curr = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (curr.child[idx] == null) {
                return false;
            }
            curr = curr.child[idx];
        }
        return curr.isEndOfWord;
    }

    /** Returns true if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node curr = root;
        for (char ch : prefix.toCharArray()) {
            int idx = ch - 'a';
            if (curr.child[idx] == null) {
                return false;
            }
            curr = curr.child[idx];
        }
        return true;
    }
}
