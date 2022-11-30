import java.util.ArrayList;
import java.util.List;

public class test{
    int[][] trie;
    int[] cnt;
    int idx = 0;
    boolean flag = false;
    int[] dx={-1,1,0,0};
    int[] dy={0,0,-1,1};
    public test(){
        trie = new int[100010][26];
        cnt = new int[100010];
    }
    public void insertTrie(String s){
        int p=0;
        for(int i=0;i<s.length();i++){
            int temp = s.charAt(i)-'a';
            if(trie[p][temp]==0)
                trie[p][temp] = ++idx;
            p = trie[p][temp];
        }
        cnt[p]++;
    }
    public boolean findW(String s){
        if(s.length()==0)
            return false;
        int p=0;
        for(int i=0;i<s.length();i++){
            int temp = s.charAt(i)-'a';
            if(trie[p][temp]==0)
                return false;
            p = trie[p][temp];
        }
        if(cnt[p]>0){
            flag = true;
            cnt[p]--;
        }

        return true;
    }
    public void dfs(int row, int col, char[][] board, List<String> res, String path){
        if(row<0||row>=board.length||col<0||col>=board[0].length||board[row][col]=='#')
            return;
        char temp = board[row][col];
        path+=temp;
        if(findW(path)){
            if(flag)
                res.add(path);
            flag = false;
        }
        else
            return ;
        board[row][col]='#';
        for(int i=0;i<4;i++){
            int curRow=row+dx[i];
            int curCol=col+dy[i];
            dfs(curRow,curCol,board,res,path);
        }
        board[row][col]=temp;
    }
    public static void main(String[] args) {
        char[][] board={{'o','a','b','n'},{'o','t','a','e'},{'a','h','k','r'},{'a','f','l','v'}};
        String[] words = {"oa","oaa",};
        List<String> res = new ArrayList<String>();
        test t = new test();
        for(String x:words)
            t.insertTrie(x);
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                String path="";
                t.dfs(i,j,board,res,path);
            }
        }
        for(String x:res){
            System.out.println(x);
        }
    }
}