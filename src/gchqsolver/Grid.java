/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gchqsolver;

import java.math.BigInteger;

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
         * We then use bitwise arithmetic to determine if any cells in each row
         * can be determined regardless of which permutation is used. These
         * become "known" solved cells and this information is propagated to the
         * other rows and columns.
         *
         * This process iterates, gradually narrowing down the number of valid
         * permutations for each row and column. Eventually we narrow it down so
         * that only one valid permutation exists for every row and therefore
         * there is only one global permutation.
         *
         * The grid is considered solved at this point and the results are
         * printed to the console.
         *
         */
        BigInteger totalCombinations;
        BigInteger lastTotalCombinations = null;

        do {

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

            System.out.println("totalCombinations: " + totalCombinations.toString());

            if (lastTotalCombinations != null) {
                if (totalCombinations.equals(lastTotalCombinations)) {
                    /**
                     * we've not reduced the total number of combinations in this iterations
                     * we must be stuck so break early
                     */                
                    break;
                    
                }
            }

            lastTotalCombinations = totalCombinations;

        } while (!totalCombinations.equals(BigInteger.ONE));

        
        if(totalCombinations.equals(BigInteger.ONE)){
            printResult();
            System.out.println("SOLVED");
        }else{
            System.out.println("COULD NOT SOLVE");
        }
        

        
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
