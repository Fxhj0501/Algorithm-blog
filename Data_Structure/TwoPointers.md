# Two Pointers & Sliding window

[toc]

## Content

## 1. Tow Pointers

### 1.1 Principle

- Why we use two pointers:
  - ==save time==
- moving ways:
  - same diraction
  - opposite diraction
- **How to solve problems**:
  - Initalize two pointers
  - move the first pointer(pointer i)
  - move the second pointer(pointer j) until met the requirement & update the result

## 1.2 Template

### 1.2.1 Get the longest/shortest substring

```java
//1.longest- get the first valid range
int j = 0;
for (int i = 0; i < n; i++) {
	while (不满足条件) {
		j += 1;
  }
	res = Math.max()
  
}
//2.shortest - get the last valid range
int j = 0;
for (int i = 0; i < n; i++) {
	while (满足条件) {
  	res = Math.min()
		j += 1;
  }
}
```

### 1.2.2  Get at least/most k distinct characters

```java
//1. at least k distinct characters
int res = 0;
int left = 0;
for (int i = 0; i < n; i++) {
	while (state is vaild) { 
		left += 1;
  } // 循环到不满足搭配为止
	res += left;  //left 前面都是满足的子数组
}

//2.at most k distinct characters
int res = 0;
int left = 0;
for (int i = 0; i < n; i++) {
	while (state is not vaild) { 
		left += 1;
  } // 循环到满足搭配为止
	res += i - left + 1; //从 left 到 i 都是满足的子数组
}
```

## 1.3 Patterns

### 1.3.1 find the longest or mininum area

#### [3. Longest Substring Without Repeating Characters](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if(s.length()==0)
            return 0;
        int[] window = new int[500];
        int res = 0;
        int j=0;
        for(int i=0;i<s.length();i++){
            window[s.charAt(i)]++;
            while(window[s.charAt(i)]>1){
                window[s.charAt(j)]--;
                j++;
            }
            res = i-j+1>res?i-j+1:res;
        }
        return res;
    }
}
```

#### [424. Longest Repeating Character Replacement](https://leetcode-cn.com/problems/longest-repeating-character-replacement/)

```java
class Solution {
    public boolean isvalid(String s,int i,int j,int k,int[] cnt){
        int maxn = 0;
        for(int idx=0;idx<cnt.length;idx++)
            maxn = Math.max(maxn,cnt[idx]);
        int range = i-j+1;
        int diff = k+maxn;
        if(range<=diff)
            return true;
        return false;
    }
    public int characterReplacement(String s, int k) {
        int res = 0;
        int[] cnt = new int[30];
        int j = 0;
        for(int i=0;i<s.length();i++){
            int tmp = s.charAt(i)-'A';
            cnt[tmp]++;
            while(!isvalid(s,i,j,k,cnt)){
                cnt[s.charAt(j)-'A']--;
                j++;
            }
            res =  Math.max(res,i-j+1);
        }
        return res;
    }
}
```

- **The following method only focuses on the state when the longest interval occurs.** It is a more in-depth understanding of sliding windows.

```java
class Solution {
    public int characterReplacement(String s, int k) {
        int[] cnt = new int[30];
        int len = 0;
        int left = 0;
        int max = 0;
        for(int i=0;i<s.length();){
            int temp = s.charAt(i)-'A';
            cnt[temp]++;
            i++;
            max = Math.max(max,cnt[temp]);
          // in this way, we can make sure that the length of range will not decrease
          // in other word, we just want to find the longest range. In this way, we can ignore those smaller 							range
            if(i-left>max+k){
                cnt[s.charAt(left)-'A']--;
                left++;
            }
            len = Math.max(len,i-left);
        }
        return len;
    }
}
```

#### [76. Minimum Window Substring](https://leetcode.cn/problems/minimum-window-substring/)

```java
class Solution {
    public String minWindow(String s, String t) {
        String res="";
        int te = 200010;
        HashMap<Character,Integer> hash1 = new HashMap<>();
        HashMap<Character,Integer> hash2 = new HashMap<>();
        int valid = 0;
        int len = t.length();
        for(int i=0;i<t.length();i++)
            hash2.put(t.charAt(i),hash2.getOrDefault(t.charAt(i),0)+1);
        int left = 0;
        int size = hash2.size();
        for(int i=0;left<s.length();++left){
            while(i<s.length()&&valid<size){
                char temp = s.charAt(i);
                hash1.put(temp,hash1.getOrDefault(temp,0)+1);
                if(hash1.get(temp)==hash2.get(temp))
                    valid++;
                i++;
            }
            if(valid==size&&i-left<te){
                res = s.substring(left,i);
                te = res.length();
            }
            hash1.put(s.charAt(left),hash1.getOrDefault(s.charAt(left),0)-1);
            if(hash2.keySet().contains(s.charAt(left))&&hash1.get(s.charAt(left))<hash2.getOrDefault(s.charAt(left),0))
                valid--;
        }
        return te == 200010?"":res;
    }
}
```

#### [438. Find All Anagrams in a String](https://leetcode.cn/problems/find-all-anagrams-in-a-string/)

```java
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<Integer>();
        int valid = 0;
        int left = 0;
        HashMap<Character,Integer> hash1 = new HashMap<>();
        HashMap<Character,Integer> hash2 = new HashMap<>();
        for(int i=0;i<p.length();i++){
            char temp = p.charAt(i);
            hash2.put(temp,hash2.getOrDefault(temp,0)+1);
        }
        int len = hash2.size();
        for(int i=0;left<s.length();left++){
            while(i<s.length()&&valid<len){
                char temp = s.charAt(i);
                hash1.put(temp,hash1.getOrDefault(temp,0)+1);
                if(hash1.get(temp)==hash2.get(temp))
                    valid++;
                i++;
            }
            if(i-left==p.length()&&valid==len)
                res.add(left);
            char tmp = s.charAt(left);
            hash1.put(tmp,hash1.getOrDefault(tmp,0)-1);
            if(hash2.keySet().contains(tmp)&&hash1.getOrDefault(tmp,0)<hash2.getOrDefault(tmp,0))
                valid--;
        }
        return res;
    }
}
```

### 1.3.2 Pattern2: Get the number of subarray with the restriction --- at most k or at least k

#### [713. Subarray Product Less Than K](https://leetcode.cn/problems/subarray-product-less-than-k/)

```java
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int res = 0;
        int mul = 1;
        int left = 0;
        for(int i=0;i<nums.length;i++){
            mul *= nums[i];
            while(left<=i&&mul>=k){
                mul /= nums[left];
                left++;
            }
            res += i-left+1;
        }
        return res;
    }
}
```

#### [1375 · Substring With At Least K Distinct Characters](https://www.lintcode.com/problem/1375/)

```java
public class Solution {
    public long kDistinctCharacters(String s, int k) {
        // Write your code here
        if (s == null || s.length() == 0 || s.length() < k) return 0;
        int total = 0;
        int[] count = new int[128];
        long res = 0;
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (count[s.charAt(i)]++ == 0) total++;
            while (total >= k) {
                if (--count[s.charAt(j)] == 0) total--;
                j++;
            }//一直到不满足退出
            res += j * 1L;
        }
        return res;
    }
}
```

[15. 3Sum](https://leetcode.cn/problems/3sum/)

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) return new ArrayList<>(res);
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int low = i + 1;
            int high = nums.length - 1;
            while(low < high) {
                if (nums[i] + nums[low] + nums[high] == 0) {
                    res.add(Arrays.asList(nums[i], nums[low], nums[high]));
                    while (low < high && nums[low] == nums[low + 1]) low++;
                    while (low < high && nums[high] == nums[high - 1]) high--;
                    low++;
                    high--;
                } else if (nums[i] + nums[low] + nums[high] < 0) {
                    low++;         
                } else {  
                    high--; 
                }
            }
        }
        return res;
    }
}
```



