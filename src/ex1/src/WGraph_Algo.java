package ex1.src;
import java.io.*;
import java.util.*;

    /** This class implements the weighted_graph_algorithms interface which allows preforming complex algorithms on a
     * weighted, undirected graph, with the following class variables:
     * weighted_graph _wg - the graph to preform the algorithms on.
     *  **/

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    private weighted_graph _wg;

    /** Constructor **/
    public WGraph_Algo(){
        _wg = new WGraph_DS();
    }

    /** Initialize the graph to work on the provided weights graph parameter
     * @param g - the weighted graph to initialize.
     * @return
     * **/
    @Override
    public void init(weighted_graph g) {
        _wg = g;

    }

    /** Returns the weighted graph in this weighted graph algorithm's as _wg.
     * @prarm
     * @return _wg
     * **/
    @Override
    public weighted_graph getGraph() {
        return _wg;
    }

    /** Returns a deep copy of this graph by sending him to a copy constructor of the WGraph_DS class
     * @param
     * @return a deep copy of _wg
     * **/
    @Override
    public weighted_graph copy() {
        weighted_graph wg_copy = new WGraph_DS(_wg);
        return wg_copy;
    }

    /** This function checks if the current weighted_graph_algo is a connected graph, meaning if their is a path between each 2 nodes in
     * the graph.
     * The base assumption behind this function is this: if a graph is a connected graph, from a specific node (each node), there
     * should be a path to each other node.
     * So, the functions takes a random node, and adds to a HashMap all the nodes which are connected to him in some path,
     * by preforming the BFS algorithm.
     * Then, after adding all the connected nodes to the HashMap, if the number of nodes in the HashMap is equal
     * to the number of the entire nodes in the graph, hence all the nodes are connected to the random node we chose,
     * and therefore there is a path between each 2 nodes in the graph, and the graph is connected.
     * @param
     * @return True - if the graph is a connected graph, False - if it's not.
     * **/
    @Override
    public boolean isConnected() {
        Collection<node_info> nodes = _wg.getV();
        if(nodes.isEmpty()){
            return true;
        }
        Queue<node_info> q = new LinkedList<node_info>();
        node_info node = nodes.iterator().next();
        q.add(node);
        HashMap<Integer, Boolean> connected = new HashMap<Integer, Boolean>();
        connected.put(node.getKey(), true);
        while(!q.isEmpty()&& connected.size()!=nodes.size()){
            node_info curr = q.poll();
            Collection<node_info> neighbors = _wg.getV(curr.getKey());
            for(node_info next : neighbors){
                if(connected.get(next.getKey())==null){
                    q.add(next);
                    connected.put(next.getKey(),true);
                }
            }
        }
        if(connected.size()==nodes.size()){
            return true;
        }
        return false;
    }

    /** **/
    @Override
    public double shortestPathDist(int src, int dest) {
        List<node_info> path = shortestPath(src,dest);
        if(!path.isEmpty()){
            return _wg.getNode(dest).getTag();
        }
        return -1;
    }

    /** This function returns the shortest path between 2 nodes in a undirected, weighted graph by preforming the Dijkstra() algorithm
     * function and the reconstructPath() function.
     * Dijkstra:
     * Initialization:
     *      - a priority queue pq, which is prioritized by the tag of each node which will be used to store the shortest distance
     *      of this current node from the src node.
     *      - a Double var, dist to Infinity, which will store the current shortest distance from src to dest.
     *      - Visited Hashset, which will store all the already visited nodes
     *      - prev Hashmap the map the parent of each node which is the closest (by path weight) to the src node.
     * The steps:
     * - Set src tag (distance from src) to 0, and add him to pq.
     * - Start going over the pq until empty, extract the head (marked as curr) of the pq and go over is neighbors (if his not already visited).
     * - For each neighbor, define the current distance from src (in the path that goes through curr), and check if the current distance
     * is shortest then the current shortest distance from src to path, if not - there is no point to continue with this neighbor.
     * - If so, check if the current neighbor is the dest node, if so, replace distance var with the ni_dist_from_src var.
     * - Check if the current neighbor as a parent node marked in the prev Hashmap,  if not - set curr as his parent and change the
     * neighbor's tag to ni_dist_from_src, if is does have a parent node, change the parent node and the tag of the neighbor only if
     * ni_dist_from_src is smaller than the current tag of this neighbor.
     * - Add the neighbor to the pq.
     * Go thorough this process for each edge in the graph until the pq is empty and return the prev hashmap.
     *
     * reconstructPath:
     * This function takes the prev HashMap from the solve function and the src and dest nodes,
     * and is trying to construct a path between dest to src (the reversed way) by adding to a list the prev of dest,
     * and then the prev of the prev of dest, and so on, until it reached the src node, if it does - its the shortest path
     * between src and dest, if it can't reach the src node - there is no path between src and dest.
     * The function ends by reversing the path (constructed as an ArrayList) to make if from src to dest and not
     * dest to src.
     * **/
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        node_info src_node = _wg.getNode(src);
        node_info dest_node = _wg.getNode(dest);
        HashMap<Integer, node_info> prev = Dijkstra(src_node, dest_node);
        List<node_info> path = reconstructPath(prev, src_node, dest_node);
        return path;
    }

    public HashMap<Integer, node_info> Dijkstra (node_info src, node_info dest){
        PriorityQueue<node_info> pq = new PriorityQueue<node_info>(Comparator.comparingDouble(node_info::getTag));
        double dist = Double.POSITIVE_INFINITY;
        HashSet<Integer> visited = new HashSet<Integer>();
        HashMap<Integer, node_info> prev = new HashMap<Integer, node_info>();
        src.setTag(0);
        pq.add(src);
        while (!pq.isEmpty()){
            node_info curr = pq.poll();
            int curr_key = curr.getKey();
            if(!visited.contains(curr_key) && curr!=dest){
                visited.add(curr_key);
                Collection<node_info> curr_ni = _wg.getV(curr_key);
                for(node_info ni : curr_ni) {
                    int ni_key = ni.getKey();
                    if (!visited.contains(ni_key)) {
                        double ni_dist_from_src = curr.getTag() + _wg.getEdge(curr_key, ni_key);
                        if (ni_dist_from_src < dist) {
                            if (ni == dest) {
                                if (ni_dist_from_src < dist) {
                                    dist = ni_dist_from_src;
                                }
                            }
                            if(prev.get(ni_key)==null){
                                ni.setTag(ni_dist_from_src);
                                prev.put(ni_key, curr);
                            }
                            else if(ni_dist_from_src<ni.getTag()) {
                                ni.setTag(ni_dist_from_src);
                                prev.put(ni_key, curr);
                            }
                            pq.add(ni);
                        }
                    }
                }
            }
        }
        return prev;
    }
    private List<node_info> reconstructPath (HashMap<Integer, node_info> prev, node_info src, node_info dest){
        List<node_info> path_temp = new ArrayList<node_info>();
        List<node_info> path = new ArrayList<node_info>();
        path_temp.add(dest);
        for(int i = dest.getKey(); prev.get(i)!=null; i=prev.get(i).getKey()){
            path_temp.add(prev.get(i));
        }
        if(!path_temp.isEmpty() && path_temp.get(path_temp.size()-1).getKey()==src.getKey()) {
            for (int i = path_temp.size() - 1; i >= 0; i--) {
                path.add(path_temp.get(i));
            }
        }
        return path;


    }

    /** This function saves _wg to a file as a string of bytes using the Serializable interface in a way that will allow us to load it again.
     * @param file - the name of the file to save _wg to.
     * @return True if successfully save, False otherwise.
     * **/
    @Override
    public boolean save(String file) {
        boolean flag = true;
        try{
            FileOutputStream WGraph_file = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(WGraph_file);
            oos.writeObject(_wg);
            oos.close();
            WGraph_file.close();
        }
        catch(Exception e){
            flag=false;
            e.printStackTrace();
        }
        return flag;


    }

    /**This function load a weighted graph from a file and is intializing him to be the weighted graph of this
     * weighted graph algorithm.
     * @param file - the file to load from the weighted graph
     * @return True if successfully load and intiliaze, False otherwise.
     * **/
    @Override
    public boolean load(String file) {
        boolean flag = true;
        try{
            FileInputStream WGraph_file = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(WGraph_file);
            Object temp = ois.readObject();
            if(temp instanceof weighted_graph){
                _wg= (weighted_graph) temp;

            }
            ois.close();
            WGraph_file.close();
        }
        catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}
