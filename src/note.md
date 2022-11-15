

# 算法基础课（配合Leetcode食用）

[toc]

# 第一章 基础算法

## 快速排序

- 思想是基于分治思想
  - 确定分界点
    - 假设区间是[l,r]，则分界点取[l+r>>1]
  - ==根据规则调整区间，小于分界点的在一侧，其余的在另一侧== 
  - 递归处理左右两端
- 不稳定排序

```cpp
void quick_sort(int q[],int l,int r){
  if(l>=r)
    return;
  int i=l-1,j=r+1,x=q[l];
  while(i<j){
    do i++;while(q[i]<x);
    do j--;while(q[j]>x);
    if(i<j)
      	swap(q[i],q[j]);
    else
      break;
  }
  quick_sort(q,l,j);
  quick_sort(q,j+1,r);
}
```

## 归并排序

- 找分界点
  - 这里使用中点：`mid = l+r>>1`
- 先递归排序
- 归并到一个数组
  - 双指针，分别指向两个区间头部
  - 比较指向的数，较小的放到归并的数组，指针后移，继续比较
  - 当一个指针走到一个区间尽头以后，将剩下的序列直接插到最后的归并数组中
- 稳定排序

```cpp
void merge_sort(int q[],int l, int r){
  if(l>=r)
    	return ;
  int mid = l+r>>1;
  //先递归划分区间
  merge_sort(q,l,mid);
  merge_sort(q,mid+1,r);
  int k=0,i=l,j=mid+1;
  //合并两个区间
  while( i<=mid && j<=r){
    if(q[i]<q[j])
      tmp[k++]=q[i++];
  	else
      tmp[k++]=q[j++];
  }
  //左右两个区间剩余部分放入合并的数组中
  while(i<=mid)
    tmp[k++]=q[i++];
  while(j<=r)
    tmp[k++]=q[j++];
  for(i=l,j=0;i<=r;i++,j++)
    	q[i]=tmp[j];
    
}
```

## 二分法

- 二分的本质：有一个边界，使得可以将整个区间一分为二。因此二分的题目并不一定需要有单调性。
- 步骤（假设符合条件的全部放到分界点的右侧）：
  - `mid=l+r+1>>1`
  - `check mid`（看是否符合分界条件）：
    - 符合：则更新区间为`[mid,r]`
    - 不符合：则更新区间为`[l,mid-1]`

``` cpp
bool check_mid(int x){
  /*...*/
}
void binary(int q[],int l, int r){
  while(l<r){
    int mid=(l+r+1)>>1;
    if(check_mid(q[mid])){
      l=mid;
    }else
      r=mid-1;
  }
}
```

```cpp
//浮点数二分
//例如手写求一个数的平方根
double binary(double x){
  double l=0,r=x;
  while(r-l>1e-6){
    double mid=(l+r)/2
    if(x>=mid*mid){
      l=mid;
    }else
      r=mid;
  }
  return l;
}

```

## 高精度

- 只有cpp会涉及这个问题，java和python是没有这个问题的
- 存储一个大整数：
  - 每一位存在数组中
  - 存储时，数组下标0存储整数的个位（方便进位）

### 高精度加法

`C=A+B A>=0 B>=0`

```cpp
vector<int> add(vector<int> &A, vector<int> &B) {
    if (A.size() < B.size()) return add(B, A);
    
    vector<int> C;
    int t = 0;
    for (int i = 0; i < A.size(); i ++ ) {
        t += A[i];
        if (i < B.size()) t += B[i];
        C.push_back(t % 10);
        t /= 10;
    }
    
    if (t) C.push_back(t);
    return C;
}
```

### 高精度减法

`C = A - B, 满足A >= B, A >= 0, B >= 0`

```cpp
vector<int> sub(vector<int> &A, vector<int> &B) {
    vector<int> C;
    for (int i = 0, t = 0; i < A.size(); i ++ ) {
        t = A[i] - t;
        if (i < B.size()) t -= B[i];
        C.push_back((t + 10) % 10);
        if (t < 0) t = 1;
        else t = 0;
    }

    while (C.size() > 1 && C.back() == 0) C.pop_back();
    return C;
}
```

### 高精度除以低精度

`C = A * b, A >= 0, b > 0`

```cpp
vector<int> mul(vector<int> &A, int b) {
    vector<int> C;
    int t = 0;
    for (int i = 0; i < A.size() || t; i ++ ) {
        if (i < A.size()) t += A[i] * b;
        C.push_back(t % 10);
        t /= 10;
    }
    
    return C;
}
```

