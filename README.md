# GCHQSolver
An algorithm to solve GCHQ's Christmas 2015 grid puzzle

# How it works
The given puzzle is encoded into a json format that I devised. This allows the puzzle to be consumed by the program in its starting state. (See input.json)

The grid is represented by 2 arrays of Row classes (technically on of these is columns but the behaviour is identical). Each row is has a set of 'blocks' which are the required set of black cell groups in the row. Eahc row also has an array of 'blacks' and an array of 'whites'. These contain the known locations of black and white cells. Initially th whites are empty and the blacks are populated with the given hints in the puzzle json file.

The grid class then iterates over each row and column and generates every possible permutation of cells for that row. With each permutation it checks of it meets the rule requirements. If it does it is added to the set of valid permutations for that row. 

Once a row has a complete set of valid permutations we can potentially infer some infomation. Using bitwise logic to AND every row. If the resulting binary value has any 1's then we know that that cell is black for every permutation and so we can "lock" it into the blacks array as a solved cell. In a similar fashion we OR every permutation together. If the resulting value has any 0s still set then we know that that cell is white in every permutation and again we can "lock" it into the whites array as a solved cell. These "solved" cells are propagated to the corresponding opposite row or column.

We iterate over and over in this fashion, each time finding more solved cells and reducing the number of valid permutations. Eventually we reduce it down to just 1 valid permutation for each row. At this stage the grid is now considered solved.

# OUPUT:

totalCells: 339

totalCombinations: 25459524747161525811128614332055474322374482780009529344000000

totalCombinations: 43330643280443473920

totalCombinations: 768

totalCombinations: 4

totalCombinations: 1


1111111011100010101111111

1000001011011000001000001

1011101000001110101011101

1011101010011111101011101

1011101001111101101011101

1000001001100000001000001

1111111010101010101111111

0000000011100011100000000

1011011100101011101001011

1010000001110110000100010

0111101011110110100001100

0101000100010101111010111

0011001010100000011011111

0001110110110111111011101

1011111111101010011000010

0110100110001101110000010

1110101010010000111110100

0000000010001101100011111

1111111010011000101010111

1000001011001001100011010

1011101000111100111110010

1011101011101111111111011

1011101010011111101111110

1000001001100000010101100

1111111011000101100011111


FINISH


This renders as a QR code containing the following URL:
http://www.gchq.gov.uk/puzz/
