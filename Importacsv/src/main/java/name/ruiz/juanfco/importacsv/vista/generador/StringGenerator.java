package name.ruiz.juanfco.importacsv.vista.generador;

public class StringGenerator {
    
    public static final String INDENT = "  ";
    public static final int MAX_VALUE_LENGTH = 64;
    public static final int MAX_CHILDREN = 64;
    public static final int MAX_STRING_LENGTH = 1024 * 16;

    
    public static String generate(Object object) {
        String s = "";

        ObjectIterator iterator = new ObjectIterator("object", object);

        // print first element without a name part
        if (iterator.next()) {
            String valueAsString = iterator.getValueAsString();

            if (valueAsString == null) {
                s += "null";
            } else {
                s += truncateString(valueAsString);
            }
        }

        // print subsequent elements as name=value pairs
        while (iterator.next()) {
            if (s.length() >= MAX_STRING_LENGTH) {
                return s;
            }

            if (iterator.getChild() >= MAX_CHILDREN) {
                iterator.nextParent();
                continue;
            }

            String valueAsString = iterator.getValueAsString();

            s += System.lineSeparator();
            s += indent(iterator.getDepth()) + truncateString(iterator.getName());
            if (valueAsString == null) {
                s += " = null";
            } else {
                s += " = " + truncateString(valueAsString);
            }
        }

        return s;
    }

    private static String truncateString(String string) {
        if (string != null && string.length() > MAX_VALUE_LENGTH) {
            string = string.substring(0, MAX_VALUE_LENGTH) + "...";
        }
        return string;
    }
    
    private static StringBuilder indent(int depth) {
        StringBuilder s = new StringBuilder(depth * INDENT.length());
        for (int i = 0; i < depth; i++) {
            s.append(INDENT);
        }
        return s;
    }

}
