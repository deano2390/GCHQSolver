package gchqsolver;

import java.util.ArrayList;

/**
 *
 * @author deanwild
 */
public class Row {

    transient ArrayList<Permutation> permutations = new ArrayList();

    transient boolean[] cells = new boolean[25];

    int[] blocks;

    // cells that are definitely black
    ArrayList<Integer> blacks = new ArrayList<>();

    // cells that are definitely white
    ArrayList<Integer> whites = new ArrayList<>();

    transient int totalCells;

    int permutationCount;

    class Permutation {

        public boolean[] cells = new boolean[25];
        private String binary;
        private int numbericValue;

        private Permutation(String binary) {

            int positionInPermutation = 0;
            int blockNumber = 0;

            for (char c : binary.toCharArray()) {
                if (c == '1') {
                    int sizeOfBlock = blocks[blockNumber];

                    for (int i = 0; i < sizeOfBlock; i++) {
                        cells[positionInPermutation] = true;
                        positionInPermutation++;
                    }

                    blockNumber++;

                } else {
                    cells[positionInPermutation] = false;
                    positionInPermutation++;
                }

            }
        }

        private void validate() {
            binary = "";
            for (int i = 0; i < cells.length; i++) {
                if (cells[i]) {
                    binary += "1";
                } else {
                    binary += "0";
                }
            }

            numbericValue = Integer.parseInt(binary, 2);
        }
    }

    void init() {

        for (int i = 0; i < blacks.size(); i++) {
            int cellLocation = blacks.get(i);
            cells[cellLocation] = true;
        }

        for (int i = 0; i < blocks.length; i++) {
            totalCells += blocks[i];
        }

    }

    public void calculatePermutations() {

        for (int i = 0; i < blacks.size(); i++) {
            int cellLocation = blacks.get(i);
            cells[cellLocation] = true;
        }

        permutations.clear();

        int noOfBlocks = blocks.length;
        int dividers = noOfBlocks - 1;
        int freeSpace = cells.length - (totalCells + dividers);
        int numberOfBits = noOfBlocks + dividers + freeSpace;
        int largestBinaryNumber = (int) Math.pow(2, numberOfBits);

        for (int sample = 0; sample < largestBinaryNumber; sample++) {
            int hasConsecutiveBits = (sample & (sample << 1));
            if (hasConsecutiveBits == 0) {
                if (Integer.bitCount(sample) == noOfBlocks) {

                    // valid permutation, no consecutive blocks and the right number of blocks
                    String binary = Integer.toBinaryString(sample);

                    // make valid length
                    while (binary.length() < numberOfBits) {
                        binary = "0" + binary;
                    }

                    Permutation permutation = new Permutation(binary);

                    boolean satisfiesHints = true;

                    for (int black : blacks) {
                        if (permutation.cells[black] != true) {
                            satisfiesHints = false;
                            break;
                        }
                    }

                    for (int white : whites) {
                        if (permutation.cells[white] != false) {
                            satisfiesHints = false;
                            break;
                        }
                    }

                    if (satisfiesHints) {
                        permutation.validate();
                        permutations.add(permutation);
                    }

                }
            }
        }

        if (permutations.size() > 0) {

            /**
             * Represent each permutation as a binary value and then perform a
             * bitwise AND on them all. If any bits remain ! at the end then we
             * know that no matter what permutation is used, this bit is locked.
             */
            int andResult = permutations.get(0).numbericValue;

            for (Permutation permutation : permutations) {
                andResult = andResult & permutation.numbericValue;
                if (andResult == 0) {
                    break;
                }
            }

            if (andResult > 0) {

                // there are some mutually consistent bits set to 1, we can lock them into the list of hints
                String binary = Integer.toBinaryString(andResult);

                // make valid length
                while (binary.length() < 25) {
                    binary = "0" + binary;
                }

                for (int i = 0; i < binary.length(); i++) {
                    if (binary.charAt(i) == '1') {
                        addBlack(i);
                    }
                }
            }

            int orResult = 0;

            for (Permutation permutation : permutations) {
                orResult = orResult | permutation.numbericValue;

            }
            String binary = "";

            binary = Integer.toBinaryString(orResult);

            // make valid length
            while (binary.length() < 25) {
                binary = "0" + binary;
            }

            String test = binary;

            // there are some mutually consistent bits set to 0, we can lock them into the list of hints
            for (int i = 0; i < binary.length(); i++) {
                if (binary.charAt(i) == '0') {
                    addWhite(i);
                }
            }

        }

        permutationCount = permutations.size();
    }

    public void addBlack(int black) {
        if (!blacks.contains(black)) {
            blacks.add(black);
        }
    }

    public void addWhite(int white) {
        if (!whites.contains(white)) {
            whites.add(white);
        }
    }

}
