import java.util.*;

public class ImprimirArbol {

  public static <T extends Comparable<T>> String getTreeDisplay(NodoAVL<T> root) {
    StringBuilder sb = new StringBuilder();
    List<List<String>> lines = new ArrayList<>();
    List<NodoAVL<T>> level = new ArrayList<>();
    List<NodoAVL<T>> next = new ArrayList<>();

    level.add(root);
    int nn = 1;
    int widest = 0;

    while (nn != 0) {
      nn = 0;
      List<String> line = new ArrayList<>();
      for (NodoAVL<T> n : level) {
        if (n == null) {
          line.add(null);
          next.add(null);
          next.add(null);
        } else {
          String aa = n.toString();
          line.add(aa);
          if (aa.length() > widest) widest = aa.length();

          next.add(n.getIzq());
          next.add(n.getDer());

          if (n.getIzq() != null) nn++;
          if (n.getDer() != null) nn++;
        }
      }

      if (widest % 2 == 1) widest++;

      lines.add(line);

      List<NodoAVL<T>> tmp = level;
      level = next;
      next = tmp;
      next.clear();
    }

    int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);

    for (int i = 0; i < lines.size(); i++) {
      List<String> line = lines.get(i);

      int hpw = (int) Math.floor(perpiece / 2f) - 1;

      if (i > 0) {
        for (int j = 0; j < line.size(); j++) {
          char c = ' ';
          if (j % 2 == 1) {
            if (line.get(j - 1) != null) {
              c = '/' ; // For right children
            } else {
              if ( line.get(j) != null) c = '\\';  // For left children
            }
          }
          sb.append(c);

          if (line.get(j) == null) {
            for (int k = 0; k < perpiece - 1; k++) {
              sb.append(' ');
            }
          } else {
            for (int k = 0; k < hpw; k++) {
              sb.append(j % 2 == 0 ? " " : "\\");
            }
            sb.append(j % 2 == 0 ? "/" : "\\");
            for (int k = 0; k < hpw; k++) {
              sb.append(j % 2 == 0 ? "/" : " ");
            }
          }
        }
        sb.append('\n');
      }

      for (int j = 0; j < line.size(); j++) {
        String f = line.get(j);
        if (f == null) f = "";
        int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
        int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

        for (int k = 0; k < gap1; k++) {
          sb.append(' ');
        }
        sb.append(f);
        for (int k = 0; k < gap2; k++) {
          sb.append(' ');
        }
      }
      sb.append('\n');

      perpiece /= 2;
    }
    return sb.toString();
  }
}
