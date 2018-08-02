package name.ruiz.juanfco.importacsv.vista.generador;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ObjectIterator implements IObjectIterator {

    private IObjectIterator iterator;
    private int depth;
    private int child = -1;
    private Stack<JavaIteratorContext> iteratorStack = new Stack<JavaIteratorContext>();
    private String name;
    private Object value;
    private String valueAsString;
    private Set<Object> values = new HashSet<Object>();

    public ObjectIterator(String name, Object object) {
        this.iterator = new RootIterator(name, object);
    }

    public boolean next() {
        endNode();

        if (value != null) {
            IObjectIterator childIterator;
            if ((childIterator = iteratorFor(value)) != null && !values.contains(value)) {
                values.add(value);
                pushIterator(childIterator);
                return next();
            }
        }

        if (iterator.next()) {
            beginNode();
            name = iterator.getName();
            value = iterator.getValue();

            if (value == null) {
                valueAsString = null;
            } else if (isSingleValued(value)) {
                valueAsString = value.toString();
            } else {
                valueAsString = value.getClass().getName() + "@" + Integer.toHexString(value.hashCode());
                if (values.contains(value)) {
                    valueAsString += "...";
                }
            }

            return true;
        }

        if (iteratorStack.size() > 0) {
            popIterator();
            return next();
        }

        return false;
    }

    public void nextParent() {
        if (iteratorStack.size() > 0) {
            popIterator();
        }
    }

    private void pushIterator(IObjectIterator childIterator) {
        iteratorStack.push(new JavaIteratorContext(iterator, depth, child, name, value));
        iterator = childIterator;
        child = -1;
        depth++;
        name = null;
        value = null;
    }

    private void popIterator() {
        JavaIteratorContext context = iteratorStack.pop();
        iterator = context.getIterator();
        child = context.getChild();
        depth--;
        // name = context.getName();
        // value = context.getValue();
        name = null;
        value = null;
    }

    private void beginNode() {
        child++;
    }

    private void endNode() {
    }

    public String getName() {
        // return iterator.getName();
        return name;
    }

    public Object getValue() {
        // if (isSingleValued(iterator.getValue())) {
        // return iterator.getValue();
        // } else {
        // return iterator.getName();
        // }

        // return iterator.getValue();
        return value;
    }

    public String getValueAsString() {
        return valueAsString;
    }

    public int getDepth() {
        return depth;
    }

    public int getChild() {
        return child;
    }

    private IObjectIterator iteratorFor(Object object) {
        try {
            if (object == null) {
                return null;
            }

            if (object.getClass().isArray()) {
                return new ArrayIterator(name, object);
            }

            if (object instanceof Iterable) {
                return new CollectionIterator(name, (Iterable<?>) object);
            }

            if (object instanceof Map) {
                return new MapIterator(name, (Map<?, ?>) object);
            }

            if (object instanceof Map.Entry) {
                return new MapEntryIterator(name, (Map.Entry<?, ?>) object);
            }

            if (isSingleValued(object)) {
                return null;
            }

            return new PropertyIterator(object);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static boolean isSingleValued(Object object) {
        if (object == null) {
            return true;
        }

        final Class<? extends Object> type = object.getClass();

        if (type.isPrimitive() || type.isEnum()) {
            return true;
        }

        if (object instanceof Number) {
            return true;
        }

        if (object instanceof CharSequence) {
            return true;
        }

        if (object instanceof Date) {
            return true;
        }

        if (object instanceof Boolean) {
            return true;
        }

        if (object instanceof Character) {
            return true;
        }

        if (object instanceof Void) {
            return true;
        }

        // if (object instanceof Map.Entry) {
        // return true;
        // }

        return false;
    }

}
