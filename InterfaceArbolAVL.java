import java.util.Iterator;

public interface InterfaceArbolAVL<T extends Comparable<T>> {
    public boolean isEmpty();
    public int size();
    public NodoAVL<T> find(T elem);


}
