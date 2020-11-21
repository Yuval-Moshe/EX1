package ex1.tests;
import ex1.src.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

    /**
     * Test class for WGraph_Algo.
     * List of all the test in this Test class:
     * basicAlgoFunction():
     *  - Test 1.1 - test init() and getGraph() functions, graph's should be equals
     *  - Test 1.2 - test the copy() function, the graph's should be equal
     * isConnected():
     *  - Test 2.1 - Tests a connected graph before node removal: True, after node removal: False
     *  - Test 2.2 - Empty graph : True
     *  - Test 2.3 - 1 node graph : True
     * shortestPath():
     *  - Test 3.1 - Path 0->2->3->4, pathDist = 6.00;
     *  - Test 3.2 - Path 0->4->2->3->10, pathDist = 6.00;
     *  - Test 3.3 - Path 0->6->10, pathDist = 7.00;
     *  - Test 3.4 - No Path, pathDist = -1.00;
     *  - Test 3.5 - 1 Node Graph, pathDist = 0.00;
     *  - Test 3.6 - The src node and dest node are neighbors but the path between them is not the shortest, Path -> 1->2->...->90
     *  - Test 3.7 - The src node and dest node are neighbors and the path between them is the shortest, Path -> 1->90
     *  load_save():
     *  - Test 4.1 - Saving a graph to a file and then loading him to another wag, the graph of both wga should be equals
     * **/

public class WGraph_Algo_Test {
        @Test
        public void basicAlgoFunction(){
            //Test 1.1//
            weighted_graph WG_1 = new WGraph_DS();
            for(int i=0; i<100; i++){
                WG_1.addNode(i);
            }
            for(int i=0; i<90; i++){
                for(int j=0; j<10;j++){
                    WG_1.connect(i, i+j, j+1);
                }
            }
            weighted_graph_algorithms WGA = new WGraph_Algo();
            WGA.init(WG_1);
            assertEquals(WG_1, WGA.getGraph());

            //Test 1.2//
            weighted_graph WG_2 = WGA.copy();
            assertEquals(WGA.getGraph(), WG_2);
        }

        @Test
        public void isConnected() {
            weighted_graph WG_1 = new WGraph_DS();
            for (int i = 0; i < 10; i++) {
                WG_1.addNode(i);
            }

            for (int i = 0; i < 9; i++) {
                double rnd_weight = Math.random() * (5) + 1;
                WG_1.connect(i, i + 1, rnd_weight);
            }

            //Test 2.1//
            weighted_graph_algorithms WGA = new WGraph_Algo();
            WGA.init(WG_1);
            assertTrue(WGA.isConnected());
            WGA.getGraph().removeNode(1);
            assertFalse(WGA.isConnected());

            //Test 2.2//
            weighted_graph WG_2 = new WGraph_DS();
            WGA.init(WG_2);
            assertTrue(WGA.isConnected());

            //Test 2.3//
            WGA.getGraph().addNode(0);
            assertTrue(WGA.isConnected());
        }

