package name.ruiz.juanfco.importacsv.vista;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hamfree
 */
public class VistaImpl implements Vista {

    private final String S = "|";
    private final String T = "\"";
    private final String CO = "'";
    private final String LA = "{";
    private final String LC = "}";
    private final String CA = "[";
    private final String CC = "]";
    private final String PA = " (";
    private final String PC = ") ";
    private final String SL = System.getProperty("line.separator");
    private final String TAB = "    ";

    @Override
    public StringBuffer exploraObjeto(Object msj) {
        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        if (msj != null) {
            sb = procesaObjeto(msj, sb, 0);
            if (sb.length() == 0) {
                Class cl = msj.getClass();
                if (cl.isArray()) {
                    sb = exploraVector((Object[]) msj, 0);
                } else if (cl.isAssignableFrom(ArrayList.class)) {
                    sb = exploraLista((List<?>) msj, 0);
                } else if (cl.isAssignableFrom(HashMap.class)) {
                    sb = exploraMapa((Map<Object, Object>) msj, 0);
                }
            }
        } else {
            sb.append("null (Sin tipo)");
        }
        return sb;
    }

    @Override
    public StringBuffer exploraLista(List<?> lista, int nivel) {
        StringBuffer sb = new StringBuffer();
        if (lista != null) {
            int indice = 0;
            int elementos = lista.size();
            if (elementos > 0) {
                sb.append("ArrayList de ").append(elementos).append(" elementos.").append(SL);
                for (Object o : lista) {
                    for (int tab = 0; tab <= nivel; tab++) {
                        sb.append(TAB);
                    }
                    sb.append(CA).append(indice).append(CC);
                    sb.append(LA);
                    if (o != null) {
                        Class cl = o.getClass();
                        StringBuffer tmp = new StringBuffer();
                        tmp.setLength(0);
                        tmp = procesaObjeto(o, tmp, -1);
                        if (tmp.length() == 0) {
                            if (cl.isArray()) {
                                Object oa[] = (Object[]) o;
                                sb.append(exploraVector(oa, ++nivel));
                            } else if (cl.isAssignableFrom(ArrayList.class)) {
                                ArrayList<Object> otraLista = (ArrayList<Object>) o;
                                sb.append(exploraLista(otraLista, ++nivel));
                            } else if (cl.isAssignableFrom(HashMap.class)) {
                                HashMap<Object, Object> hm = (HashMap<Object, Object>) o;
                                sb.append(exploraMapa(hm, ++nivel));
                            }
                        } else {
                            sb.append(tmp);
                        }
                    } else {
                        sb.append("null");
                    }
                    indice++;
                    sb.append(LC).append(SL);
                }
            } else {
                sb.append("ArrayList VACIO").append(SL);
            }
        } else {
            sb.append("null");
        }
        return sb;
    }

    @Override
    public StringBuffer exploraMapa(Map<Object, Object> mapa, int nivel) {
        StringBuffer sb = new StringBuffer();
        if (mapa != null) {
            if (!mapa.isEmpty()) {
                int tam = mapa.size();
                Set<Object> conjunto = mapa.keySet();
                Iterator<Object> iter = conjunto.iterator();
                sb.append("HashMap de ").append(tam).append(" elementos").append(SL);
                while (iter.hasNext()) {
                    Object key = iter.next();
                    Object value = mapa.get(key);
                    for (int tab = 0; tab <= nivel; tab++) {
                        sb.append(TAB);
                    }
                    sb.append("CLAVE=").append(S).append(key.toString()).append(S)
                            .append(SL).append("VALOR=").append(LA).append(SL);
                    if (value != null) {
                        Class clazz = value.getClass();
                        StringBuffer tmp = new StringBuffer();
                        tmp.setLength(0);
                        tmp = procesaObjeto(value, tmp, nivel);
                        if (tmp.length() == 0) {
                            if (clazz.isArray()) {
                                sb.append(exploraVector((Object[]) value, ++nivel));
                            } else if (clazz.isAssignableFrom(ArrayList.class)) {
                                sb.append(exploraLista((ArrayList) value, ++nivel));
                            } else if (clazz.isAssignableFrom(HashMap.class)) {
                                sb.append(exploraMapa((HashMap) value, ++nivel));
                            }
                        } else {
                            sb.append(tmp);
                        }
                    } else {
                        sb.append("null");
                    }
                    sb.append(LC).append(SL);
                }
            } else {
                sb.append("HashMap VACIO").append(SL);
            }
        } else {
            sb.append("null");
        }
        return sb;
    }

