/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pkg8puzzle;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author soheilchangizi
 */
public class ProblemTree {
    private PuzzleNode root;
    private ArrayList<PuzzleNode> checker = new ArrayList<>();
    private Queue<PuzzleNode> queue = new LinkedTransferQueue<>();
    PriorityQueue<PuzzleNode> pq=
            new PriorityQueue<>((a,b) -> a.getFn() - b.getFn());
    private Stack<String> path = new Stack<>();
    private boolean foundRBFS = false;
    
    public ProblemTree(PuzzleNode rootData) {
        root = new PuzzleNode(rootData.getState(), rootData.getX(), rootData.getY());
    }
    
    public void bfs(PuzzleNode r){
        r.setParent(root);
        path.clear();
        queue.clear();
        queue.add(r);
        while(!queue.isEmpty()){
            PuzzleNode node = queue.remove();
            //Add solution to path for illustration
            if(node.checkGoal()){
                path.push(node.getAction());
                while(!node.getParent().equals(root)){
                    node = node.getParent();
                    path.push(node.getAction());
                }
                break;
            }
            
            // Get Child Node
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "UP")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP"))){
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP");
                tmp.setParent(node);
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "DOWN")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN"))){
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN");
                tmp.setParent(node);
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(),  "RIGHT")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT"))){
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT");
                tmp.setParent(node);
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "LEFT")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT"))){
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT");
                tmp.setParent(node);
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            
            // Add new Children to queue
            for (PuzzleNode pz : node.getChildren()) {
                queue.add(pz);
            }
        }
    }
    
    
    public void astarMD(PuzzleNode r){
        path.clear();
        pq.clear();
        r.reset();
        Heuristics hr = new Heuristics(r.getState());
        r.setFn(hr.getMD());
        pq.add(r);
        while(!pq.isEmpty()){
            PuzzleNode node = pq.remove();
            
            //Add solution to path for illustration
            if(node.checkGoal()){
                path.push(node.getAction());
                while(!node.getParent().equals(root)){
                    node = node.getParent();
                    path.push(node.getAction());
                }
                break;
            }
            
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "UP")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMD() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "DOWN")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMD() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(),  "RIGHT")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMD() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "LEFT")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMD() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            for (PuzzleNode pz : node.getChildren()) {
                pq.add(pz);
            }
        }
    }
    
    
    public void astarMT(PuzzleNode r){
        path.clear();
        pq.clear();
        r.reset();
        Heuristics hr = new Heuristics(r.getState());
        r.setFn(hr.getMT());
        pq.add(r);
        while(!pq.isEmpty()){
            PuzzleNode node = pq.remove();
            
            if(node.checkGoal()){
                path.push(node.getAction());
                while(!node.getParent().equals(root)){
                    node = node.getParent();
                    path.push(node.getAction());
                }
                break;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "UP")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMT() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "DOWN")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMT() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(),  "RIGHT")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMT() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "LEFT")
                    && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT"))){
                Heuristics h = new Heuristics(node.getState());
                PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT");
                tmp.setParent(node);
                tmp.setDep(node.getDep()+1);
                tmp.setFn(h.getMT() + tmp.getDep());
                node.addChildren(tmp);
                checker.add(tmp);
                tmp = null;
            }
            for (PuzzleNode pz : node.getChildren()) {
                pq.add(pz);
            }
        }
    }
    
    
    public void rbfsMD(PuzzleNode node, int Limit, int dep){
        int memory = 0;
        path.clear();
        if(node.checkGoal()){
            foundRBFS = true;
            memory -= node.getChildren().size();
            
            path.push(node.getAction());
            while(!node.getParent().equals(root)){
                node = node.getParent();
                path.push(node.getAction());
                memory += node.getChildren().size();
            }
            return;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "UP")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP",
                    Math.max(h.getMD()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "DOWN")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN",
                    Math.max(h.getMD()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(),  "RIGHT")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT",
                    Math.max(h.getMD()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "LEFT")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT",
                    Math.max(h.getMD()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        while(true){
            if(node.getChildren().size() >= 2){
                int first = node.getChildren().get(0).getFn();
                int second = node.getChildren().get(1).getFn();
                int imin = 0;
                for (int i = 0; i < node.getChildren().size() ; i++){
                    if (node.getChildren().get(i).getFn() < first){
                        second = first;
                        first = node.getChildren().get(i).getFn();
                        imin = i;
                    }
                    
                    else if (node.getChildren().get(i).getFn() < second
                            && node.getChildren().get(i).getFn() != first){
                        second = node.getChildren().get(i).getFn();
                    }
                }
                if(first > Limit || foundRBFS){
                    node.setFn(first);
                    return;
                }
                rbfsMD(node.getChildren().get(imin), Math.min(second, Limit), dep+1);
            }else if(node.getChildren().size() == 1){
                if(node.getChildren().get(0).getFn() > Limit || foundRBFS){
                    node.setFn(node.getChildren().get(0).getFn());
                    return;
                }
                rbfsMD(node.getChildren().get(0), Limit, dep+1);
            }else{
                node.setFn(Integer.MAX_VALUE);
                return;
            }
        }
    }
    
    public void rbfsMT(PuzzleNode node, int Limit, int dep){
        int memory = 0;
        
        if(node.checkGoal()){
            foundRBFS = true;
            memory -= node.getChildren().size();
            
            path.push(node.getAction());
            while(!node.getParent().equals(root)){
                node = node.getParent();
                path.push(node.getAction());
                memory += node.getChildren().size();
            }
            return;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "UP")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "UP",
                    Math.max(h.getMT()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "DOWN")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "DOWN",
                    Math.max(h.getMT()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(),  "RIGHT")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(),  "RIGHT",
                    Math.max(h.getMT()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        if(PuzzleNode.isPossNode(node.getState(), node.getX(), node.getY(), "LEFT")
                && !checker.contains(new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT"))){
            Heuristics h = new Heuristics(node.getState());
            PuzzleNode tmp = new PuzzleNode(node.getState(), node.getX(), node.getY(), "LEFT",
                    Math.max(h.getMT()+dep, node.getFn()));
            tmp.setParent(node);
            node.addChildren(tmp);
            checker.add(tmp);
            tmp = null;
        }
        while(true){
            if(node.getChildren().size() >= 2){
                int first = node.getChildren().get(0).getFn();
                int second = node.getChildren().get(1).getFn();
                int imin = 0;
                for (int i = 0; i < node.getChildren().size() ; i++){
                    if (node.getChildren().get(i).getFn() < first){
                        second = first;
                        first = node.getChildren().get(i).getFn();
                        imin = i;
                    }
                    
                    else if (node.getChildren().get(i).getFn() < second
                            && node.getChildren().get(i).getFn() != first){
                        second = node.getChildren().get(i).getFn();
                    }
                }
                if(first > Limit || foundRBFS){
                    node.setFn(first);
                    return;
                }
                rbfsMT(node.getChildren().get(imin), Math.min(second, Limit), dep+1);
            }else if(node.getChildren().size() == 1){
                if(node.getChildren().get(0).getFn() > Limit || foundRBFS){
                    node.setFn(node.getChildren().get(0).getFn());
                    return;
                }
                rbfsMT(node.getChildren().get(0), Limit, dep+1);
            }else{
                node.setFn(Integer.MAX_VALUE);
                return;
            }
        }
    }
    
    public PuzzleNode getRoot() {
        return root;
    }

    public Stack<String> getPath() {
        //Collections.reverse(path);
        return path;
    }

    public int getMemoryUsed() {
        return checker.size();
    }
    
    
}
