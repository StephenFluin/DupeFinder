package com.mortalpowers.dupefinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Hashtable;
import java.util.Vector;

public class DupeFinder {
	public static void main(String[] args) {
		String folder = "D:\\Images\\";
		
		print("starting in " + folder);
		try{
			digest = MessageDigest.getInstance("MD5");
		} catch(Exception e) {
			e.printStackTrace();
		}
		File dir = new File(folder);
		
		Vector<File> fileList1 = new Vector<File>();
		
		//Hashtable<Long,File> sizeList = new Hashtable<Long,File>();
		Hashtable<String,File> hashList = new Hashtable<String,File>();
		Vector<Pair<File,File>> dupes = new Vector<Pair<File,File>>();
		
		addToList(fileList1, dir);
		print("Found " + fileList1.size() + " files  to scan.");
		print("Calculating md5 hashes...");
		int i = 0;
		int fsize = 0;
		String s;
		for(File f : fileList1) {
			//print("Scanning " + f.getName());
			fsize = hashList.size();
			s = hash(f);
			if(hashList.containsKey(s) && !f.getName().equals("desktop.ini")) {
				dupes.add(new Pair<File,File>(f, hashList.get(s)));
				
			} else {
				//print(sizeList.toString());
				i++;
				if(i % 1024 == 0) {
					System.out.print(i + ",");
				}
				
			}
			hashList.put(s, f);
			/*if(sizeList.size() == fsize) {
				print("Problem, key didn't add to hash size.");
				print(sizeList.toString());
				print(f.toString());
				if(sizeList.containsKey(2828)) {
					print("contains 2828, strange.");
				} else {
					print("doesn't contain 2828, strange");
				}
				break;
			}*/
		}
		String message;
		BufferedReader stdin = new BufferedReader
	      (new InputStreamReader(System.in));
		i = 0;
		try {
			print("Done scanning files, inserted " + i + " keys:" + hashList.keySet().size());
			print("\n\nDupes:");
			for(Pair<File,File> p : dupes) {
				++i;
				//print("Dupe: " + p.left + "   (" + ++i + "/" + dupes.size() + ")");
				//print("      " + p.right);
			    //System.out.print ("Delete which one?  (''/1 means top, 2 means bottom, n/3 means none, q means quit:");
			    
			    //System.out.flush();
				
			    //message = stdin.readLine();
				message = "2";
			    
			    if(message.equals("1") || message.equals("")) {
			    	p.left.delete();
			    } else if(message.equals("2")) {
			    	p.right.delete();
			    } else if(message.equals("q")) {
			    	break;
			    } else if(message.equals("b")) {
			    	p.left.delete();
			    	p.right.delete();
			    }
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		print(i + " dupes deleted.");
		
	}
	public static MessageDigest digest;
	public static String hash(File f) {
					
		byte[] buffer = new byte[8192];
		int read = 0;
		try {
			InputStream is = new FileInputStream(f);	
			while( (read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}		
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			//print("MD5: " + bigInt.toString(16));
			return bigInt.toString(16);
			
		}
		catch(IOException e) {
			throw new RuntimeException("Unable to process file for MD5", e);
		}
		
	}
	
	public static void addToList(Vector<File> list, File dir) {
		//		Find subdirs
		for(File file : dir.listFiles()) {
			if(file.isDirectory()) {
				addToList(list, file);
			} else {
				list.add(file);
			}
		}
	}
	
	public static void print(String s) {
		System.out.println(s);
	}
}
