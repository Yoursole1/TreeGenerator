**Tree Generator For RND Forest Generation**

This project defines a tree using a few parameters:

    - Spread: The angle of split between branches
    - Split: The rate at which the trunk and branches split
            (S split is defined as one branch turning into two)
    - Branch: The rate at which branches split off
            (A branch is defined as a node coming from between
            two nodes)
    - Variability: Multiplied within the standard deviation
                to change the variability of the tree from 
                base parameters
    - Branch height: **NOT IMPL**, will define height at which
                    branching starts. 
<img width="470" alt="Screenshot 2024-01-16 at 20 31 23" src="https://github.com/Yoursole1/TreeGenerator/assets/80355246/a0da88f8-b8f6-4ae4-b7bf-b913879cfc0b">

The generator works in layers, implemented by the GeneratorLayer class.
These layers pass the TreeSkeleton along as it gets a trunk, then branches, 
then leaves generated. The trunk is generated first, then locations are
selected for branches.  Branches are the same as the trunk, but are rotated
and offset onto the trunk.  
