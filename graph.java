import java.util.*;
import java.lang.*;

public class graph{
    static List<node> nodes = new ArrayList<node>();
    static int minDegree = 0; //The smallest degree that is a neighbor to the largest degree node
    static int maxDegree = 0;
    static int maxInit = 0;
    static int totalUsed = 0;
    static int bigColor = 0;
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
                //adding something to front of arraylist to offset
                node toAdd = new node(0);
                toAdd.color = 0;
                nodes.add(toAdd);
                nodes.sort((n1, n2) -> Integer.compare(n1.name, n2.name));
                for(int i = 0; i < nums.length; i++){
                    int numz = Integer.parseInt(nums[i]);
                    if(numz != 0){
                        nodes.get(i + 1).color = numz;
                        nodes.get(i + 1).pre = true;
                        if(nodes.get(i + 1).color > maxInit){
                            maxInit = nodes.get(i + 1).color;
                        }
                    }
                }
            }
        }
        getMaxClique();
        colorGraph();
        //optimize();
        output();
        //output2(); 
    }
    public static void output2(){
        System.out.println(maxDegree);
        int maxColor = 0;
        for(int i = 0; i < nodes.size(); i++){
            if(nodes.get(i).color > maxColor){
                maxColor = nodes.get(i).color;
            }
        }
        System.out.println(maxColor);
    }
    public static void output(){
        if(bigColor - maxInit > 0){
            System.out.println(bigColor - maxInit);
        }else{
            System.out.println(0);
        }
        for(int i = 1; i < nodes.size(); i++){
            System.out.print(nodes.get(i).color + " ");
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
    public static void optimize(){
        List<Integer> colors = new ArrayList<Integer>();
        for(int i = 1; i < nodes.size(); i++){
            for(int p = 0; p < nodes.get(i).neighbors.size(); p++){
                colors.add(nodes.get(nodes.get(i).neighbors.get(p).dest).color);
            }
            for(int u = 1; u < minDegree; u++){
                if(!colors.contains(u) && !nodes.get(i).pre){
                    nodes.get(i).color = u;
                    break;
                }
            }
            colors.clear();
        }
    }
    public static void colorGraph(){
        List<Integer> usedColor = new ArrayList<Integer>();
        for(int i = 1; i < nodes.size(); i++){
            usedColor.add(0);
           // System.out.println("OLD COLOR " +  nodes.get(i).color);
            if(nodes.get(i).color == 0){
                for(int p = 0; p < nodes.get(i).neighbors.size(); p++){
                    usedColor.add(nodes.get(nodes.get(i).neighbors.get(p).dest).color);
                }
                for(int u = 1;  u < maxDegree; u++){
                    //
                    //System.out.println("trying " + u);
                    if(!usedColor.contains(u) && !nodes.get(i).pre){
                        //System.out.println("NEW COLOR " + u);
                        nodes.get(i).color = u;
                        if(u > bigColor){
                            bigColor = u;
                        }
                        break;
                    }
                }
            }
            usedColor.clear();
        }

    }
    public static void getMaxClique(){
        for(int i = 0; i< nodes.size(); i++){
            if(nodes.get(i).neighbors.size() > maxDegree){
                maxDegree = nodes.get(i).neighbors.size();
            }
            if(nodes.get(i).neighbors.size() > minDegree){
                int smallest = nodes.get(i).neighbors.size();
                for(int p = 0; p < nodes.get(i).neighbors.size(); p++){
                    if(smallest > nodes.get(nodes.get(i).neighbors.get(p).dest).neighbors.size()){
                        smallest = nodes.get(nodes.get(i).neighbors.get(p).dest).neighbors.size();
                    }
                }
                minDegree = smallest;
            }
        }
    }
}


class node{
    boolean visited = false;
    int color;
    ArrayList<edge> neighbors = new ArrayList<edge>();
    int name;
    boolean pre = false;
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