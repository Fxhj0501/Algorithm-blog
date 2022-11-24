# Content

[toc]

## Tree

### 1.1 Principle

- A tree consists of :
  - A set of nodes
  - A set of edges that connect those nodes.
    - Constraint: There is exactly one path between any tow nodes

![IMG_0772](../imgs/IMG_0772.PNG)

## Binary Search Trees

### 1.1 Principle

- A tree with BST property:
  - For every node X in the tree:
    - Every key in the left subtree is less than X's key
    - Every key in the right subtree is greater than X's key
    - **The value of each node shouldn't be duplicated**

### 1.2 Baisc Operations

#### 1.2.1 Search

[700. Search in a Binary Search Tree](https://leetcode.cn/problems/search-in-a-binary-search-tree/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode searchBST(TreeNode root, int val) {
        if(root == null || root.val == val)
            return root;    
        root = val>root.val?searchBST(root.right,val):searchBST(root.left,val);
        return root;
    }
}
```

#### 1.2.2 Insert

- Serach for a key:
  - if not found:
    - Create new node 
    - Set appropriate link
  - if found:
    - return node

[701. Insert into a Binary Search Tree](https://leetcode.cn/problems/insert-into-a-binary-search-tree/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root == null){
            TreeNode temp = new TreeNode(val);
            return temp;
        }
        if(val<root.val)
            root.left = insertIntoBST(root.left,val);
        else if(val > root.val)
            root.right = insertIntoBST(root.right,val);
        return root;
    }
}
```

