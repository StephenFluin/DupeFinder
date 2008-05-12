package com.mortalpowers.dupefinder;

public class Pair<A,B> {
	
	A left;
	B right;
	
	public Pair(A a, B b) {
		left = a;
		right = b;
	}
	public String toString() {
		return "<" + (left != null ? left.toString() : "") + "," + (right != null ? right.toString() : "") + ">";
	}
	public boolean equals (Pair p) {
		return (p.left.equals(left) && p.right.equals(right));
	}
	
}
