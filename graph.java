import java.util.*;
import java.lang.*;

public class graph{
    static ArrayList<node> nodes = new ArrayList<node>();
    static int current;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String[] nodedge = sc.nextLine().split("\\s+");
        while(sc.hasNextLine()){
            String[] nums = sc.nextLine().split("\\s+");
            if(nums.length == 2){
                int first = Integer.parseInt(nums[0]);
                int second = Integer.parseInt(nums[1]);
                if(checkArray(first)){
                    node toAdd = new node(first);
                    nodes.add(toAdd);
                }
                if(checkArray(second)){
                    node toAdd = new node(second);
                    nodes.add(toAdd);
                }
                addEdge(first, second);  
            }else{
                for(int i = 0; i < nums.length; i++){
                    nodes.get(i).color = Integer.parseIntnums[i];
                }
            }
        }   
    }
    public static boolean checkArray(int num){
        boolean toReturn = false;
        for(int p = 0; p < nodes.size(); p++){
            if(nodes.get(p).name == num){
                return false;
            } 
        }
        return true;
    }
    //f == first 
    //s == second
    public static void addEdge(int f, int s){
        for(int i = 0; i < nodes.size(); i++){
            if(nodes.get(i).name == f){
                edge toAdd = new edge(f, s);
                nodes.get(i).addNeighbor(toAdd);
            }
            if(nodes.get(i).name == s){
                edge toAdd = new edge(s,f);
                nodes.get(i).addNeighbor(toAdd);
            }
        }
    }
}


class node{
    boolean visited = false;
    int color;
    ArrayList<edge> neighbors = new ArrayList<edge>();
    int name;
    public node(int n){
        this.name = n;
        this.color = 0;
    }
    public void addNeighbor(edge e){
        neighbors.add(e);
    }

}
class edge{
    int source;
    int dest;
    public edge(int s, int d){
        this.source = s;
        this.dest = d;
    }
}