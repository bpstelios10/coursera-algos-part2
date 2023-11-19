# Algorithms from Coursera, part 2

## Week 1 - wordNet
To run tests of week1 assignment, from commandline, you need to go to week1-wordnet/build folder and run:
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
To run tests of week2 assignment, from commandline, you need to go to week2-seam/build folder and run:
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
To run tests of week3 assignment, from commandline, you need to go to week3-baseball/build folder and run:
```shell
java -classpath :../../.lift/algs4.jar BaseballElimination ../teams8.txt
```
and from intellij run (make sure that working directory is coursera-algos-part2):
BaseballElimination::main with program arguments "week2-seam/teams8.txt"
