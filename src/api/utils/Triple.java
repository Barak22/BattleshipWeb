package api.utils;

/**
 * Created by barakm on 14/10/2017
 */
public class Triple<A, B, C> {

    private final A left;
    private final B middle;
    private final C right;

    public Triple(A left, B middle, C right) {
        this.left = left;
        this.right = right;
        this.middle = middle;
    }

    public A getUserName() {
        return left;
    }

    public B getMessage() {
        return middle;
    }

    public C getTime() {
        return right;
    }
}