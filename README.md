# Algorithms from Coursera, part 2

## Week 1 - wordNet
To run tests of week1 assignment, from commandline, you need to go to [week1-wordnet/build](week1-wordnet/build) folder and run:
```shell
java -classpath :../../.lift/algs4.jar WordNet ../synsets3.txt ../hypernyms3.txt
java -classpath :../../.lift/algs4.jar SAP ../digraph1.txt
java -classpath :../../.lift/algs4.jar Outcast ../synsets.txt ../hypernyms.txt ../outcast5.txt ../outcast8.txt ../outcast11.txt
```
and from intellij run (make sure that working directory is coursera-algos-part2):
WordNet::main with program arguments "week1-wordnet/synsets3.txt week1-wordnet/hypernyms3.txt"
Outcast::main with program arguments "week1-wordnet/synsets.txt week1-wordnet/hypernyms.txt week1-wordnet/outcast5.txt week1-wordnet/outcast8.txt week1-wordnet/outcast11.txt"
SAP::main with program arguments "week1-wordnet/digraph1.txt"

## Week 2 - Seam Carver
To run tests of week2 assignment, from commandline, you need to go to [week2-seam/build](week2-seam/build) folder and run:
```shell
java -classpath :../../.lift/algs4.jar SeamCarver ../10x12.png
java -classpath :../../.lift/algs4.jar SeamCarverTest ../6x5.png
java -classpath :../../.lift/algs4.jar AcyclicWeightedVertexShortestPath
```
and from intellij run (make sure that working directory is coursera-algos-part2):
SeamCarver::main with program arguments "week2-seam/10x12.png"
SeamCarverTest::main with program arguments "week2-seam/6x5.png"
AcyclicWeightedVertexShortestPath::main

## Week 3 - Baseball
To run tests of week3 assignment, from commandline, you need to go to [week3-baseball/build](week3-baseball/build) folder and run:
```shell
java -classpath :../../.lift/algs4.jar BaseballElimination ../teams4a.txt
java -classpath :../../.lift/algs4.jar BaseballEliminationTest ../teams5b.txt
```
and from intellij run (make sure that working directory is coursera-algos-part2):
BaseballElimination::main with program arguments "week3-baseball/teams4a.txt"
BaseballEliminationTest::main with program arguments "week3-baseball/teams5b.txt"

## Week 4 - Boggle
To run tests of week4 assignment, from commandline, you need to go to [week4-boggle/build](week4-boggle/build) folder and run:
```shell
java -classpath :../../.lift/algs4.jar BoggleSolverTest ..
java -classpath :../../.lift/algs4.jar BoggleSolverManualTest ..
# and for a game of boggle in GUI with 5 rows and 6 cols (if no numbers specified, a 4x4 board is created)
java -classpath :../../.lift/algs4.jar BoggleGame ".." 5 6
```
and from intellij run (make sure that working directory is coursera-algos-part2):
BoggleSolverTest::main with program arguments "week4-boggle"
BoggleSolverManualTest::main with program arguments "week4-boggle"
BoggleGame::main with program arguments week4-boggle 5 6
NOTE: if it fails with error for GraphicsEnvironment, you should use `java` directly from your jdk/bin path

## Week 5 - Burrows
To run tests of week5 assignment, from commandline, you need to go to [week5-burrows/build](week5-burrows/build) folder and run:
```shell
# abra.txt to test MoveToFront encode (result is in assignment page)
# complexTestCases.txt for testing decode and more complex cases
cat ../abra.txt | java -classpath :../../.lift/algs4.jar MoveToFront - | java -classpath :../../.lift/algs4.jar edu.princeton.cs.algs4.HexDump 16
cat ../aomplexTestCases.txt | java -classpath :../../.lift/algs4.jar MoveToFront - | java -classpath :../../.lift/algs4.jar MoveToFront +
java -classpath :../../.lift/algs4.jar CircularSuffixArray
# abra.txt to test BurrowsWheeler encode (result is in assignment page)
# complexTestCases.txt for testing decode and more complex cases
cat ../abra.txt | java -classpath :../../.lift/algs4.jar BurrowsWheeler - | java -classpath :../../.lifedu.princeton.cs.algs4.HexDump 16
cat ../complexTestCases.txt | java -classpath :../../.lift/algs4.jar BurrowsWheeler - | java -classpath :../../.lift/algs4.jar BurrowsWheeler +
```
From intellij it is trickier. For encoding, it is possible to provide StdIn from file, but for decoding i didnt try it. So:
MoveToFront::main with program argument "-" and _**redirect input from**_ (in run configuration) point to abra.txt
CircularSuffixArray::main
BurrowsWheeler::main with program argument "-" and _**redirect input from**_ (in run configuration) point to abra.txt
