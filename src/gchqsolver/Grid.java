/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gchqsolver;

import com.google.gson.Gson;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author deanwild
 */
public class Grid {

    Row[] rows;
    Row[] columns;

    transient int totalCells;

    void solve() {

        // init
        for (int i = 0; i < rows.length; i++) {
            Row row = rows[i];
            row.init();
            totalCells += row.totalCells;
        }

        for (int i = 0; i < columns.length; i++) {
            Row col = columns[i];
            col.init();
        }
        System.out.println("totalCells: " + totalCells);

        // solve 
        /**
         * We use the given information to calculate the number of valid
         * permutations for each row.
         *
         * If any row only has one valid permutation then we lock that in and
         * propagate the new info to the rest of the grid and then recalculate
         * the permutations. We carry on doing this iteratively until no more
         * rows can be locked then we resort to brute forcing the remaining
         * permutations.
         */
        BigInteger totalCombinations;

        boolean foundRowsWith1Permutation = false;

        do {

            foundRowsWith1Permutation = false;

            for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {

                Row row = rows[rowIndex];
               
                row.calculatePermutations();              

                // ensure all hints are propagated to corresponding columns
                for (int black : row.blacks) {
                    columns[black].addBlack(rowIndex);
                }

                for (int white : row.whites) {
                    columns[white].addWhite(rowIndex);
                }
            }

            for (int colIndex = 0; colIndex < columns.length; colIndex++) {
                Row col = columns[colIndex];
               
                col.calculatePermutations();               
             
                // ensure all hints are propagated to corresponding rows
                for (int black : col.blacks) {
                    rows[black].addBlack(colIndex);
                }
                
                for (int white : col.whites) {
                    rows[white].addWhite(colIndex);
                }

            }

            totalCombinations = BigInteger.valueOf(rows[0].permutations.size());

            for (int i = 1; i < rows.length; i++) {
                BigInteger multiplier = BigInteger.valueOf(rows[i].permutations.size());
                totalCombinations = totalCombinations.multiply(multiplier);
            }

            System.out.println("row totalCombinations: " + totalCombinations.toString());
            
           
        } while (!totalCombinations.equals(BigInteger.ONE));

        //Gson gson = new Gson();
         //String json = gson.toJson(this);
         //System.out.println(json); 
         
         printResult();
         
         System.out.println("FINISH");
    }

    boolean isSolved() {

        /*for (int i = 0; i < rows.length; i++) {
         Row row = rows[i];
         if (!row.isSolved()) {
         return false;
         }
         }*/
        for (int i = 0; i < columns.length; i++) {
            Row col = columns[i];
            //  col.loadPermutation();
            if (!col.isSolved()) {
                return false;
            }
        }

        return true;

    }

    private void printResult() {
        for (int i = 0; i < rows.length; i++) {

            Row row = rows[i];

            String rowStr = "";

            for (boolean cell : row.cells) {
                if (cell) {
                    rowStr += "1";
                } else {
                    rowStr += "0";
                }
            }

            System.out.println(rowStr);
        }

    }

}
