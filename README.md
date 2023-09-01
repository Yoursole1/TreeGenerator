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