        @Test
        public void shortestPath() {
            //Test 3.1//
            weighted_graph WG_1 = new WGraph_DS();
            for (int i = 0; i < 5; i++) {
                WG_1.addNode(i);
            }

            WG_1.connect(0, 1, 1);
            WG_1.connect(0, 2, 2);
            WG_1.connect(1, 2, 1);
            WG_1.connect(1, 4, 6);
            WG_1.connect(2, 3, 3);
            WG_1.connect(3, 4, 1);
            weighted_graph_algorithms WGA = new WGraph_Algo();
            WGA.init(WG_1);
            List<node_info> shortestPath = WGA.shortestPath(0, 4);
            List<node_info> expected = new ArrayList<>();
            expected.add(WG_1.getNode(0));
            expected.add(WG_1.getNode(2));
            expected.add(WG_1.getNode(3));
            expected.add(WG_1.getNode(4));
            boolean flag = true;
            for (int i = 0; i < shortestPath.size(); i++) {
                flag &= shortestPath.get(i).equals(expected.get(i));
            }
            assertTrue(flag);
            assertEquals(WGA.shortestPathDist(0, 4), 6);

            //Test 3.2//
            weighted_graph WG_2 = new WGraph_DS();
            for (int i = 0; i < 11; i++) {
                WG_2.addNode(i);
            }
            WG_2.connect(0, 1, 5);
            WG_2.connect(0, 4, 2);
            WG_2.connect(0, 6, 4);
            WG_2.connect(1, 2, 9);
            WG_2.connect(1, 3, 7);
            WG_2.connect(2, 3, 1);
            WG_2.connect(2, 4, 2);
            WG_2.connect(3, 10, 1);
            WG_2.connect(4, 7, 4);
            WG_2.connect(4, 9, 5);
            WG_2.connect(5, 6, 2);
            WG_2.connect(5, 8, 4);
            WG_2.connect(6, 7, 7);
            WG_2.connect(6, 10, 3);
            WG_2.connect(7, 8, 1);
            WG_2.connect(8, 9, 3);
            WG_2.connect(9, 10, 6);
            WGA.init(WG_2);
            shortestPath = WGA.shortestPath(0, 10);
            expected.clear();
            expected.add(WG_2.getNode(0));
            expected.add(WG_2.getNode(4));
            expected.add(WG_2.getNode(2));
            expected.add(WG_2.getNode(3));
            expected.add(WG_2.getNode(10));
            flag = true;
            for (int i = 0; i < shortestPath.size(); i++) {
                flag &= shortestPath.get(i).equals(expected.get(i));
            }
            assertTrue(flag);
            assertEquals(WGA.shortestPathDist(0, 10), 6);

            //Test 3.3//
            WGA.getGraph().removeEdge(3, 10);
            shortestPath = WGA.shortestPath(0, 10);
            expected.clear();
            expected.add(WG_2.getNode(0));
            expected.add(WG_2.getNode(6));
            expected.add(WG_2.getNode(10));
            flag = true;
            for (int i = 0; i < shortestPath.size(); i++) {
                flag &= shortestPath.get(i).equals(expected.get(i));
            }
            assertTrue(flag);
            assertEquals(WGA.shortestPathDist(0, 10), 7);

            //Test 3.4//
            WGA.getGraph().removeEdge(6, 10);
            WGA.getGraph().removeEdge(9, 10);
            shortestPath = WGA.shortestPath(0, 10);
            assertTrue(shortestPath.isEmpty());
            assertEquals(WGA.shortestPathDist(0, 10), -1);


            //Test 3.5//
            weighted_graph WG_3 = new WGraph_DS();
            WG_3.addNode(0);
            WGA.init(WG_3);
            assertEquals(WGA.shortestPathDist(0, 0), 0);

            //Test 3.6//
            weighted_graph WG_5 = new WGraph_DS();
            WG_5.addNode(1);
            WG_5.addNode(90);
            WG_5.connect(1, 90, 91);
            for (int i = 2; i < 90; i++) {
                WG_5.addNode(i);
            }
            for (int i = 1; i < 90; i++) {
                WG_5.connect(i, i + 1, 0.5);
            }
            WGA.init(WG_5);
            shortestPath = WGA.shortestPath(1, 90);
            flag = true;
            for (int i = 1; i < 91; i++) {
                flag &= shortestPath.get(i - 1).equals(WG_5.getNode(i));
            }
            assertTrue(flag);

            //Test 3.7//
            for (int i = 1; i < 90; i++) {
                WG_5.connect(i, i + 1, 2);
            }
            shortestPath = WGA.shortestPath(1, 90);
            flag = true;
            assertEquals(shortestPath.size(), 2);
            flag &= shortestPath.get(0).equals(WG_5.getNode(1));
            flag &= shortestPath.get(1).equals(WG_5.getNode(90));
            assertTrue(flag);
            assertEquals(WGA.shortestPathDist(1, 90), 91);
        }

        @Test
        public void save_load() {
            //Test 4.1//
            String file_name = "WGA_1_WGraph";
            weighted_graph WG_1 = new WGraph_DS();
            for (int i = 0; i < 10; i++) {
                WG_1.addNode(i);
            }
            for (int i = 0; i < 9; i++) {
                WG_1.connect(i, i + 1, 3);
            }
            weighted_graph_algorithms WGA_1 = new WGraph_Algo();
            WGA_1.init(WG_1);
            assertTrue(WGA_1.save(file_name));
            weighted_graph_algorithms WGA_2 = new WGraph_Algo();
            WGA_2.load(file_name);
            assertEquals(WGA_1.getGraph(), WGA_2.getGraph());



        }

    }
