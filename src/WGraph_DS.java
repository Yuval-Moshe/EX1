
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

    /**
     * @author Yuval Moshe
     *
     * This class implements the weighted_graph interface which is a weighted undirected graph with the following class variables:
     * HashMap<Integer, node_info> _vertices  - Reperesents the vertices in the graph as a hashmap which maps each node to his unique key.
     * HashMap<Integer, HashMap<Integer, Double>> _weights  - represents the edges and their wieght in the graph, by mapping the key of each
     * node in the graph to an hashmap which maps all the neighbors (by their key) of this specific node, to the weight of the edge between
     * the node and his neighbor.
     * int _numOfEdges = number of edges in the grpah.
     * int _mc = number of actions preformed on the the graph.
     * **/

public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> _vertices = new HashMap<Integer, node_info>();
    private HashMap<Integer, HashMap<Integer, Double>> _weights = new HashMap<Integer, HashMap<Integer, Double>>();
    private int _numOfEdges;
    private int _mc;

    /** Constructor **/
    public WGraph_DS(){
        _vertices = new HashMap<Integer, node_info>();
        _weights = new HashMap<Integer, HashMap<Integer, Double>>();
        _numOfEdges = 0;
        _mc = 0;

    }
    /** Copy Constructor **/
    public WGraph_DS (weighted_graph other){
        Collection<node_info> other_v = other.getV();
        for(node_info node : other_v){
            int node_key = node.getKey();
            node_info curr = new NodeInfo(node_key);
            _vertices.put(node_key, curr);
            HashMap<Integer, Double> node_weights = new HashMap<Integer, Double>();
            Collection<node_info> node_ni = other.getV(node_key);
            for(node_info ni : node_ni){
                int ni_key = ni.getKey();
                node_weights.put(ni_key, other.getEdge(node_key, ni_key));
            }
            _weights.put(node_key, node_weights);
        }
        _numOfEdges = other.edgeSize();
        _mc = other.getMC();
    }

    /** Retrieves the specific node from the graph, by returning the mapped node_info the provided key.
     * @param key  - the key of the node to retrieve.
     * @return  node_info - the node to retrieve.
     * **/
    @Override
    public node_info getNode(int key) {
        return _vertices.get(key);
    }

    /** Checks if theres an edge between 2 nodes in the grpah, by checking if their each other's neighbors Hashmap.
     * @param node1 - node1's key.
     * @param node2 - node2'2 key.
     * @return  True if there is an edge between node1 and node2, False if there isn't.
     * **/
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(node1!=node2){
            if(_vertices.containsKey(node1) && _vertices.containsKey(node2)){
                return _weights.get(node1).containsKey(node2);
            }
        }
        return false;
    }

    /**Returns the weight of the edge between 2 nodes (if exists) by checking the value of the mapped neighbor key.
     * @param node1 - node1's key.
     * @param node2 - node2's key.
     * @return - weight of the edge between them, if there isn't any return -1;
     *  **/
    @Override
    public double getEdge(int node1, int node2) {
        if(this.hasEdge(node1, node2)){
            return _weights.get(node1).get(node2);
        }
        return -1;
    }

    /** Adds a node to the graph by mapping his key to a new node_info
     * @param key - the unique key of the node to add/
     * @return
     * **/
    @Override
    public void addNode(int key) {
        if(_vertices.get(key)==null) {
            _vertices.put(key, new NodeInfo(key));
            _weights.put(key, new HashMap<Integer, Double>());
            _mc++;
        }

    }

    /** Connects 2 nodes by an edge with weight w, by adding each ones to the other's neighbors hasmap and maping the neighbor's
     * key the the provided weight.
     * @param node1 - node1's key.
     * @param node2 - node2's key.
     * @param w - the weights of the edge to add between node1 and node 2.
     * @return
     * **/
    @Override
    public void connect(int node1, int node2, double w) {
        if(w>=0) {
            if (hasEdge(node1, node2)) {
                _weights.get(node1).put(node2, w);
                _weights.get(node2).put(node1, w);
            }
            else if (node1 != node2) {
                if (_vertices.containsKey(node1) && _vertices.containsKey(node2)) {
                    _weights.get(node1).put(node2, w);
                    _weights.get(node2).put(node1, w);
                    _numOfEdges++;
                }
            }
            _mc++;
        }
    }

    /** Returns all the vertices in the graph by return the values of the _vertices hashmap (O(1) time complexity)
     * @param
     * @return
     * **/

    @Override
    public Collection<node_info> getV() {
        return _vertices.values();
    }

    /** Returns all the vertices in the graph which are connected to the provided node key, be returning all the node's neighbors
     * from the _weights Hashmap
     * @param node_id - the node's key
     * @return an Arraylist of all the conncted vertices to this node, if the nodes not in the graph/ the node is not connected
     * to any other vertices, returns any empty Arraylist**/
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> neighbors = new ArrayList<node_info>();
        if(_vertices.containsKey(node_id)){
            Collection<Integer> neighbors_keys = _weights.get(node_id).keySet();
            for(Integer nodeKey : neighbors_keys){
                neighbors.add(_vertices.get(nodeKey));
            }
        }
        return neighbors;
    }

    /** Removes the specific node from the graph (if exsits) by first removing him
     * from each of his neighbors neighbor's Hashmap and them removing him from the _vertices Hashmap.
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        node_info temp = _vertices.get(key);
        if(_vertices.containsKey(key)){
            Collection<Integer> neighbors_keys = new ArrayList<Integer>();
            neighbors_keys.addAll(_weights.get(key).keySet());
            for(int nodeKey : neighbors_keys){
                removeEdge(nodeKey,key);
            }
            _vertices.remove(key);
            _mc++;
        }
        return temp;
    }

    /** Removes an edge between 2 nodes (if exists) by removing each other from their _weights map.
     * @param node1 - node1's key.
     * @param node2 - node2's key.
     * @return
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){
            _weights.get(node1).remove(node2);
            _weights.get(node2).remove(node1);
            _numOfEdges--;
            _mc++;
        }

    }

    /** returns the number of nodes in the graph by returning the num of mapped keys in _vertices Hashmap
     * @return number of nodes.
     */
    @Override
    public int nodeSize() {
        return _vertices.size();
    }

        /** returns _numOfEdges variable
         * @return number of edges.
         */
    @Override
    public int edgeSize() {
        return _numOfEdges;
    }

    /** returns the number of actions preformed on the graph by returning the _mc var.
     * @return number of actions
     */
    @Override
    public int getMC() {
        return _mc;
    }

    /** toString function
     * @return string representation of the graph**/
    public String toString (){
        String s="";
        for(node_info node : _vertices.values()){
            for(node_info ni : getV(node.getKey())){
               s+=node.getKey() + " and "+ ni.getKey() + " are connected by: "+getEdge(node.getKey(), ni.getKey())+"\n";
            }
        }
        return s;
    }

    /** Equals function for graph comparison, checks if each node & edge in this graph are also in the compared graph
     * and vice versa
     * @param WG_2_obj - the graph formed as an object to compare to
     * @return True if the graphs are equals, False if not
     * **/
    public boolean equals (Object WG_2_obj){
        boolean flag = false;
        if(WG_2_obj instanceof weighted_graph ) {
            weighted_graph WG_2 = (weighted_graph) WG_2_obj;
            if(edgeSize()!=WG_2.edgeSize() || nodeSize()!=WG_2.nodeSize()){
                return false;
            }
            flag = true;
            Collection<node_info> WG1_V = getV();
            Collection<node_info> WG2_V = WG_2.getV();
            for (node_info node1 : WG1_V) {
                flag &= (WG_2.getNode(node1.getKey()) != null);
            }
            for (node_info node : WG_2.getV()) {
                for (node_info ni : WG_2.getV(node.getKey())) {
                        flag &= hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        return flag;

    }

    /** This class implements the node_info interface which represents node in an unweighted undirected graph with the following class variables:
     * String _info - the information stored in the node.
     * double _tag - a temporarily tag assigned to this node
     * int _key - the unique key of the node
     * **/


    private class NodeInfo implements node_info, Serializable{
        private String _info;
        private double _tag;
        private int _key;


        /** Constructor
         * @param key - the unique key of this node **/
        public NodeInfo(int key){
            _key = key;
            _info ="";
            _tag=0.0;
        }
        /** Copy Constructor **/
        public NodeInfo (node_info other){
            _info = other.getInfo();
            _tag = other.getTag();

        }

        /** returns the key of this node.
         * @return _key.
         * **/
        @Override
        public int getKey() {
            return _key;
        }

        /** return the info stored in the node.
         * @return _info.
         * **/
        @Override
        public String getInfo() {
            return _info;
        }

        /** changes the info stored in the node to the provided String
         * @param s - the new info to set as _info
         * @return
         * **/
        @Override
        public void setInfo(String s) {
            _info = s;
        }

        /** returns the tag of this node.
         * @return _tag.
         * **/
        @Override
        public double getTag() {
            return _tag;
        }

        /** changes the tag of this node to the provided double
         * @param t - the new tag to set as _tag
         * @return
         * **/
        @Override
        public void setTag(double t) {
            _tag = t;

        }


//        public int compareTo(node_info other){
//            if(_tag == other.getTag()){
//                return 0;
//            }
//            else if (_tag > other.getKey()){
//                return 1;
//            }
//            else {
//                return -1;
//            }
//        }
    }
}
