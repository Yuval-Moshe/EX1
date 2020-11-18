# EX1 

Implementation of a weighted, undirected graph with the following classes:

***NodeInfo*** - A private class in WGraph_DS that implements the **node_info** interface, which is the interface nodes of the weighted, undirected graph.
***WGraph_DS*** - Implementing the **weighted_graph** interface, which is the graph itselfs. 
***WGraph_Algo*** - Implementing the **weighted_graph_algorithms** interface, which allows performing algorithmic queries about a specific graph. 

---------------------------------------------------------
**Links**: 
- [Dijkstra Algorithm](https://www.coursera.org/lecture/advanced-data-structures/core-dijkstras-algorithm-2ctyF)

---------------------------------------------------------

**NodeInfo** 
- a private class which implements the nodes of the graph, who stores a simple key (which is provided), a String of info that the node stores, 
and a temporary tag. This class supports simple set & get functions in O(1) time complexity.


**WGraph_DS**
- Implementing the weighted & undirected graph, with the following data structures:
HashMap _vertices - to store the nodes of the graph.
HashNap _weights - to store the connection and edges between nodes and their weights.
The followig class supports O(1) methods such as:
    - getV() - returns all the vertices, in O(1) time complexity. 
    - getV(node_id) - return all the vertices connected to the a node provided by the node_id
    - hasEdge() - checks if 2 nodes are connected in the graph, in O(1) time complexity. 
    - getEdge() - return the weight of the edge between 2 nodes (if exists) 
    - addNode() - adds a new node to the graph, in O(1) time complexity. 
    - conncet() - connects two vertices in the graph by an edge with a provided weight, in O(1) time complexity. 
    - removeNode() - removes a node from the graph, in O(n) time complexity, when n is the number of vertices in the graph.
    - removeEdge() - removes an edge between 2 nodes in the graph in O(1) time complexity. 

**WGraph_Algo**
- The implementation of algorithmic queries about the graph, such as:
    - isConnected() - check if the graph is connected, meaning the is a path between every 2 nodes in the graph.
    - shortestPath() - returns the shortest path by weight (if exists) between 2 nodes in the graph by preforming the Dijkstra algorithm (view attaches video in links for source)
    - shortestPathDist() - returns the distance of the shortest path by weight (if exists) between 2 nodes in the graph by using the shortestPath() function to find the weight of the returned path. 
    - save() - allows saving the current WGA's graph in a file that can be later loaded back.
    - load() - allows loading a graph from a file, and initialize them if loaded successfully to the WGA.




