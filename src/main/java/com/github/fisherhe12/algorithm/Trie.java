package com.github.fisherhe12.algorithm;

/**
 * 实现一个字典树(LeeCode-208)
 *  fisher
 */
public class Trie {

	TrieNode root;

	/** Initialize your data structure here. */
	public Trie() {
		root = new TrieNode();
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.insert("apple");

	}

	/** Inserts a word into the trie. */
	public void insert(String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			char currentChar = word.charAt(i);
			if (!node.containsKey(currentChar)) {
				node.put(currentChar, new TrieNode(currentChar));
			}
			node = node.get(currentChar);
		}
		node.setEnd();

	}

	// search a prefix or whole key in trie and
	// returns the node where search ends
	private TrieNode searchPrefix(String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			char curLetter = word.charAt(i);
			if (node.containsKey(curLetter)) {
				node = node.get(curLetter);
			} else {
				return null;
			}
		}
		return node;
	}


	// Returns if the word is in the trie.
	public boolean search(String word) {
		TrieNode node = searchPrefix(word);
		return node != null && node.isWord();
	}

	public boolean startsWith(String prefix) {
		TrieNode node = searchPrefix(prefix);
		return node != null;
	}

	class TrieNode {
		private TrieNode[] children;
		private boolean isWord;
		private char val;

		public TrieNode() {
			children = new TrieNode[26];
		}

		public TrieNode(char val) {
			children = new TrieNode[26];
			this.val = val;
		}

		public void setEnd() {
			isWord = true;
		}

		public boolean isWord() {
			return isWord;
		}

		public boolean containsKey(char ch) {
			return children[ch - 'a'] != null;
		}

		public TrieNode get(char ch) {
			return children[ch - 'a'];
		}

		public void put(char ch, TrieNode node) {
			children[ch - 'a'] = node;
		}

	}
}
