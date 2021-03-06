import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


class Tree {
    static class Node {
        long key;
        long numL = 0;
        long numR = 0;
        long dif;
        Node left;
        Node right;
        public Node(long key) {
            this.key = key;
        }
    }
    private Node root;
    TreeMap <Long, Set<Long>> map = new TreeMap<Long, Set<Long>>();
    public void Insert(long x) {
    	if(Find(root, x) != null)
    		return;
        root = Paste(root, x);
    }
     
    private static Node Paste(Node node, long x) {
        if (node == null) {
            return new Node(x);
        }
        if(x < node.key){
        	node.numL ++;	
        }
        else
        	node.numR ++;
        node.dif = Math.abs(node.numL - node.numR);
        if (x < node.key) {
            node.left = Paste(node.left, x);
        } else if (x > node.key) {
            node.right = Paste(node.right, x);
        }
        return node;
    }
    public boolean Search(long x){
    	return (Find(root, x) == null)?false:true;
    }
    private static Node Find(Node root, long x)
    {
        if(root == null)
            return null;
        if(root.key == x)
            return root;
        if(root.key > x){

            return Find(root.left, x);
        }
        else{
            return Find(root.right, x);
        }
    }
    public void Print(FileWriter file, boolean flag)throws Exception
    {
    	preorderPrint(root, file, flag);
    }
    private  void preorderPrint(Node root, FileWriter file, boolean flag)throws Exception
    {
        if (root == null)   
        {
           return;
        }
        if(flag == true){
        String str = Long.toString(root.key) +  "\n";
        file.write(str);
        }
    	//map.put(root.dif,root.key);
        if(map.containsKey(root.dif) == false)
        {
        	Set<Long> tmp = new TreeSet <Long>();
        	tmp.add(root.key);
        	map.put(root.dif, tmp);
        }
        else{
        	Set<Long> tmp = new TreeSet <Long>();
        	tmp = map.get(root.dif);
        	tmp.add(root.key);
        	map.replace(root.dif, tmp);
        }
        preorderPrint(root.left, file, flag);   
        preorderPrint(root.right, file, flag);  
    }
    public void Erase(long x)
    {
    	root = Remove(root, x);
    }
    private static Node Remove(Node root, long x)
    {
    	if(root == null)
    		return null;
    	if( x < root.key)
    	{ 
    		root.left = Remove(root.left, x);
    		return root;
    	}
    	if( x > root.key)
    	{
    		root.right = Remove(root.right, x);
    		return root;
    	}
    		if (root.left == null)
    			return root.right;
    		if(root.right == null)
    			return root.left;
    		else{
    			long min_key = FindMin(root.right).key;
    			root.key = min_key;
    			root.right = Remove(root.right, min_key);
    			return root;
    		}
    }
    private static Node FindMin(Node v)
    {
    	if(v.left != null)
    		return FindMin(v.left);
    	else
    		return v;
    }
}
public class Main_26 {



	public static void main(String [] args){
		try
		{
			FileReader file = new FileReader("tst.in");
			BufferedReader br = new BufferedReader (file);
			String str;
			Tree tree = new Tree();
			while((str = br.readLine()) != null)
			{
				long num = Long.parseLong(str);
				tree.Insert(num);
			}
			file.close();
			br.close();
			FileWriter out = new FileWriter("tst.out");
			tree.Print(out, false);
			long num = 0;
			for(long l : tree.map.get(tree.map.lastKey())){
		         num = l;
		       }
			 tree.Erase(num);
			tree.Print(out, true);
			out.close();
		}
		 catch(IOException ex)
		{
			 System.out.println(ex.getMessage());
		}
		catch(IllegalArgumentException ex)
		{
			 System.out.println(ex.getMessage());
		}
		catch(Exception ex)
		{
			 System.out.println(ex.getMessage());
		}
	}
}