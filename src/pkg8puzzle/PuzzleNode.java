/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8puzzle;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author soheilchangizi
 */
public class PuzzleNode{
    
    private int x, y;
    private int dep;
    private int fn;
    private String action;
    private int[][] state;
    private PuzzleNode parent;
    private List<PuzzleNode> children;
    private static int PUZZLE_LIMIT = 3;
    
    public PuzzleNode(int[][] state, int x, int y) {
        this.fn = 0;
        this.dep = 0;
        this.x = x;
        this.y = y;
        this.state = new int[PUZZLE_LIMIT][PUZZLE_LIMIT];
        for (int i = 0; i < PUZZLE_LIMIT; i++) {
            for (int j = 0; j < PUZZLE_LIMIT; j++) {
                this.state[i][j] = state[i][j];
            }
        }
        children = new ArrayList<>();
    }

    public PuzzleNode(int[][] state, int x, int y, String action) {
        this.fn = 0;
        this.x = x;
        this.y = y;
        this.action = action;
        this.state = new int[PUZZLE_LIMIT][PUZZLE_LIMIT];
        for (int i = 0; i < PUZZLE_LIMIT; i++) {
            for (int j = 0; j < PUZZLE_LIMIT; j++) {
                this.state[i][j] = state[i][j];
            }
        }
        children = new ArrayList<>();
        int tmp = 0;
        switch(action){
            case "UP":
                tmp = this.state[x-1][y];
                this.state[x-1][y] = 0;
                this.state[x][y] = tmp;
                this.x--;
                break;
            case "DOWN":
                tmp = this.state[x+1][y];
                this.state[x+1][y] = 0;
                this.state[x][y] = tmp;
                this.x++;
                break;
            case "LEFT":
                tmp = this.state[x][y-1];
                this.state[x][y-1] = 0;
                this.state[x][y] = tmp;
                this.y--;
                break;
            case "RIGHT":
                tmp = this.state[x][y+1];
                this.state[x][y+1] = 0;
                this.state[x][y] = tmp;
                this.y++;
                break;    
        }
    }
    
    public PuzzleNode(int[][] state, int x, int y, int fn) {
        this.fn = fn;
        this.x = x;
        this.y = y;
        this.state = new int[PUZZLE_LIMIT][PUZZLE_LIMIT];
        for (int i = 0; i < PUZZLE_LIMIT; i++) {
            for (int j = 0; j < PUZZLE_LIMIT; j++) {
                this.state[i][j] = state[i][j];
            }
        }
        children = new ArrayList<>();
    }

    public PuzzleNode(int[][] state, int x, int y, String action, int fn) {
        this.fn = fn;
        this.x = x;
        this.y = y;
        this.action = action;
        this.state = new int[PUZZLE_LIMIT][PUZZLE_LIMIT];
        for (int i = 0; i < PUZZLE_LIMIT; i++) {
            for (int j = 0; j < PUZZLE_LIMIT; j++) {
                this.state[i][j] = state[i][j];
            }
        }
        children = new ArrayList<>();
        int tmp = 0;
        switch(action){
            case "UP":
                tmp = this.state[x-1][y];
                this.state[x-1][y] = 0;
                this.state[x][y] = tmp;
                this.x--;
                break;
            case "DOWN":
                tmp = this.state[x+1][y];
                this.state[x+1][y] = 0;
                this.state[x][y] = tmp;
                this.x++;
                break;
            case "LEFT":
                tmp = this.state[x][y-1];
                this.state[x][y-1] = 0;
                this.state[x][y] = tmp;
                this.y--;
                break;
            case "RIGHT":
                tmp = this.state[x][y+1];
                this.state[x][y+1] = 0;
                this.state[x][y] = tmp;
                this.y++;
                break;    
        }
    }
    
    public boolean checkGoal(){
        return state[0][0] == 1 && state[0][1] == 2 && state[0][2] == 3
                && state[1][0] == 8 && state[1][1] == 0 && state[1][2] == 4
                && state[2][0] == 7 && state[2][1] == 6 && state[2][2] == 5;
    }
    
    public static boolean isPossNode(int[][] state, int x, int y, String action){
        switch(action){
            case "UP":
                if(x-1 >= 0) return true;
                break;
            case "DOWN":
                if(x+1 <= PUZZLE_LIMIT-1) return true;
                break;
            case "LEFT":
                if(y-1 >= 0) return true;
                break;
            case "RIGHT":
                if(y+1 <= PUZZLE_LIMIT-1) return true;
                break;
        }
        return false;
    }

    public int[][] getState() {
        return state;
    }

    public PuzzleNode getParent() {
        return parent;
    }

    public void setParent(PuzzleNode parent) {
        this.parent = parent;
    }

    public List<PuzzleNode> getChildren() {
        return children;
    }

    public void addChildren(PuzzleNode child) {
        this.children.add(child);
    }

    public static int getPUZZLE_LIMIT() {
        return PUZZLE_LIMIT;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDep() {
        return dep;
    }

    public void setDep(int dep) {
        this.dep = dep;
    }

    public String getAction() {
        return action;
    }
    
    @Override
    public String toString() {
        String tpp = "";
        for (int i = 0; i < PUZZLE_LIMIT; i++) {
            for (int j = 0; j < PUZZLE_LIMIT; j++) {
                tpp += this.state[i][j] + " ";
            }
            tpp += "\n";
        }
        tpp += "-----\n";
        
        return tpp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Arrays.deepHashCode(this.state);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PuzzleNode other = (PuzzleNode) obj;
        if (!Arrays.deepEquals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    public void reset(){
        children.clear();
    }
    
}