### 高精度除以低精度

`A / b = C ... r, A >= 0, b > 0`

```cpp
vector<int> div(vector<int> &A, int b, int &r) {
    vector<int> C;
    r = 0;
    for (int i = A.size() - 1; i >= 0; i -- )
    {
        r = r * 10 + A[i];
        C.push_back(r / b);
        r %= b;
    }
    reverse(C.begin(), C.end());
    while (C.size() > 1 && C.back() == 0) C.pop_back();
    return C;
}
```

## 前缀和

- 一维前缀和

  - `s[i]=a[1]+a[2]+...+a[i]`

    `a[l]+...a[r]=s[r]-s[l-1]`

    `s[0]=0`

  - 注意元素下标是从1开始，这样可以定义出`s[0]`

- 作用：只用`O(1)`的复杂度就能求出数组中一段区间的和

- 二维前缀和

  - `s[i][j]=s[i-1][j]+s[i][j-1]-s[i-1][j-1]+a[i][j]`

## KMP

- 当有一个待处理的字符串`S[N]`和一个模版串`p[M]`，我们需要进行匹配

```cpp
//朴素做法
bool get_p(String s,String p){
  for(int i=1;i<=s.length();i++){
    bool flag = true;
    for(int j=1;j<=p.length();j++){
      if(s[i+j-1]!=p[j]){
        flag = false;
        break;
      }
    }
  }
  return flag;
}
```

- 朴素做法每次匹配失败之后需要在下一个元素重新开启匹配。

- 为了减少重复性工作，我们对模版串进行预处理。

  - 建立`next`数组：`next[i]`表示以`i`开始的后缀和以`1`开始的前缀相等，并且后缀的长度最长。

    即`next[i]=j`表示 `p[1~j]=p[i-j+1,i]`

  - 因此，假设模版`p[j]`与字符串`s[i]`不匹配，并且`next[j]=k`，那么只需要从模版串`p[k+1]`与`s[i]`开始匹配即可。（==此处是一个迭代的过程==）

```cpp
const int N 100010;
int ne[N];
bool kmp(string s,string p){
  int n = s.size();
  int m = p.size();
  if(m==0)
    	return 0;
 	s.insert(s.begin(),' ');
  p.insert(p.begin(),' ');
  //ne从2开始处理，1的时候肯定是0不用管
  for(int i=2,j=0;i<=m;i++){
    while(j && p[i]!=p[j+1])
      	j = ne[j];
   	if(p[i]==p[j+1])
      j++;
    ne[i] = j;
  }
  for(int i=1,j=0;i<=n;i++){
    while(j && s[i]!=p[j+1])
      j = ne[j];
    if(s[i]==p[j+1])
      j++;
    //匹配成功
    if(j==m)
      return true;
  }
  return false;
}
```



## 双指针

```cpp
for(int i=0,j=0;i<n;i++){
  while(j<i&&check(i,j))
    j++;
  //具体问题的逻辑
}
```

- 对于一个序列，用两个指针维护一段区间
- 对于两个序列，维护某种次序，比如归并排序中合并两个有序序列的操作
- 核心特点就是将暴力遍历序列(两层循环)的`O(n^2)`的复杂度降为`O(n)`

## 位运算

- n的2进制表示中第k位是几

  - `n>>k&1`

- 返回n的最后一位：

  - `lowbit`：返回x的最后一位1。例如x=10100，`lowbit(x)=100`

  - ```cpp
    int lowbit(int x){
      return x&-x;
    }
    ```

  - 

  - 是树状数组的一个基本操作

# 第三章 搜索与图论

## 深度优先搜索（DFS）

- 全排列问题

```cpp
//假设全排列n个数
const int N = 100010;
bool st[N];
int res[N];
int n;
void dfs(int index){
  if(index == n){
    for(int i=0;i<n;i++){
      cout<<res[i]<<" ";
    	cout<<endl;
    	return;
    }
    for(int j=1;j<=n;j++){
      if(!st[j]){
       	res[index] = j;
        st[j] = true;
        dfs(index+1);
        st[j] = false;
      }
}
int main(){
  cin>>n;
  dfs(0);
  return 0;
}
```

- 八皇后问题
- 自制模版（主要是根据题目描述来找return时的边界）

