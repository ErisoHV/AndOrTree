# AND-OR Tree
ADN-OR tree is an n-ary tree used to represent knowledge about groups of tasks to be executed to achieve some goal

1. AND-OR tree has two types of nodes: the AND nodes and OR nodes.
2. The tasks that are not composed of subtasks are known as atomic tasks.
3. An atomic task can be in two states: executed or unexecuted. 
Meanwhile, the status of a non-atomic task depends on the state of their children: 
if it is an AND node is executed if all child nodes are executed.
OR node is executed if at least one of their children has been done.

Task/nodes types and status:
<img src="resources/images/02.png?raw=true" height="250" >

AND-OR Tree example:
<img src="resources/images/01.PNG?raw=true" height="250" >

The project was developed with a java swing interface.

Java classes:

1. `Tree.java` A simple Tree structure, with inorder, postorder and preorder traversal.
2. `AndOrTree.java` extends tree and contains the AND-OR Tree structure. The node content is a String. 
3. `TreeStructureLoader.java` contains the AND-OR Tree uploaded from a file text through a levels route by **parenthetical notation**. This class is the intermediary between the AND-OR tree and the interface.
4. `TreeWindow.java` is a `JFrame` that contains the canvas where the tree is drawn
5. `TreeCanvas.java` is the canvas where the tree is drawn, is a JPanel. AND tasks are represented by an oval, the OR tasks are represented with a rectangle
6. `UIInterface.java` the interface...  
