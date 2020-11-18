package ex1;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

    /** Test Class for WGraph_DS
     * List of all the test in this Test class:
     * basicFunctions():
     *  - Test 1.1 - adding 10 nodes, nodeSize() : 10, getNode()!=null
     *  - Test 1.2 - connecting 2 nodes by weight 4.3, hasEdge(): True, edgeSize() : 1, getEdge(): 4.3.
     *  - Test 1.3 - connecting an an already connected node by weight 2 -> should change edge weight to 2.
     *  - Test 1.4 - removing edge, hasEdge(): False, edgeSize(): 0
     *  - Test 1.5 - check weight of unconnected nodes, getEdge(): -1
     *  - Test 1.6 - checking that removing a node that doesn't exists doesn't preform any action.
     * deepCopy():
     *  - Test 2.1 - Creating a graph and a copy of him, graph's are equals.
     *  - Test 2.2 - Remove node from one of the graph and test that the other hasn't changed. graph's are not equals.
     *  getV():
     *  - Test 3.1 - checks that the getV() equals to all the node that were added to the graph.
     *  - Test 3.2 - connection all the nodes to node with key 0, check if getV(0) equals to all the nodes minus himself.
     *  - Test 3.3 - remove an edge between 0 and 1, now getV(0) is not equal to all the nodes minus himself.
     * **/


class WGraph_DS_Test {


        @Test
        public void basicFunctions() {
            boolean flag = true;
            weighted_graph WG = new WGraph_DS();
            //Test 1.1 //
            for (int i = 0; i < 10; i++) {
                WG.addNode(i);
            }
            assertEquals(WG.nodeSize(), 10);
            for (int i = 0; i < 10; i++) {
                flag &= (WG.getNode(i) != null);
            }
            assertTrue(flag);

            //Test 1.2//
            WG.connect(0, 1, 4.3);
            assertTrue(WG.hasEdge(0, 1));
            assertEquals(WG.getEdge(0, 1), 4.3);
            assertEquals(WG.edgeSize(), 1);

            //Test 1.3//
            WG.connect(0, 1, 2);
            assertEquals(WG.getEdge(0, 1), 2);

            //Test 1.4//
            WG.removeEdge(0, 1);
            assertFalse(WG.hasEdge(0, 1));
            assertEquals(WG.edgeSize(), 0);

            //Test 1.5//
            assertEquals(WG.getEdge(0, 1), -1);

            //Test 1.6//
            int mcPrev = WG.getMC();
            double edgesPrev = WG.edgeSize();
            node_info temp = WG.removeNode(35);
            assertNull(temp);
            assertEquals(mcPrev, WG.getMC());
            assertEquals(edgesPrev, WG.edgeSize());
        }

        @Test
        public void deepCopy() {
            //Test 2.1//
            weighted_graph WG = new WGraph_DS();
            for (int i = 0; i < 10; i++) {
                WG.addNode(i);
            }

            for (int i = 0; i < 9; i++) {
                double rnd_weight = Math.random() * (5) + 1;
                WG.connect(i, i + 1, rnd_weight);
            }
            weighted_graph WG_Copy = new WGraph_DS(WG);
            assertEquals(WG, WG_Copy);

            //Test 2.2//
            node_info curr = WG.getV().iterator().next();
            node_info node_to_remove = WG.getV(curr.getKey()).iterator().next();
            WG.removeEdge(curr.getKey(), node_to_remove.getKey());
            assertNotEquals(WG, WG_Copy);


        }

        @Test
        public void getV() {
            //Test 3.1//
            boolean flag = true;
            weighted_graph WG = new WGraph_DS();
            Collection<node_info> compV = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                WG.addNode(i);
                compV.add(WG.getNode(i));
                WG.connect(i, 0, 3);
            }

            flag &= (compV.size() == WG.getV().size());
            flag &= WG.getV().containsAll(compV);
            assertTrue(flag);

            //Test 3.2//
            flag = true;
            compV.remove(WG.getNode(0));
            flag &= (compV.size() == WG.getV(0).size());
            flag &= WG.getV(0).containsAll(compV);
            assertTrue(flag);

            //Test 3.3//
            WG.removeEdge(0, 1);
            flag &= (compV.size() == WG.getV(0).size());
            flag &= WG.getV(0).containsAll(compV);
            assertFalse(flag);


        }

    }