```cpp
int grid[m][n];//要搜索的目标
bool visit[m][n];//记录
void dfs(int row,int col){
	if(check)
		return;
  visit[row][col] = true;
  dfs(row-1,col);
  dfs(row+1,col);
  dfs(row,col-1);
  dfs(row,col+1);
  visit[row][col] = false;
}
```

## 广度优先搜索（BFS）

- 具有最短性
- 一般来说配合`queue`一起使用
- 二叉树的广度优先搜索，按照每层进行搜索
- 下面是层序遍历二叉树

```cpp
queue<int> path;
void bfs(treenode* root){
  path.push(root);
  while(!path.empty()){
    int size = path.size();
    while(size){
      treenode* cur = path.front();
    	path.pop();
      if(cur->left != nullptr)
        	path.push(cur->left);
      if(cur->right != nullptr)
        	path.push(cur->right);
      size--;
    }
  }
}
```

- 走迷宫类问题。找到最短路径

```cpp
//“+”表示墙，“.”表示可以走的路，entrance是出发点的坐标，最后走到迷宫边缘就行
class Solution {
public:
    int a[4] = {-1,1,0,0};
    int b[4] = {0,0,-1,1};
    bool flag = false;
    int nearestExit(vector<vector<char>>& maze, vector<int>& entrance) {
        int dis = 0;
        queue<pair<int,int>> path;
        path.push({entrance[0],entrance[1]});
        maze[entrance[0]][entrance[1]] = '+';
        while(!path.empty()){
            int size = path.size();
            while(size){
                int row = path.front().first;
                int col = path.front().second;
                path.pop();
                for(int i=0;i<4;i++){
                    int tempr = row+a[i];
                    int tempc = col+b[i];
                    if(tempr>=0 && tempr < maze.size() && tempc >=0 && tempc < maze[0].size() && maze[tempr][tempc]== '.'){
                        if(tempr == 0 || tempr == maze.size()-1 || tempc == 0 || tempc == maze[0].size()-1){
                            dis++;
                            return dis;
                        }

                        path.push({tempr,tempc});
                        maze[tempr][tempc] = '+';
                        flag = true;
                    }
                }
                size --;
            }
            if(flag){
                dis++;
                flag = false;
            }
        }
        //这里直接返回-1，是因为能直接走出去的已经return了，这里要么是直接没路，要么是在里面乱走一通然后出不去的
        return -1;

    }
};
```

## 树和图的存储

- 树是一种无环联通图

- 图：

  - 有向图
    - 邻接矩阵g[a,b]:表示a->b的边
    - 邻接表（链表）：
      - 有几个点就开几条链表

  ```cpp
  const int N=100010,M=N*2;
  int h[N],e[M],ne[M],idx=0;
  //添加a到b的一条边
  //h[a]相当于是每个点的链表的头
  //e[idx]记录到达的点，ne[idx]表示下条边的索引
  void add(int a,int b){
    e[idx] = b,ne[idx] = h[a],h[a] = idx++;
  }
  //查询a这个点相关的边
  void browse(int a){
    for(int i=h[a];i!=-1;i=ne[i]){
      cout<<e[i];
    }
  }
  //初始化
  int main(){
    memset(h,-1,sizeof h);
  }
  ```

  

  - 无向图
    - 对于一条边，建立a->b和b->a的边的表示,相当于双向的存有向图。即`add(a,b)`和`add(b,a)`即可。

## 拓扑排序

- - 针对有向图
  - 拓扑序列：若一个由图中所有的点构成的序列A满足：对于图中的每条边(x,y),x在A中都出现在y之前，则称A是该图的一个拓扑序列。
  - 有环就无拓扑序列。
  - 有向无环图一定有一个拓扑序列。因为一个有向无环图一定存在一个入度为0的点

  ```cpp
  queue <- 所有入度为0的点
  while(!queue.empty()){
    auto t = queue.front();
    queue.pop();
    枚举出t的所有出边j{
      //数组d是用来记录一个点的入度数量的
      删掉t->j,d[j]--;
    	if(d[j]==0){
        queue.push(j);
      }  
    }
  }
  ```

  

