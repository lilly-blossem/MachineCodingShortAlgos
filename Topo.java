import java.util.*;

class Solution {
    public List<Integer> topoSort(int V, int[][] edges) {

        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            g.add(new ArrayList<>());
        }

        int[] indegree = new int[V];

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            g.get(u).add(v);
            indegree[v]++;
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }
        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()) {
            int node = q.poll();
            topo.add(node);
            for (int nei : g.get(node)) {
                indegree[nei]--;
                if (indegree[nei] == 0) {
                    q.offer(nei);
                }
            }
        }
        return topo;
    }

    public boolean isCyclic(int V, int[][] e) {
        // code here
        List<List<Integer>> g = convert(e, V);
        // assumining graph is not disconnected
        Set<Integer> vis = new HashSet<>();
        Set<Integer> rec = new HashSet<>();

        for (int i = 0; i < V; i++) {
            if (topo(g, i, vis, rec)) {
                return true;
            }
        }
        return false;

    }

    public boolean topo(List<List<Integer>> g, int node,
                        Set<Integer> vis, Set<Integer> recur) {

        if (recur.contains(node)) {
            return true;
        }
        if (vis.contains(node)) return false;
        vis.add(node);
        recur.add(node);

        for (var v : g.get(node)) {
            if (topo(g, v, vis, recur)) {
                return true;
            }
        }
        recur.remove(node);
        return false;
    }

    public List<List<Integer>> convert(int[][] arr, int n) {
        List<List<Integer>> g = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }
        for (int[] c : arr) {
            int u = c[0];
            int v = c[1];
            g.get(u).add(v);
        }
        return g;
    }
}

