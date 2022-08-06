# Tree-width Approximation
An implementation of Robertson-Seymour tree-width 4-approximation algorithm.  
Also, an example of practical usage of tree decompositions in form of a dynamic algorithm for solving Steiner Tree problem.  

An example usage is showcased in Program.java class.
In the example, a Steiner Problem instance is read form a file provided in program's first argument, then an attempt is made to calculate decomposition of width 7, 11, 15, 19, 23. If any succeeds, the decomposition is used to solve the problem. The decomposition and solved Steiner instance are displayed and result is saved at path given in second argument.

Displayed by preview program:  
![Preview](src/main/resources/images/prefuse_example.bmp "Red vertices indicate terminals, red edges indicate the Steiner Tree")  

**Input file format:**
```
<vertex_amount> <edge_amount>  
<edge_1_first> <edge_1_second>   
...  
<edge_n_first> <edge_n_second>  
<terminals_amount>  
<terminal_1>  
...  
<terminal_n>
```
  
**Output file format:**
```
<solution_edges_amount>
<edge_1_first> <edge_1_second>   
...  
<edge_k_first> <edge_k_second>
```

Feel free to experiment, although be wary of high computation time for more complex instances.