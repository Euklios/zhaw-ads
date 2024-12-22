package ch.zhaw.ads;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class Storage {
    public static StringBuffer log = new StringBuffer();
    private final static List<Collectable> root = new LinkedList<>();
    private final static List<Collectable> heap = new LinkedList<>();
    private final static List<Collectable> finalizeStage = new LinkedList<>();
    private static boolean finalizer;

    /* add  root object */
    public static void addRoot(Collectable obj) {
        root.add(obj);
    }

    public static void clear() {
        root.clear();
        heap.clear();
        finalizeStage.clear();
    }

    public static void enableFinalizer(boolean on) {
        finalizer = on;
    }

    private static String getPackage() {
        Package pack = Storage.class.getPackage();
        if (pack != null) {
            return pack.getName() + ".";
        } else {
            return "";
        }
    }

    // create a collectable object of class cls
    public static Collectable _new(String cls, Object arg) {
        Collectable obj;
        try {
            // create an object and call constructor
            Constructor<?> cst = Class.forName(getPackage() + cls).getConstructor(arg.getClass());
            obj = (Collectable) cst.newInstance(new Object[] { arg});
            // add object to heap
            heap.add(obj);
            log.append("new ").append(obj).append("\n");
            return obj;
        } catch (Exception ex) {
            throw new RuntimeException(
                    "error creating object " + getPackage() + cls);
        }
    }

    /* remove object from heap */
    public static void delete(Collectable obj) {
        if (heap.remove(obj)) {
            log.append("delete ").append(obj.toString()).append("\n");
        } else {
            throw new RuntimeException(
                    "error trying to delete object not in heap " + obj.toString());
        }
    }

    /* get all root objects */
    public static Iterable<Collectable> getRoot() {
        return new LinkedList<>(root);
    }

    /* get heap */
    public static Iterable<Collectable> getHeap() {
        return new LinkedList<>(heap);
    }

    /* get references to collectables of an object */
    public static Iterable<Collectable> getRefs(Collectable obj) {
        // get all fields of an object
        Field[] fields = obj.getClass().getFields();
        List<Collectable> fieldList = new LinkedList<>();
        for (Field field : fields) {
            try {
                Object o = field.get(obj);
                if (o instanceof Collectable) {
                    fieldList.add((Collectable) o);
                }
            } catch (Exception ignored) {
            }
        }
        return fieldList;
    }

    /* dump an iterator */
    public static void dump(String s, Iterable<?> itr) {
        log.append(s);
        for (Object o: itr) {
            log.append(" ").append(o.toString());
        }
        log.append("\n");
    }

    public static String getLog() {
        return log.toString();
    }

    private static void mark(Collectable obj) {
        obj.setMark(true);
    }

    private static void sweep() {
        if (finalizer) {
            sweepWithFinalizer();
        } else {
            for (Collectable obj : Storage.getHeap()) {
                if (!obj.isMarked()) {
                    delete(obj);
                }

                obj.setMark(false);
            }
        }
    }

    private static void sweepWithFinalizer() {
        for (Collectable obj : Storage.getHeap()) {
            if (!obj.isMarked()) {
                finalizeStage.add(obj);
                try {
                    //noinspection FinalizeCalledExplicitly
                    obj.finalize();
                } catch (Exception ignored) {
                }
            }

            obj.setMark(false);
        }
    }

    public static void gc() {
        for (Collectable collectable : finalizeStage) {
            delete(collectable);
        }
        finalizeStage.clear();

        for (Collectable collectable : Storage.getRoot()) {
            markRecursive(collectable);
        }

        sweep();
    }

    public static void markRecursive(Collectable obj) {
        if (obj.isMarked()) {
            return;
        }

        mark(obj);
        for (Collectable ref : Storage.getRefs(obj)) {
            markRecursive(ref);
        }
    }
}
