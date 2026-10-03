class Solution {
    public boolean isCyclic(int V, int[][] e) {
        // code here
        List<List<Integer>> g = convert(e, V);
        // assumining graph is not disconnected
        Set<Integer> vis = new HashSet<>();
        Set<Integer> rec = new HashSet<>();
        
        for(int i=0;i<V;i++){
            if(topo(g,i,vis, rec)){
                return true;
            }
        }
        return false;
        
    }
    public boolean topo(List<List<Integer>> g, int node, 
                    Set<Integer>vis, Set<Integer>recur){
                        
        if(recur.contains(node)){
            return true;
        } 
        if(vis.contains(node)) return false;
        vis.add(node);
        recur.add(node);
        
        for(var v : g.get(node)){
            if(topo(g,v,vis,recur)){
                return true;
            }
        }
        recur.remove(node);
        return false;
    }
    public List<List<Integer>> convert(int[][] arr, int n){
            List<List<Integer>> g = new ArrayList<>(n);
            for(int i=0;i<n;i++){
                g.add(new ArrayList<>());
            }
            for(int[] c : arr){
                int u = c[0];
                int v = c[1];
                g.get(u).add(v);
            }
            return g;
        }
}