- 例题

  给定一个长度为 n 的整数数组 nums ，其中 nums 是范围为 [1，n] 的整数的排列。还提供了一个 2D 整数数组 sequences ，其中 sequences[i] 是 nums 的子序列。
  检查 nums 是否是唯一的最短 超序列 。最短 超序列 是 长度最短 的序列，并且所有序列 sequences[i] 都是它的子序列。对于给定的数组 sequences ，可能存在多个有效的 超序列 。

  例如，对于 sequences = [[1,2],[1,3]] ，有两个最短的 超序列 ，[1,2,3] 和 [1,3,2] 。
  而对于 sequences = [[1,2],[1,3],[1,2,3]] ，唯一可能的最短 超序列 是 [1,2,3] 。[1,2,3,4] 是可能的超序列，但不是最短的。
  如果 nums 是序列的唯一最短 超序列 ，则返回 true ，否则返回 false 。
  子序列 是一个可以通过从另一个序列中删除一些元素或不删除任何元素，而不改变其余元素的顺

```
输入：nums = [1,2,3], sequences = [[1,2],[1,3]]
输出：false
解释：有两种可能的超序列：[1,2,3]和[1,3,2]。
序列 [1,2] 是[1,2,3]和[1,3,2]的子序列。
序列 [1,3] 是[1,2,3]和[1,3,2]的子序列。
因为 nums 不是唯一最短的超序列，所以返回false。

输入：nums = [4,1,5,2,6,3], sequences = [[5,2,6,3],[4,1,5,2]]
输出：true
```

```cpp
class Solution {
public:
    int e[100010],ne[100010],h[100010];
    int idx = 0;
    int st[100010];
  //构建有向图
    void add(int a, int b){
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
    bool sequenceReconstruction(vector<int>& nums, vector<vector<int>>& sequences) {
        memset(h,-1,sizeof (h));
        memset(st,0,sizeof st);
        queue<int> res;
        queue<int> temp;
        //构建有向图
        for(auto x:sequences){
            for(int i=0;i<x.size()-1;i++){
                int a=x[i];
                int b=x[i+1];
                add(a,b);
                st[b]++;
            }
        }
        //找出入度为0的点
        for(int i=1;i<=nums.size();i++){
            if(st[i]==0){
                res.push(i);
                temp.push(i);
            }
        }
        //如果入度为0的点不为1或没有入度为0的点，则序列不存在
        if(res.size()>1 || res.size()==0)
            return false;
        //构造拓扑排序
        while(!res.empty()){
            int cur = res.front();
            res.pop();
            for(int i=h[cur];i!=-1;i=ne[i]){
                int j=e[i];
                st[j]--;
                if(st[j]==0){
                    res.push(j);
                    temp.push(j);
                }
            }
            if(res.size()>1)
                return false;
        }
        //如果序列中有点没加进来也不行      
        if(temp.size() == nums.size())
            return true;
        return false;
    }
};
```

## 最短路径

- 单源最短路径
  - 一个点到其他点的最短路径
  - 还可以分为两类：
    - 所有边的权值都是正数
      - 朴素Dijkstra：`O(n^2)`
      - 堆优化版的Dijkstra算法：`O(mlogn)` (其中n代表点的数量，m代表边的数量)
      - 稠密图用朴素版，稀疏图用堆优化版
      - 基于贪心算法
    - 存在某些边是负权值
      - Bellman-Ford：`O(nm)`
        - 基于动态规划
      - SPFA：对Bellman-Fold的优化
        - 一般来说是`O(m)`,最坏情况是`O(nm)`
- 多源汇短路径
  - 起点和终点不确定
  - 可能有多个起点和对应的终点
  - Floyd算法：`O(n^3)`
- ==最短路算法难点在建图==，从图中抽象出对应的算法

### Dijkstra算法

- Dijkstra应用场景中一定不能存在负权边

#### 朴素版

```cpp
//初始化距离,源点到自己的距离为0，到其他点的距离为正无穷
dis[1]=0,dis[其他点]=INT_MAX
//定义集合s代表当前已经确定最短距离的点
for(int i=0;i<n;i++){
	t<-不在s中的距离最近的点
  s<-t//把t并入到集合s中
  用t更新到其他点的距
}
```

```cpp
//假设有N个点，M条边
//求第k个点到第n个点的最短距离
int g[N][N];
int dist[N];
bool st[N];
int dijkstra(int k,int g[][],int n){
  memset(dist,0x3f3f3f,sizeof dist);
  memset(st,false,sizeof st);
  dist[k] = 0;
  for(int i=0,i<N;i++){
    int t=-1;
    for(int j=1;j<=N;j++){
      if(!st[j]&&(t==-1 || dist[t]>dist[j]))
        t=j;
    }
    dist[t] = true;
    for(int j=1;j<=N;j++){
      dist[j] = min(dist[j],(dist[t]+g[t][j]))
    }
  }
  if(dist[n]>0x3f)
  	return -1;
  return dist[n];
}
```

