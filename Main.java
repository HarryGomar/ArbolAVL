public class Main {
    public static void main(String[] args) {
        // Creando el árbol AVL
        ArbolAVL<Integer> arbolAVL = new ArbolAVL<>();
        arbolAVL.inserta(10);
        arbolAVL.inserta(-10);
        arbolAVL.inserta(2);
        arbolAVL.inserta(4);
        arbolAVL.inserta(7);
        arbolAVL.inserta(-5);
        arbolAVL.inserta(9);
        arbolAVL.inserta(5);
        arbolAVL.inserta(-20);
        arbolAVL.inserta(13);
        arbolAVL.inserta(6);
        arbolAVL.inserta(-2);
        arbolAVL.inserta(-50);
        arbolAVL.inserta(80);
        arbolAVL.inserta(-70);


        System.out.println("Arbol 1:");
        arbolAVL.imprimirPorNiveles();

        System.out.println();
        System.out.println();

        System.out.println(arbolAVL.toString());

        System.out.println(arbolAVL.find(1));


        arbolAVL.borra(2);
        arbolAVL.borra(4);
        arbolAVL.borra(5);
        arbolAVL.borra(-10);
        arbolAVL.borra(-10);
        arbolAVL.borra(-5);
        arbolAVL.borra(5);
        arbolAVL.borra(9);


        System.out.println("Arbol 2:");
        
        arbolAVL.imprimirPorNiveles();
        System.out.println(arbolAVL.toString());

    }
}