### 1.3.3 Pattern3: Opposite direction

#### [11. Container With Most Water](https://leetcode-cn.com/problems/container-with-most-water/)

```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length-1;
        int res = 0;
        while(left<right){
            int temp = (right-left)*Math.min(height[right],height[left]);
            res = temp>res?temp:res;
            if(height[right]>height[left])
                left++;
            else
                right--;
        }
        return res;
    }
}
```

#### [75. Sort Colors](https://leetcode.cn/problems/sort-colors/)

```java
class Solution {
    public void swap(int[] nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
        return ;
    }
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        for(int i = 0;i<=right;){
            if(nums[i]==0){
                swap(nums,i,left);
                i++;
                left++;
            }else if(nums[i]==2){
                swap(nums,i,right);
                right--;
            }else
                i++;
        }
        return;
    }
}
```

### 1.3.4 Pattern4: Combined with linked list

- In this pattern, we usually use **fast & slow pointer**

#### [19. Remove Nth Node From End of List](https://leetcode.cn/problems/remove-nth-node-from-end-of-list/)

- Remove the last $n^{th}$ node of linked list
  - Create a dummy node
  - Initialize two pointers, point to the dummy head
  - Let fast pointer advance `n` steps
  - Move both two pointers,when fast pointer reach the last node, break loop
  - Let slow pointer point to the next next node

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast  = dummy;
        for(int i=0;i<n;i++)
            fast = fast.next;
        while(fast.next!=null){
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }
}
```

#### [141. Linked List Cycle](https://leetcode.cn/problems/linked-list-cycle/)

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow)
                return true;
        }
        return false;
    }
}
```

#### [142. Linked List Cycle II](https://leetcode.cn/problems/linked-list-cycle-ii/)

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                fast = head;
                while(fast!=slow){
                    fast = fast.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }
}
```