    @Override
    public StringBuffer exploraObjetos(Object... msj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StringBuffer exploraVector(Object[] matriz, int nivel) {
        StringBuffer sb = new StringBuffer();
        if (matriz != null) {
            int elementos = matriz.length;
            if (elementos > 0) {
                int indice = 0;
                sb.append("Vector de ").append(elementos).append(" elementos.").append(SL);
                for (Object o : matriz) {

                    for (int tab = 0; tab <= nivel; tab++) {
                        sb.append(TAB);
                    }
                    sb.append(CA).append(indice).append(CC).append(" = ");
                    sb.append(LA);
                    if (o != null) {
                        Class clazz = o.getClass();
                        StringBuffer tmp = new StringBuffer();
                        tmp.setLength(0);
                        tmp = procesaObjeto(o, tmp, -1);
                        if (tmp.length() == 0) {
                            if (clazz.isArray()) {
                                Object oa[] = (Object[]) o;
                                sb.append(exploraVector(oa, ++nivel));
                                --nivel;
                            } else if (clazz.isAssignableFrom(HashMap.class)) {
                                HashMap<Object, Object> hm = (HashMap<Object, Object>) o;
                                sb.append(exploraMapa(hm, ++nivel));
                            }
                        } else {
                            sb.append(tmp);
                        }
                    } else {
                        sb.append("null");
                    }
                    sb.append(LC);
                    for (int tab = 0; tab <= nivel; tab++) {
                        sb.append(TAB);
                    }
                    sb.append(SL);
                    indice++;
                }
            } else {
                sb.append("Vector VACIO").append(SL);
            }
        } else {
            sb.append("null");
        }
        return sb;
    }

    private StringBuffer procesaObjeto(Object o, StringBuffer sb, int nivel) {
        if (nivel >= 0) {
            for (int tab = 0; tab <= nivel; tab++) {
                sb.append(TAB);
            }
        }
        if (o != null) {
            Class cl = o.getClass();
            String nc = cl.getSimpleName();

            if (cl.isAssignableFrom(Byte.class)) {
                Byte b = (Byte) o;
                sb.append(S).append(b.toString()).append(S)
                        .append(PA).append(nc).append(PC);
            } else if (cl.isAssignableFrom(Short.class)) {
                Short s = (Short) o;
                sb.append(S).append(s.toString()).append(S)
                        .append(PA).append(nc).append(PC);
            } else if (cl.isAssignableFrom(Integer.class)) {
                Integer i = (Integer) o;
                sb.append(S).append(i.toString()).append(S)
                        .append(PA).append(nc).append(PC);
            } else if (cl.isAssignableFrom(Double.class)) {
                Double d = (Double) o;
                sb.append(S).append(d.toString()).append(S)
                        .append(PA).append(nc).append(PC);
            } else if (cl.isAssignableFrom(BigDecimal.class)) {
                BigDecimal bg = (BigDecimal) o;
                sb.append(S).append(bg.toPlainString()).append(S)
                        .append(PA).append(nc).append(PC);
            } else if (cl.isAssignableFrom(Long.class)) {
                Long l = (Long) o;
                sb.append(S).append(l.toString()).append(S)
                        .append(PA).append(nc).append(PC);
            } else if (cl.isAssignableFrom(String.class)) {
                String s = (String) o;
                sb.append(T).append(s).append(T)
                        .append(PA).append(nc).append(PC);
            } else if (cl.isAssignableFrom(Character.class)) {
                Character ch = (Character) o;
                sb.append(CO).append(ch.toString()).append(CO)
                        .append(PA).append(nc).append(PC);
            }
        } else {
            sb.append("null");
        }
        return sb;
    }
}
