/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gchqsolver;

/**
 *
 * @author deanwild
 */
public class Column {
    
    int index;
    int[] blocks;
    boolean[] cells = new boolean[25];
     int totalCells;
    
    void init(int index) {
        
        this.index = index;
        
        for (int i = 0; i < blocks.length; i++) {
            totalCells += blocks[i];
        }        
    }
        
    
    public void loadPermutation(Grid grid){        
        int cellIndex = 0;
        
        for (Row row : grid.rows) {            
            cells[cellIndex] = row.cells[index];
            cellIndex++;
            
        }        
    }
    
    boolean isSolved() {

        int total = 0;

        for (int i = 0; i < cells.length; i++) {
            if (cells[i]) {
                total++;
            }
        }

        // incorrect total cells - break early
        if (total != totalCells) {
            return false;
        }

        int position = 0;
        int blocksFound = 0;

        for (int block : blocks) {

            int targetLength = block;
            boolean blockStart = false;

            while (true) {

                if (targetLength > 0) {

                    if (cells[position]) {
                        blockStart = true;
                        targetLength--;
                    } else {
                        if (blockStart) {
                            // this block should have been longer - break early
                            return false;
                        }
                    }

                    position++;

                } else {
                    blocksFound++;
                    break;
                }

                if (position > 24) {
                    break;
                }
            }

        }

        if (blocksFound == blocks.length) {
            return true;
        }

        return false;
    }

    
}
