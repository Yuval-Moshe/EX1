package ex1;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DS_Test {
    private static Random _rnd = null;


//    public static weighted_graph graphCreator(int v, int e, int seed){
//        weighted_graph wg = new WGraph_DS();
//        _rnd = new Random(seed);
//        for(int i=0;i<v;i++) {
//            wg.addNode(n);
//        }    }

    //Test Basic Functionalities
    @Test
    public void test_0() {
        weighted_graph WG = new WGraph_DS();
        WG.addNode(0);
        WG.addNode(1);
        WG.connect(0, 1, 4.3);
        assertTrue(WG.hasEdge(0, 1));
        assertEquals(WG.getEdge(0, 1), 4.3);
        WG.removeEdge(0, 1);
        assertFalse(WG.hasEdge(0, 1));
        assertEquals(WG.getEdge(0, 1), -1);
        WG.addNode(2);
        WG.addNode(3);
        WG.connect(0, 2, 3.5);
        WG.connect(0, 3, 2.6);
        assertEquals(WG.getEdge(0, 2), 3.5);
        assertEquals(WG.getEdge(0, 3), 2.6);
        WG.removeNode(2);
        assertFalse(WG.hasEdge(0, 2));
        assertEquals(WG.edgeSize(), 1);
        WG.connect(0, 0, 8);
        assertEquals(WG.edgeSize(), 1);
    }

    @Test
    public void test_1() {
        /** Test Deep Copy **/

        // Tes 2.1 //
        weighted_graph WG = new WGraph_DS();
        for (int i = 0; i < 10; i++) {
            WG.addNode(i);
        }

        for (int i = 0; i < 9; i++) {
            double rnd_weight = Math.random() * (5) + 1;
            WG.connect(i, i + 1, rnd_weight);
        }
        weighted_graph WG_Copy = new WGraph_DS(WG);
        boolean flag =true;
        flag &= WG.getV().containsAll(WG_Copy.getV()) ;
        flag &= WG_Copy.getV().containsAll(WG.getV()) ;
        assertTrue(flag);
        for(node_info node : WG.getV()){
            for(node_info ni : WG.getV()){
                if(WG.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG_Copy.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        for(node_info node : WG_Copy.getV()){
            for(node_info ni : WG_Copy.getV()){
                if(WG_Copy.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        assertTrue(flag);

        // Test 2.2 - Remove 1 edge  //
        node_info curr = WG.getV().iterator().next();
        node_info node_to_remove = WG.getV(curr.getKey()).iterator().next();
        WG.removeEdge(curr.getKey(), node_to_remove.getKey());
        flag &= WG.getV().containsAll(WG_Copy.getV()) ;
        flag &= WG_Copy.getV().containsAll(WG.getV()) ;
        for(node_info node : WG.getV()){
            for(node_info ni : WG.getV()){
                if(WG.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG_Copy.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        for(node_info node : WG_Copy.getV()){
            for(node_info ni : WG_Copy.getV()){
                if(WG_Copy.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        assertFalse(flag);


    }


}