#### 堆优化的Dijkstra

- 由于用于稀疏图，因此使用临接表进行存储

```cpp
//假设有n个点m条边
//求第1个点到第n个点的最短距离
const int N=100010;
int e[N],w[N],ne[N],h[N];
int idx = 0;
int dist[N];
bool st[N];
typedef pair<int,int> PII:
void add(int a,int b,int c){
  e[idx] = b;
  w[idx] = c;
  ne[idx] = h[a];
  h[a] = idx++;
}
int dijkstra(){
  memset(dist,0x3f,sizeof dist);
  memset(h,-1,sizeof h);
  memset(st,false,sizeof st);
  priority_queue<PII,vector<PII>,greater<PII>> heap;
  heap.push({0,1});
  while(heap.size()){
    auto t = heap.top();
    heap.pop();
    int ver = t.second,distance = t.first;
    //堆优化的Dijkstra会存在冗余，因此这里如果出现冗余直接continue
    if(st[ver])
      continue;
    for(int i=h[ver];i!=-1;i++){
      int j=e[i];
      if(dist[j]>distance+w[i]){
        dist[j]+distance+w[i];
        heap.push({dist[j],j});
      }
    }
  }
  if(dist[n]==0x3f3f3f)
    	return -1;
  return dist[n];
}
int main(){
	while(m--){
	scanf("%d%d%d",&a,&b,&c);
	add(a,b,c);
	}
}
```

#### Bellman-Ford

```cpp
//基本上有限制，比如最多经过k条边之类的，否则出现负环就会无解
for k 次：
  for 所有边a,b,w
    dist[b] = min(dist[b],dist[a]+w)
```

# 第四章数学知识

## 质数

- 定义：在大于1的整数中，如果只包含1和本身这两个约数，就被称为质数。

- 判定：

  - 试除法（时间复杂度sqrt(N)）

  ```cpp
  bool is prime(int n){
    if(n<2)
      return false;
    for(int i=2;i<=n/i;i++){
      if(n%i==0)
        	return false;
    }
    return true;
  }
  ```

  - 分解质因数（时间复杂度`log(N)~sqrt(N)`）

    ```cpp
    void divide(int n)
    {
        for (int i = 2; i <= n; i++)
        {
            if (n % i == 0)
            {
                int s = 0;
                while (n % i == 0)
                {
                    n /= i;
                    s++;
                }
                cout << i << " " << s << endl;
            }
        }
        if (n > 1)
            cout << n;
    }
    ```

  - 埃氏筛法(时间复杂度`Nlog(log(N))`近似于`O(N)`)

    ```cpp
    const int N = 100010;
    bool st[N];
    int cnt;
    int primes[N];
    void get_primes(int n){
      for(int i=2;i<=n;i++){
        if(!st[i]){
          //记录质数的数组
          primes[cnt++] = i;
          //这里相当于把质数的倍数全筛出去
        	for(int j=i+i;j<=n;j+=i)
          	st[j] = true;
        }
      }
    }
    ```

  - 线性筛法

    - 核心思想：n只会被它的最小质因子筛掉（`O(N)`）

    ```cpp
    const int N = 100010;
    bool st[N];
    int cnt;
    int primes[N];
    void get_primes(int n){
      for(int i=2;i<=n;i++){
        if(!st[i]){
          //记录质数的数组
          primes[cnt++] = i;
          //这里相当于把质数的倍数全筛出去
        	for(int j=0;primes[j]<=n/i;j++){
            //这里用最小质因子去筛合数
            st[primes[j]*i] = true;
            //用最小质因子筛完以后，就没必要用其他质因子去筛了，因为那就会重复筛了
            //直接退出
            if(i%primes[j]==0)
              break;
          }
        }
      }
    
    ```

  

## 约数

- 试除法`O(N)`

  ```cpp
  void get_divisors(int n)
  {
      vector<int> res;
      for (int i = 2; i <= n / i; i++)
      {
          if (n % i == 0)
          {
              res.push_back(i);
              if (i != n / i)
                  res.push_back(n / i);
          }
      }
      sort(res.begin(), res.end());
      for (auto x : res)
          cout << x << " ";
  }
  ```

