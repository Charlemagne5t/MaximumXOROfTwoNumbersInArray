class Solution {
    public int findMaximumXOR(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int max = nums[0];
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            max = Math.max(max, nums[i]);
        }

        int length = (int) (Math.log(max) / Math.log(2)) + 1;

        Trie trie = new Trie();
        for (int i = 0; i < n; i++) {
            trie.insert(Integer.toBinaryString(nums[i]), i, length);
        }
        int maxXOR = 0;
        for (int i = 0; i < n; i++) {
            int index = trie.search(Integer.toBinaryString(nums[i]), length);
            maxXOR = Math.max(nums[i] ^ nums[index], maxXOR);
        }

        return maxXOR;
    }

}

class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode('0');
    }

    public void insert(String word, int index, int maxLength) {
        int wordLength = word.length();
        int diff = maxLength - wordLength;
        TrieNode temp = root;
        for (int i = 0; i < maxLength; i++) {
            char ch = 'p';
            if (i < diff) {
                ch = '0';
            } else ch = word.charAt(i - diff);
            if (ch == '0') {
                if (temp.zero == null) {
                    TrieNode node = new TrieNode(ch);
                    temp.zero = node;
                }
                temp = temp.zero;
            } else {
                if (temp.one == null) {
                    TrieNode node = new TrieNode(ch);
                    temp.one = node;
                }
                temp = temp.one;
            }

            if (i == diff + wordLength - 1) {
                temp.isTerminal = true;
                temp.index = index;
            }
        }

    }

    public int search(String word, int maxLength) {
        TrieNode temp = root;
        int wordLength = word.length();
        int diff = maxLength - wordLength;
        for (int i = 0; i < maxLength; i++) {
            char ch = 'p';
            if (i < diff) {
                ch = '1';
            } else {
                ch = word.charAt(i - diff);
                if (ch == '1') {
                    ch = '0';
                } else ch = '1';
            }
            if (ch == '0') {
                if (temp.zero != null) {
                    temp = temp.zero;
                } else temp = temp.one;
            } else {
                if (temp.one != null) {
                    temp = temp.one;
                } else temp = temp.zero;
            }

        }
        return temp.index;
    }

}

class TrieNode {
    char value;
    TrieNode one;
    TrieNode zero;
    boolean isTerminal;
    int index = -1;

    public TrieNode(char value) {
        this.value = value;
        this.isTerminal = false;
    }
}
