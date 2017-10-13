package api.utils;

/**
 * Created by barakm on 14/10/2017
 */
public class Triple<A, B, C> {

    private A left;
    private B middle;
    private C right;

    public Triple(A left, B middle, C right) {
        this.left = left;
        this.right = right;
        this.middle = middle;
    }

    public A getLeft() {
        return left;
    }

    public void setLeft(A left) {
        this.left = left;
    }

    public B getMiddle() {
        return middle;
    }

    public void setMiddle(B middle) {
        this.middle = middle;
    }

    public C getRight() {
        return right;
    }

    public void setRight(C right) {
        this.right = right;
    }
}
