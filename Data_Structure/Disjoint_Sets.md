# Content

[toc]

## Disjoint Sets

### 1.Principle

#### 1.1 Why we use it?

- We use Disjoint Sets data structure for solving the "Dynamic Connectivity" problem.

#### 1.2 The basic operations of Disjoint Sets

- Connect(x,y): Connects x and y
- isConnected(x,y):Return true if x and y are connected. Connections can be transitive,i.e. thet don't need to be direct.

- Useful for many purposes:
  - Percolation theory: Computational chemistry
  - Implementation of other algorithms:Kruskal's algorithm

#### 1.3 Representation:

```java
// The Disjoint Sets Interface
public DisjointSets{
  int[] parent;
  int[] size;
	public DisjointSets(int n){
    this.parent = new int[n];
    this.size = new int[n];
    for(int i=0;i<n;i++){
      parent[i] = i;
      size[i] = 0;
    }
  }
  //find root of x
  //path compression
  public int find(int x){
    if(parent[x]!=x)
    	parent[x] = find(parent[x]);
    return parent[x];
  }
  //Union two nodes
  public void connect(int x,int y){
    if(find(x)!=find(y)){
   	/*
    We defined that connect tree with smaller depth to the tree with larger depth in order to reduce the time
    complexity
     */
      if(size[x]>size[y]){
        parent[y] = x;
      }else if(size[y]>size[x]){
        parent[x] = y;
      }else{
        parent[y] = x;
        size[x]++
      }
    }
  }
  public boolean isConnected(int x,int y){
    return find(x)==find(y);
  }
}
```

- The naive approach:
  - Connect two things:Record every single connection line in some data structure.
  - Checking connectedness:Do some sort of iterations over the lines to check if one thing can be reached from the other
- The drawback of naive approach: The cost of searching is very high when the volume of data is very large
- **Better approach**: Model connectedness in terms of sets.
  - Only need to keep track of which connected component each item belongs to.

### 2.Applications

[684. Redundant Connection](https://leetcode.cn/problems/redundant-connection/)

```java
class Solution {
    public class Disjoint_sets{
        int[] parent;
        int[] size; 
        public Disjoint_sets(){
            this.parent = new int[1010];
            this.size = new int[1010];
            for(int i=1;i<1010;i++){
                parent[i] = i;
                size[i] = 0;
            }
        }
        public int find(int x){
            if(parent[x]!=x)
                parent[x] = find(parent[x]);
            return parent[x];
        }
        public void Connect(int x,int y){
            int rootx = find(x);
            int rooty = find(y);
            if(rootx!=rooty){
                if(size[rootx]>size[rooty]){
                    parent[rooty] = rootx;
                }else if(size[rooty]>size[rootx]){
                    parent[rootx] = rooty;
                }else{
                    parent[rooty] = rootx;
                    size[rootx]++;
                }
            }
        }
        public boolean isConnected(int x,int y){
            return find(x)==find(y);
        }
    }
    public int[] findRedundantConnection(int[][] edges) {
        Disjoint_sets s = new Disjoint_sets();
        for(int[] x:edges){
            if(s.isConnected(x[0],x[1]))
                return x;
            else{
                s.Connect(x[0],x[1]);
            }
        }
        int[] res = new int[0];
        return res;
    }
}
```

[827. Making A Large Island](https://leetcode.cn/problems/making-a-large-island/)

```java
class Solution {
    public class Disjoint_sets{
        int[] parent;
        int[] size;
        int[] rank;
        int col =0;
        int row = 0;
        public Disjoint_sets(int row,int col){
            parent = new int[row*col];
            size = new int[row*col];
            rank = new int[row*col];
            this.col = col;
            this.row = row;
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    parent[i*col+j] = i*col+j;
                    size[i*col+j] =1;
                    rank[i*col+j] = 0;
                }
            }
        }
        public int find(int x){
            if(parent[x]!=x){
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        public void Connect(int x1,int y1,int x2,int y2){
            int rootx = find(x1*col+y1);
            int rooty = find(x2*col+y2);
            if(find(rootx)!=find(rooty)){
                if(rank[rooty]<rank[rootx]){
                    parent[rooty] = rootx;
                    size[rootx] += size[rooty];
                }
                else if(rank[rootx]<rank[rooty]){
                    parent[rootx] = rooty;
                    size[rooty] += size[rootx];
                }
                else{
                    parent[rooty] = rootx;
                    size[rootx] += size[rooty];
                    rank[rootx]++;
                }
            }
        }
        }
    public int largestIsland(int[][] grid) {
        int res = 0;
        int row = grid.length;
        int col = grid[0].length;
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,1,-1};
        Disjoint_sets s = new Disjoint_sets(row,col);
        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++)
                if(grid[i][j]==1)
                    s.size[i*col+j] = 1;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(grid[i][j]==1){
                    s.size[i*col+j] = 1;
                    for(int d=0;d<4;d++){
                        int nx = i+dx[d];
                        int ny = j+dy[d];
                        if(nx>=0&&nx<row&&ny>=0&&ny<col&&grid[nx][ny]==1)
                            s.Connect(i,j,nx,ny);
                    }
                }
            }
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                int temp = 1;
                if(grid[i][j]==0){
                    Set<Integer> set = new HashSet<>();
                    for(int k=0;k<4;k++){
                        int r = i+dx[k];
                        int c = j+dy[k];
                        if(r>=0&&r<row&&c>=0&&c<col&&grid[r][c]==1){
                            int val = r*s.col+c;
                            int root = s.find(val);
                            if (set.contains(root)) continue;
                            temp+=s.size[root];
                            set.add(root);
                        }
                    }
                }else if(grid[i][j]==1){
                    temp = s.size[s.find(i*j+j)];
                }
                if(temp>res)
                    res = temp;
            }
        }
        return res;
    }
}
```

