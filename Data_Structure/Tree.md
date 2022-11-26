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

### 1. Principle

- A tree with BST property:
  - For every node X in the tree:
    - Every key in the left subtree is less than X's key
    - Every key in the right subtree is greater than X's key
    - **The value of each node shouldn't be duplicated**

### 2. Baisc Operations

#### 2.1 Search

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

#### 2.2 Insert

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

#### 2.3 Deletion

- Three Cases：

  - Deletion key has no child
    - Directly delete the node

                50                            50
             /     \         delete(20)      /   \
            30      70       --------->    30     70 
           /  \    /  \                     \    /  \ 
         20   40  60   80                   40  60   80
  - ~ has one child
    - Directly connect left/right child nodes

                50                            50
             /     \         delete(30)      /   \
            30      70       --------->    40     70 
              \    /  \                          /  \ 
              40  60   80                       60   80
  - ~ has two child
    - **Find inorder successor of the node.**

                50                            60
             /     \         delete(50)      /   \
            40      70       --------->    40    70 
                   /  \                            \ 
                  60   80                           80

[450. Delete Node in a BST](https://leetcode.cn/problems/delete-node-in-a-bst/)

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
    public TreeNode findMin(TreeNode root){
        if(root.left == null)
            return root;
        return findMin(root.left);
    }
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root == null)
            return null;
        if(key>root.val){
            root.right = deleteNode(root.right,key);
        }else if(key<root.val){
            root.left = deleteNode(root.left,key);
        }else{
            if(root.left == null && root.right == null){
                return null;
            }
            if(root.left !=null && root.right == null){
                return root.left;
            }
            if(root.right !=null && root.left == null){
                return root.right;
            }
            if(root.left!=null && root.right !=null){
                TreeNode temp = findMin(root.right);
                root.val = temp.val;
                root.right=deleteNode(root.right,temp.val);
                return root;
            }
        }
        return root;
    }
}
```

- Ps: What is what is successor and processor in BST inorder traverse?

```java
public int successor(TreeNode root){
  root = root.right;
  while(root.left!=null)
    root = root.left;
  return root.val;
}
```

```java
public int processor(TreeNode root){
  root = root.left;
  while(root.right!=null)
    root = root.right;
  return root.val;
}
```

#### 2.4 Time Complexity

The worst case time complexity of Search / Insertion / Delete operation is O(h) where h is the height of the Binary Search Tree. In worst case, we may have to travel from the root to the deepest leaf node. The height of a skewed tree may become n and the time complexity of the operations may become O(n).

### 3. Ohther Operations

[235. Lowest Common Ancestor of a Binary Search Tree](https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val<p.val && root.val<q.val)
            root  = lowestCommonAncestor(root.right,p,q);
        if(root.val>q.val&&root.val>p.val)
            root  = lowestCommonAncestor(root.left,p,q);
        return root;
    }
}
```

[98. Validate Binary Search Tree](https://leetcode.cn/problems/validate-binary-search-tree/)

- Inorder traversal can determine whether a tree is a BST

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
    boolean flag = true;
    TreeNode pre = null;
    public void midBrowse(TreeNode root){
        if(root.left !=null)
            midBrowse(root.left);
        if(pre!=null&&pre.val>=root.val)
            flag = false;
        pre = root;
        if(root.right !=null)
            midBrowse(root.right);

    }
    public boolean isValidBST(TreeNode root) {
        midBrowse(root);
        return flag;
    }
}
```

[230. Kth Smallest Element in a BST](https://leetcode.cn/problems/kth-smallest-element-in-a-bst/)

- **By using inorder traversal, we can access the values of the binary tree from small to large **

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
    int count;
    int res = 0;
    public void browse(TreeNode root){
        if(root==null)
            return ;
        browse(root.left);
        count--;
        if(count==0)
            res = root.val;
        browse(root.right);
    }
    public int kthSmallest(TreeNode root, int k) {
        count = k;
        browse(root);
        return res;
    }
}
```

[99. Recover Binary Search Tree](https://leetcode.cn/problems/recover-binary-search-tree/)

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

/*
This question has two conditions:
if the sequnce is:
1 [6] 3 4 [5] 2 7
There are two postions dissatisfy the BST property eg.6>3,5>2
if the sequnce is:
1 [3] [2] 4 5 6 7
Only one postion dissatisfy the BST property
So in this question, we need to find the first node(only judge once) and second node(judge one or twice). And then change the val;
*/

class Solution {
    TreeNode pre = null;
    boolean flag = true;
    TreeNode first  = null;
    TreeNode second = null;
    public void inorderTraversal(TreeNode root){
        if(root==null)
            return;
        inorderTraversal(root.left);
        if(pre!=null && pre.val>root.val){
            if(flag){
                first = pre;
                flag = false;
            }
            second = root;
        }
        pre = root;
        inorderTraversal(root.right);
    }
    public void recoverTree(TreeNode root) {
        inorderTraversal(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }
}
```

