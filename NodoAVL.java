public class NodoAVL<T extends Comparable <T>>  {

    //Declaracion
    T elem;
    NodoAVL <T> izq, der, papa;
    
    //factor de equilibrio
    int fe; 
    

    public NodoAVL(T elem){
        this.elem=elem;
        izq=null;
        der=null;
        fe=0;
    }

    //Getters y setters
    public T getElem() {
        return elem;
    }
    public void setElem(T elem) {
        this.elem = elem;
    }

    public NodoAVL<T> getIzq() {
        return izq;
    }
    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public NodoAVL<T> getDer() {
        return der;
    }
    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }
    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }

    public int getFe() {
        return fe;
    }
    public void setFe(int fe) {
        this.fe = fe;
    }

    //Funciones para colgar
    public void cuelgaDir(NodoAVL<T> n, char dir)
    {
        if( dir == 'I')
            izq = n;

        else if (dir == 'D')
            der = n;

        if (n!= null)
            n.setPapa(this);
    }

    public void cuelga(NodoAVL<T> n){
        if(n==null){
            return;
        }
        if(n.getElem().compareTo(elem)<=0){
            izq=n;
        }else{
            der=n;
        }
        n.setPapa(this);
    }

    public String toString() {
        // Devolvemos el elemento del nodo y su factor de equilibrio
        return "[ " + this.getElem().toString() + " ] (" + this.getFe() + ")";
    }


}
