/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pkg8puzzle;

/**
 *
 * @author soheilchangizi
 */
public class Heuristics {
    
    private int value;
    private int[][] state;
    
    public Heuristics(int[][] state) {
        value = 0;
        this.state = new int[PuzzleNode.getPUZZLE_LIMIT()][PuzzleNode.getPUZZLE_LIMIT()];
        for (int i = 0; i < PuzzleNode.getPUZZLE_LIMIT(); i++) {
            for (int j = 0; j < PuzzleNode.getPUZZLE_LIMIT(); j++) {
                this.state[i][j] = state[i][j];
            }
        }
    }
    
    public int getMT(){ // misplaced tile
        value = 0;
        
        if(state[0][0] == 1) value++;
        if(state[0][1] == 2) value++;
        if(state[0][2] == 3) value++;
        if(state[1][0] == 8) value++;
        if(state[1][1] == 0) value++;
        if(state[1][2] == 4) value++;
        if(state[2][0] == 7) value++;
        if(state[2][1] == 6) value++;
        if(state[2][2] == 5) value++;
        
        return 9-value;
    }
    
    public int getMD(){ // manhatan distance
        value = 0;
        
        for (int i = 0; i < PuzzleNode.getPUZZLE_LIMIT(); i++) {
            for (int j = 0; j < PuzzleNode.getPUZZLE_LIMIT(); j++) {
                if(state[i][j] == 1) value+=(Math.abs(i-0) + Math.abs(j-0));
                if(state[i][j] == 2) value+=(Math.abs(i-0) + Math.abs(j-1));
                if(state[i][j] == 3) value+=(Math.abs(i-0) + Math.abs(j-2));
                if(state[i][j] == 8) value+=(Math.abs(i-1) + Math.abs(j-0));
                if(state[i][j] == 0) value+=(Math.abs(i-1) + Math.abs(j-1));
                if(state[i][j] == 4) value+=(Math.abs(i-1) + Math.abs(j-2));
                if(state[i][j] == 7) value+=(Math.abs(i-2) + Math.abs(j-0));
                if(state[i][j] == 6) value+=(Math.abs(i-2) + Math.abs(j-1));
                if(state[i][j] == 5) value+=(Math.abs(i-2) + Math.abs(j-2));
            }
        }
        return value;
    }
    
}
