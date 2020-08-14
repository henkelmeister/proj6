package structures;


public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;


  @Override
  public void add(T t) {
    // TODO: Implement the add() method
		if (t == null) throw new NullPointerException();
    upperBound++;
    double l = Math.log((double)3/2);
    double k = (double)2/3;
		BSTNode<T> newNode = new BSTNode<T>(t, null, null);
		root = addToSubtree(root, newNode);
		if ((Math.log(upperBound) / l) < height()) {
			BSTNode<T> child = newNode;
			BSTNode<T> temp = newNode.parent;
			while (k >= (double)subtreeSize(child)/ subtreeSize(temp)) {
				temp = temp.parent;
				child = child.parent;
			}
			ScapegoatTree<T> sTree = new ScapegoatTree<T>();
			sTree.root = temp;
			BSTNode<T> originalPrnt = temp.parent;
			sTree.balance();
			if (originalPrnt.getLeft() == temp){
        originalPrnt.setLeft(sTree.root);
      }else {
        originalPrnt.setRight(sTree.root);
      }
		}
  }

  @Override
  public boolean remove(T element) {
    // TODO: Implement the remove() method
    if(element == null) throw new NullPointerException(); 

    if(contains(element)){
      root = removeFromSubtree(root,element);
    }
    if(2*size() > upperBound){
      this.balance(); 
      upperBound = size(); 
    }
    return contains(element);
  }
}