- 求一个数的约数个数

  - 若一个数可以通过分解质因数的形式变成如下表示：

    $N=p_1^{\alpha_1}\cdot p_2^{\alpha_2}\dots p_k^{\alpha_k}$

    则这个数的约数个数即为：$(\alpha_1+1)(\alpha_2+1)\dots(\alpha_k+1)$

- 求一个数的约数之和

  - 若一个数可以通过分解质因数的形式变成如下表示：

    $N=p_1^{\alpha_1}\cdot p_2^{\alpha_2}\dots p_k^{\alpha_k}$

    则这个数的约数之和可以表示为：$(p_1^0+p_1^1\dots+p_1^{\alpha_1})\dots+(p_k^0+p_k^1\dots+p_k^{\alpha_k})$

- 辗转相除法（欧几里得算法）

  ```cpp
  //求n对数的最大公约数
  #include<bits/stdc++.h>
  using namespace std;
  int n,a,b;
  int main() {
      cin >> n;
      while(n--){
          cin >> a >> b;
          cout << __gcd(a,b) << endl;//调用STL函数
      }
      return 0;
  }
  ```

  

# 第五章动态规划

## 背包问题

### 01背包

- `N`个物品，背包体积是`V`。第`i`个物品体积是$v_i$，价值是$w_i$，并且==每个物品只能用一次==。求背包能装物品的最大价值是多少。

```cpp
int f[N][V];
f[0][0] = 0;
for(int i=1;i<=N;i++){
  for(int j=1;j<=V;j++){
    	f[i][j] = f[i-1][j];
    	if(j>=v[i])
    		f[i][j] = max(f[i-1][j],f[i-1][j-vi]+wi);
  }
}
cout<<f[N][V];
```

```cpp
//简化版
int f[N];
for(int i=1;i<=n;i++){
  for(int j=V;j>=v[i];j--){
    f[j] = max(f[j],f[j-v[i]]+w[i]);
  }
}
cout<<f[V];
```



### 完全背包问题

- `N`个物品，背包体积是`V`。第`i`个物品体积是$v_i$，价值是$w_i$，并且==每个物品能用无限次==。求背包能装物品的最大价值是多少。

```cpp
//朴素版
#include<iostream>
using namespace std;
const int n = 1010;
int f[n][n];
int v[n],w[n];
int main()
{
    int N,V;
    cin>>N>>V;
    for(int i = 1 ; i <= N ;i ++)
    {
        cin>>v[i]>>w[i];
    }

    for(int i = 1 ; i<=N ;i++){
        for(int j = 0 ; j<=V ;j++){
            for(int k = 0 ; k*v[i]<=j ; k++){
                if(k*v[i]<=j)
                    f[i][j] = max(f[i][j],f[i-1][j-k*v[i]]+k*w[i]);
                else
                    f[i][j] = f[i-1][j];
            }
        }   
    }
    cout<<f[N][V]<<endl;
}


```

对于完全背包问题，就是在01背包的基础上，对每个物品进行个数的考虑，因此加入`k`维来考虑每个物品拿取时候的个数。因此可以下出下列的递推表达式：

`f[i][j] = max{f[i-1][j],f[i-1][j-v]+w,f[i-1][j-2v]+2w...f[i-1][j-k*v]+k*w}`

`f[i][j-v] =max{f[i-1][j-v],f[i-1][j-2v]+w...}`

通过观察上述表达式，可以简化代码，没有必要再循环`k`次，而是通过迭代实现对每个物品取`k`次的循环。

```cpp
//简化版
#include<iostream>
using namespace std;
const int n = 1010;
int f[n][n];
int v[n],w[n];
int main()
{
    int N,V;
    cin>>N>>V;
    for(int i = 1 ; i <= N ;i ++)
    {
        cin>>v[i]>>w[i];
    }

    for(int i = 1 ; i<=N ;i++){
        for(int j = 0 ; j<=V ;j++){
            f[i][j] = f[i-1][j];
            if(j>=v[i])
                f[i][j] = max(f[i][j],f[i][j-v[i]]+w[i]);
        }   
    }
    cout<<f[N][V]<<endl;
    return 0;
}

```

```cpp
//降维版，明天补充
```



### 多重背包

- `N`个物品，背包体积是`V`。第`i`个物品体积是$v_i$，价值是$w_i$，并且==每个物品个数不一样==。求背包能装物品的最大价值是多少。