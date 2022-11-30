package Disjoint_Sets;

import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

public class Disjoint_sets {
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
                size[i*col+j] =0;
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
    public boolean isConnected(int x1,int y1,int x2,int y2){
        int rootx = x1*y1+y1;
        int rooty = x2*y2+y2;
        return find(rootx)==find(rooty);
    }
    public static void main(String[] args) {
        int [][] grid = {{0,0},{0,0}};
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
                        if(set.isEmpty())
                            temp = 0;
                    }
                }else if(grid[i][j]==1){
                    temp = s.size[s.find(i*j+j)];
                }
                if(temp>res)
                    res = temp;
            }
        }
        System.out.println(res);
    }
    }

//    int[] parent;
//    int[] size;
//    public Disjoint_sets(int n){
//        this.parent = new int[n+1];
//        this.size = new int[n+1];
//        for (int i=1;i<=n;i++){
//            parent[i] = i;
//            size[i] = 0;
//        }
//    }
//    public int find(int x){
//        if(parent[x]!=x)
//            parent[x] = find(parent[x]);
//        return parent[x];
//    }
//    public boolean isConnected(int x,int y){
//        return find(x)==find(y);
//    }
//    public void Connect(int x,int y){
//        int rootx = find(x);
//        int rooty = find(y);
//        if(rootx!=rooty){
//            if(size[find(y)]<size[find(x)]){
//                parent[rooty] = rootx;
//            }else if(size[find(x)]<size[find(y)]){
//                parent[rootx] = rooty;
//            }else{
//                size[rootx] +=1;
//                parent[rooty] = rootx;
//            }
//        }
//    }



//        Disjoint_sets s = new Disjoint_sets(10);
//        s.Connect(1,2);
//        s.Connect(2,3);
//        s.Connect(1,4);
//        s.Connect(1,5);
//        s.Connect(6,7);
//        s.Connect(1,6);
//        System.out.println(s.size[1]);
//        int [][] edges = {{1,0},{0,1}};
//        int row = edges.length;
//        int col = edges[0].length;
//        Queue<Integer> q = new LinkedList<Integer>();
//        q.offer(3);
//        System.out.println(q.poll());