package cn.shulaoh.trees;

import java.util.HashMap;
import java.util.Map;


/*********************************************************************
 * 1. If 'x' is a substring of txt, then 'x' represents the state (ie. location) in the suffix tree
 *    found by tracing out the characters of x from the root. Note that 'x' might be part-way along
 *    an edge of tree.
 * 2. A vertex (node) of the suffix tree is called an explicit state.
 * 3. A substring x = txt[L..R] can be represented by (L, R), an array with 2 elements. upper and lower
 *    boundary.
 * 4. if 'v' is a vertex of the suffix tree, the pair (v, x), equivalently (v, (L, R)), represents the
 *    state (location) in the suffix tree found by tracing out the characters of x from v.
 * 5. (v, x) os canonical if v is the last explicit state on the path from v to (v, x).
 ********************************************************************/


/*********************************************************************
 * pseudo code
 *
 * for each character in the string:
 *     pre-process {
 *         update Active Point, remainder
 *     }
 *
 *     update tree
 *
 ********************************************************************/
public class SuffixTree {

    static final int END_FLAG =  Integer.MAX_VALUE;
    static final String NONE_EDGE = "none";

    static class Node {

        int value; /*value = i 表示该结点表示从第i个字符开始的后缀*/
        Map<Character, Edge> edges = new HashMap<Character, Edge>();

        Node suffixLink;

        Node () {

        }

        Node(int v) {
            value = v;
        }

        void addEdge(Edge e) {
            edges.put(e.firstLetter, e);
            e.parent = this;
        }
    }

    static class Edge {
        Node parent;
        Node child;
        Character firstLetter;
        int labelStart;
        int labelEnd;

        Edge(Character ch, int s, int e) {
            firstLetter = ch;
            labelStart = s;
            labelEnd = e;
        }
    }

    private int remainder = 0;

    private Node activeNode;
    private String activeEdge;
    private int activeLength;

    private Node root;

    private String source;


    private Edge getSuffixNode(char s) {
        Edge e = activeNode.edges.get(s);
        return e;
    }

    private Edge getActiveEdge() {
        if (activeEdge.equals(NONE_EDGE)) {
            return null;
        }

        return activeNode.edges.get(activeEdge.charAt(0));
    }

    private void processRemainder(int pos) {
        Edge e = null, e1 = null, e2 = null;
        Node n1 = null, n2 = null;
        Node pre = null, parent = null;

        Character ch = source.charAt(pos);
        int p = pos - remainder + 1;

        while (remainder > 0) {
            e = getActiveEdge();
            if (e == null) {
                //插入一个新的结点
                e1 = new Edge(ch, pos, END_FLAG);
                activeNode.addEdge(e1);
                n1 = new Node();
                e1.child = n1;
            } else {
                parent = e.child;
                Character c1 = source.charAt(e.labelStart + activeLength);
                e.labelEnd = e.labelStart + activeLength - 1;
                n1 = new Node();
                n2 = new Node();
                e1 = new Edge(c1, e.labelStart + activeLength, END_FLAG);
                e2 = new Edge(ch, p, END_FLAG);
                e1.child = n1;
                e2.child = n2;
                parent.addEdge(e1);
                parent.addEdge(e2);
                if (pre != null) {
                    pre.suffixLink = parent;
                }
                pre = parent;
                activeLength--;
                p++;
                activeEdge = String.valueOf(source.charAt(p));
                if (activeLength == 0) {
                    if (activeNode.suffixLink != null) {
                        activeNode = activeNode.suffixLink;
                    } else {
                        activeNode = root;
                        activeEdge = NONE_EDGE;
                    }
                }
            }

            remainder--;
    }
}

    public void buildSuffixTree(String str) {

        char[] chars = str.toCharArray();
        Edge e = null;
        for (int i = 0; i < chars.length; i++) {
            Character ch = chars[i];

            remainder++;
            if (activeLength == 0) {
                e = getSuffixNode(ch);
            }

            if (e != null) {
                if (e.labelEnd < e.labelStart + activeLength) {//如果当前匹配长度超过该边包含字符数，重置结点到子结点。
                    activeNode = e.child;
                    activeLength = 0;
                    activeEdge = NONE_EDGE;
                } else {//如果匹配，更新结点继续。
                    Character ch1 = source.charAt(e.labelStart + activeLength);
                    if (ch1.equals(ch)) {
                        if (activeEdge.equals(NONE_EDGE)) {
                            activeEdge = String.valueOf(ch);
                        }
                        activeLength++;
                        continue;
                    }
                }
            }

            //此时，需要根据remainder的个数来更新了。
            processRemainder(i);
        }

    }

    public SuffixTree(String s) {
        root = new Node(-1);
        source = s;

        remainder = 0;
        activeNode = root;
        activeEdge = NONE_EDGE;
        activeLength = 0;

        /*****************************************
         * 1. 该后缀字符不存在，且不存在保存的字符。
         * 2. 该后缀字符不存在，且缓存中有字符。
         * 3. 该后缀字符存在，且并没有完全和边字符串匹配。
         * 4. 该后缀字符存在，且缓存中完全匹配边。
         ****************************************/

        buildSuffixTree(s);

    }

    public static void main(String[] args) {
        System.out.println("Start Testing...");
        String str = "abcabxabcd";
        SuffixTree st = new SuffixTree(str);
        System.out.println("End Testing...");
    }

}
