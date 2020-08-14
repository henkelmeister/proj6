package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) {
    // TODO: Implement the contains() method
    return get(t) != null;
  }

  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    if(node == null) throw new NullPointerException(); 
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    // TODO
    if (t == null) throw new NullPointerException();
    return getHelper(t,root);
  }

  private T getHelper(T t, BSTNode<T> node){
    if(node == null)return null;
    
    if(t.compareTo(node.getData()) == 0){
      return node.getData(); 
    } 
    else if(t.compareTo(node.getData()) < 0){
      return getHelper(t,node.getLeft());
    } 
    else{
      return getHelper(t,node.getRight()); 
    }
  }


  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    // TODO: Implement the getMinimum() method
    if (root == null) return null; 

    return getMinimumHelper(root);
  }

  private T getMinimumHelper(BSTNode<T> node){
      if(node.getLeft() == null){
        return node.getData(); 
      } 
      else{
        return getMinimumHelper(node.getLeft()); 
      }
  }


  @Override
  public T getMaximum() {
    // TODO: Implement the getMaximum() method
    if(root == null) return null; 
    
    return getHighestValue(root); 
  }

  


  @Override
  public int height() {
    // TODO: Implement the height() method
    if(root == null) return -1; 
    
    return maxDepth(root) - 1;
  }

  private int maxDepth(BSTNode<T> node){
    if(node == null) return 0;
    else{
      int lDepth = maxDepth(node.getLeft());
      int rDepth = maxDepth(node.getRight());

      if(lDepth > rDepth){
        return (lDepth + 1);
      }
      else{
        return (rDepth + 1); 
      }

    }
  }


  public Iterator<T> preorderIterator() {
    // TODO: Implement the preorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue,root);
    return queue.iterator();
  }

  private void preorderTraverse(Queue<T> queue, BSTNode<T> node){
    if(node != null){
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue,node.getRight()); 
    }
  }


  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }


  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue,root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue,BSTNode<T> node){
    if(node != null){
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData()); 
    }

  }

  @Override
  public boolean equals(BSTInterface<T> other) {
    // TODO: Implement the equals() method
    return equalsHelper(root, other.getRoot());
  }

  private boolean equalsHelper(BSTNode<T> t1,BSTNode<T> t2){
    if(t1 == null && t2 == null){
      return true; 
    }
    else if(t1 == null || t2 == null){
      return false; 
    }
    else{
      if(!(t1.getData().compareTo(t2.getData()) == 0)) return false; 
      return equalsHelper(t1.getLeft(),t2.getLeft()) && equalsHelper(t1.getRight(),t2.getRight()); 
    }
  }


  @Override
  public boolean sameValues(BSTInterface<T> other) {
    // TODO: Implement the sameValues() method
    Iterator<T> iterator1 = this.inorderIterator();
    Iterator<T> iterator2 = other.inorderIterator(); 
    while(iterator1.hasNext() && iterator2.hasNext()){
      if((iterator1.next().compareTo(iterator2.next()) != 0)) return false; 
    }

    return !iterator1.hasNext() && !iterator2.hasNext();
  }

  @Override
  public boolean isBalanced() {
    // TODO: Implement the isBalanced() method
    return Math.pow(2,height()) <= size() && size() < Math.pow(2,height() + 1);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void balance() {
    // TODO: Implement the balanceHelper() method
    Iterator<T> iterator = this.inorderIterator(); 
    T[] a = (T[]) new Comparable[size()];
    int i=0;
    while(iterator.hasNext()){
      a[i] = iterator.next(); 
      i++; 
    } 
    root = arrToBST(a,0,a.length-1);
  }

  private BSTNode<T> arrToBST(T[] a,int low,int high){
    if(low > high) return null;

    int middle = (low + high)/2;
    BSTNode<T> newNode = new BSTNode<T>(a[middle],arrToBST(a,low,middle - 1),arrToBST(a,middle + 1,high));
    return newNode; 
  }
 


  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
