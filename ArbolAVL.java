import java.util.LinkedList;
import java.util.Queue;

public class ArbolAVL<T extends Comparable<T>> implements InterfaceArbolAVL<T> {
    private NodoAVL<T> raiz = null;

    private int contador = 0;

    public int size(){
        return contador;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public NodoAVL<T> getRaiz(){
        return raiz;
    }

    public String toString() {
        return ImprimirArbol.getTreeDisplay(raiz);
    }

    public void imprimirPorNiveles() {
        if (raiz == null)
        {
            System.out.println("El árbol está vacío.");
            return;
        }
    
        Queue<NodoAVL<T>> cola = new LinkedList<>();
        cola.add(raiz);
    
        while (!cola.isEmpty())
        {
            int nodosNivelActual = cola.size(); // Numero de nodos en el nivel actual.
            StringBuilder nivelInfo = new StringBuilder(); // Informacion de todos los nodos en el nivel actual.
    
            for (int i = 0; i < nodosNivelActual; i++)
            {
                NodoAVL<T> nodo = cola.poll();
    
                nivelInfo.append("[" + nodo.getElem()).append("] (FE: ").append(nodo.getFe()).append(")");
    
                // Agregamos hijos a la cola para ser procesados en las proximas iteraciones.
                if (nodo.getIzq() != null)
                {
                    cola.add(nodo.getIzq());
                }
    
                if (nodo.getDer() != null)
                {
                    cola.add(nodo.getDer());
                }
    
                // Añadir un espacio entre los nodos del mismo nivel.
                if (i < nodosNivelActual - 1)
                {
                    nivelInfo.append(" | ");
                }
            }
    
            System.out.println(nivelInfo);
        }
    }

    public NodoAVL<T> find(T elem) {
        return find(raiz, elem);
    }

    private NodoAVL<T> find(NodoAVL<T> actual, T elem) {
        if (actual == null)
            return null;

        if (elem.equals(actual.elem))
            return actual;

        if (elem.compareTo(actual.elem) < 0 )
            return find(actual.getIzq(), elem);
        return find(actual.getDer(), elem);
    }

    public int altura(NodoAVL<T> actual){
        if(actual == null)
            return 0;

        else
        {
            int uno = altura(actual.getIzq());
            int dos = altura(actual.getDer());
            return Math.max(uno, dos) + 1;
        }
    }

    private int calcularFe(NodoAVL<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        int alturaIzquierda = altura(nodo.getIzq());
        int alturaDerecha = altura(nodo.getDer());

        return alturaDerecha - alturaIzquierda;
    }

    public void checarFe(NodoAVL<T> actual) {
        NodoAVL<T> papa;

        while (actual != raiz) {
            papa = actual.getPapa();
            int fePapa = calcularFe(papa);
            papa.setFe(fePapa);

            if (fePapa == 2 || fePapa == -2) {
                papa = rota(papa);
            }

            actual = papa;
        }

        int feRaiz = actual.getFe();
        if (actual == raiz && (feRaiz == 2 || feRaiz == -2)) {
            raiz = rota(actual);
        }
    }

    public void inserta(T elem) {
        NodoAVL<T> nuevo = new NodoAVL(elem);

        if(raiz == null)
        {
            // Si esta vacio, el nuevo elemento se convierte en la raiz
            raiz = nuevo;
            contador++;
            return;
        }
        else if (find(elem) == null)
            inserta(elem, nuevo);
    }

    private void inserta(T elem, NodoAVL<T> nuevo) {
        NodoAVL<T> actual = raiz;
        //Se baja hasta encontrar la posicion del nodo
        NodoAVL<T> aux = null;
        while (actual != null)
        {
            aux = actual;
            if (elem.compareTo(actual.getElem()) > 0)
                actual = actual.getDer();

            else
                actual = actual.getIzq();
        }

        // Insertamos el nuevo nodo en esa posicion
        if (elem.compareTo(aux.getElem()) > 0)
            aux.setDer(nuevo);
        else
            aux.setIzq(nuevo);

        contador++;
        nuevo.setPapa(aux);

        checarFe(nuevo);
    }

    public void borra(T elem) {
        // Buscar el nodo con el elemento proporcionado.
        NodoAVL<T> actual = find(elem);

        // Si el nodo no se encuentra, se termina la ejecucion.
        if (actual == null) return;

        contador--;
        NodoAVL<T> papa = actual.getPapa();

        NodoAVL<T> izq = actual.getIzq();
        NodoAVL<T> der = actual.getDer();


        // Si el nodo es una hoja (no tiene hijos).
        if (izq == null && der == null)
        {
            // Si el nodo es la raiz.
            if (actual == raiz)
                raiz = null;

            else
            {
                if (papa.getIzq() != null && papa.getIzq().equals(actual))
                    papa.setIzq(null);
                else
                    papa.setDer(null);

                checarFe(actual);
            }
            return;
        }

        // Si el nodo tiene un solo hijo.
        NodoAVL<T> hijo = (izq != null) ? izq : der;

        if (izq == null || der == null)
        {
            // Si el nodo es la raiz.
            if (actual == raiz)
            {
                raiz = hijo;
                raiz.setPapa(null);
            }
            else
            {
                papa.cuelga(hijo);
                checarFe(hijo);
            }
            return;
        }

        // Si el nodo tiene dos hijos, encontrar el sucesor inorden.
        NodoAVL<T> sucesor = actual.getDer();
        while (sucesor.getIzq() != null)
            sucesor = sucesor.getIzq();


        NodoAVL<T> papaSuc = sucesor.getPapa();
        actual.setElem(sucesor.getElem());

        // Se remplaza el nodo actual con el sucesor inorden.
        if (papaSuc != raiz) {
            if (sucesor.getPapa().equals(actual))
                actual.setDer(sucesor.getDer());
            else if (sucesor.getDer() == null)
            {
                papaSuc.setIzq(null);
                checarFe(sucesor);
            }
            else
            {
                papaSuc.cuelga(sucesor.getDer());
                checarFe(sucesor.getDer());
            }
        }
        else
        {
            papaSuc.setDer(sucesor.getDer());

            if (papaSuc.getDer() != null)
                papaSuc.getDer().setPapa(papaSuc);

            raiz.setFe(raiz.getFe() - 1);
            checarFe(raiz);
        }
    }

    private NodoAVL<T> rota(NodoAVL<T> actual) {
        if (actual.getFe() == 2 && ((actual.getDer() != null && actual.getDer().getFe() == 0) || (actual.getDer() != null && actual.getDer().getFe() == 1))) {
            return rotarDD(actual);
        }

        if (actual.getFe() == -2 && ((actual.getIzq() != null && actual.getIzq().getFe() == 0) || (actual.getIzq() != null && actual.getIzq().getFe() == -1))) {
            return rotarII(actual);
        }

        if (actual.getFe() == 2 && actual.getDer() != null && actual.getDer().getFe() == -1) {
            return rotarDI(actual);
        }

        return rotarID(actual);
    }

    private NodoAVL<T> rotarDD(NodoAVL<T> actual) {
        NodoAVL<T> papa, alfa, beta, gamma, A, B, C, D;

        // Asignación inicial de los nodos relacionados
        papa = actual.getPapa();
        alfa = actual;
        beta = actual.getDer();
        gamma = beta.getDer();
        A = alfa.getIzq();
        B = beta.getIzq();
        C = gamma.getIzq();
        D = gamma.getDer();

        // Reestructuración del árbol AVL
        beta.cuelgaDir(alfa, 'I');
        beta.cuelgaDir(gamma, 'D');
        alfa.cuelgaDir(A, 'I');
        alfa.cuelgaDir(B, 'D');
        gamma.cuelgaDir(C, 'I');
        gamma.cuelgaDir(D, 'D');

        // Si el nodo actual no tiene padre, entonces beta se convierte en la nueva raíz
        if (papa == null)
        {
            raiz = beta;
        }
        else
        {
            // De lo contrario, conectamos el nodo beta al padre
            papa.cuelga(beta);
        }

        // Ajuste de factores de equilibrio (FE) basado en el FE de beta
        if (beta.getFe() == 1)
        {
            // Si el FE de beta es 1, reseteamos el FE de beta y alfa a 0
            beta.setFe(0);
            alfa.setFe(0);
        }
        else // Si el FE de beta fue 0
        {
            // El FE de gamma no cambia, pero ajustamos el FE de alfa y beta
            alfa.setFe(1);
            beta.setFe(-1);
        }

        // Devolvemos el nodo beta como la nueva raíz del subárbol reestructurado
        return beta;
    }

    private NodoAVL<T> rotarII(NodoAVL<T> actual) {
        NodoAVL<T> papa, alfa, beta, gamma, A, B, C, D;

        // Asignación de nodos relacionados
        papa = actual.getPapa();
        alfa = actual;
        beta = actual.getIzq();
        gamma = beta.getIzq();
        A = gamma.getIzq();
        B = gamma.getDer();
        C = beta.getDer();
        D = alfa.getDer();

        // Reestructuración del árbol AVL
        beta.cuelgaDir(gamma, 'I');
        beta.cuelgaDir(alfa, 'D');
        gamma.cuelgaDir(A, 'I');
        gamma.cuelgaDir(B, 'D');
        alfa.cuelgaDir(C, 'I');
        alfa.cuelgaDir(D, 'D');

        // Si el nodo actual no tiene padre, se convierte en raíz
        if (papa == null)
        {
            raiz = beta;
        }
        else
        {
            // De lo contrario, se cuelga el nodo beta al padre
            papa.cuelga(beta);
        }

        // Ajuste de factores de equilibrio (FE) basado en el FE de beta
        if (beta.getFe() == -1)
        {
            // Si el FE de beta es -1, se resetea el FE de beta y alfa a 0
            beta.setFe(0);
            alfa.setFe(0);
        }
        else // Si el FE de beta fue 0
        {
            // El FE de gamma no cambia, pero se ajusta el FE de alfa y beta
            alfa.setFe(-1);
            beta.setFe(1);
        }

        // Devuelve el nodo beta como la nueva raíz del subárbol reestructurado
        return beta;

    }

    private NodoAVL<T> rotarDI(NodoAVL<T> actual) {
        NodoAVL<T> papa, alfa, beta, gamma, A, B, C, D;

        // Asignación inicial de los nodos relacionados
        papa = actual.getPapa();
        alfa = actual;
        beta = actual.getDer();
        gamma = beta.getIzq();
        A = alfa.getIzq();
        B = gamma.getIzq();
        C = gamma.getDer();
        D = beta.getDer();

        // Reestructuración del árbol AVL alrededor de gamma
        gamma.cuelgaDir(alfa, 'I');
        gamma.cuelgaDir(beta, 'D');
        alfa.cuelgaDir(A, 'I');
        alfa.cuelgaDir(B, 'D');
        beta.cuelgaDir(C, 'I');
        beta.cuelgaDir(D, 'D');

        // Si el nodo actual no tiene padre, gamma se convierte en la nueva raíz
        if (papa == null)
        {
            raiz = gamma;
        }
        else
        {
            // Si tiene padre, conectamos gamma al nodo padre
            papa.cuelga(gamma);
        }

        // Ajuste de factores de equilibrio (FE) basado en el FE original de gamma
        if (gamma.getFe() == 1) // Si el FE de gamma era 1
        {
            gamma.setFe(0);
            alfa.setFe(-1);
            beta.setFe(0);
        }
        else if (gamma.getFe() == -1) // Si el FE de gamma era -1
        {
            gamma.setFe(0);
            alfa.setFe(0);
            beta.setFe(1);
        }
        else // Si el FE de gamma era 0
        {
            gamma.setFe(0);
            alfa.setFe(0);
            beta.setFe(0);
        }

        //regresamos nueva raiz del subarbol
        return gamma;
    }

    private NodoAVL<T> rotarID(NodoAVL<T> actual) {
        NodoAVL<T> papa, alfa, beta, gamma, A, B, C, D;

        // Asignación inicial de los nodos relacionados
        papa = actual.getPapa();
        alfa = actual;
        beta = actual.getIzq();
        gamma = beta.getDer();
        A = beta.getIzq();
        B = gamma.getIzq();
        C = gamma.getDer();
        D = alfa.getDer();

        // Reestructuración del árbol AVL alrededor de gamma
        gamma.cuelgaDir(beta, 'I');
        gamma.cuelgaDir(alfa, 'D');
        beta.cuelgaDir(A, 'I');
        beta.cuelgaDir(B, 'D');
        alfa.cuelgaDir(C, 'I');
        alfa.cuelgaDir(D, 'D');

        // Si el nodo actual no tiene padre, gamma se convierte en la nueva raíz
        if (papa == null)
        {
            raiz = gamma;
        }
        else
        {
            // Si tiene padre, conectamos gamma al nodo padre
            papa.cuelga(gamma);
        }

        // Ajuste de factores de equilibrio (FE) basado en el FE original de gamma
        // Estos valores han sido derivados algebraicamente
        if (gamma.getFe() == 1) // Si el FE de gamma era 1
        {
            gamma.setFe(0);
            alfa.setFe(0);
            beta.setFe(-1);
        }
        else if (gamma.getFe() == -1) // Si el FE de gamma era -1
        {
            gamma.setFe(0);
            alfa.setFe(1);
            beta.setFe(0);
        }
        else // Si el FE de gamma era 0
        {
            gamma.setFe(0);
            alfa.setFe(0);
            beta.setFe(0);
        }

        //regresamos nueva raiz del subarbol
        return gamma;
    }


}
