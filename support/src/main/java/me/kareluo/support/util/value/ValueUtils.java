package me.kareluo.support.util.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by felix on 16/4/28.
 */
public class ValueUtils {

    private ValueUtils() {
        /* cannot be instantiated */
    }

    /**
     * 判空
     *
     * @param sequence 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    /**
     * 判空
     *
     * @param arrays byte[]
     * @return 是否为空
     */
    public static boolean isEmpty(byte[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays int[]
     * @return 是否为空
     */
    public static boolean isEmpty(int[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays int[]
     * @return 是否为空
     */
    public static boolean isEmpty(short[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays char[]
     * @return 是否为空
     */
    public static boolean isEmpty(char[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays boolean[]
     * @return 是否为空
     */
    public static boolean isEmpty(boolean[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays long[]
     * @return 是否为空
     */
    public static boolean isEmpty(long[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays float[]
     * @return 是否为空
     */
    public static boolean isEmpty(float[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays double[]
     * @return 是否为空
     */
    public static boolean isEmpty(double[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param arrays T[]
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * 判空
     *
     * @param collection Collection
     * @return 是否为空
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 比较两值是否相同
     *
     * @param a 参数a
     * @param b 参数b
     * @return 是否相同
     */
    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    /**
     * 比较两值是否相同，且不为null
     *
     * @param a 参数a
     * @param b 参数b
     * @return 是否相同
     */
    public static boolean nonNullEquals(Object a, Object b) {
        return a != null && a.equals(b);
    }

    /**
     * 将long型数组转为List<Long>类型对象
     *
     * @param values long[]型对象
     * @return List<Long>型对象
     */
    public static List<Long> asList(long[] values) {
        List<Long> list = new ArrayList<>();
        if (!isEmpty(values)) {
            for (long value : values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 将int型数组转为List<Integer>类型对象
     *
     * @param values int[]型对象
     * @return List<Integer>型对象
     */
    public static List<Integer> asList(int[] values) {
        List<Integer> list = new ArrayList<>();
        if (!isEmpty(values)) {
            for (int value : values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 将boolean型数组转为List<Boolean>类型对象
     *
     * @param values boolean[]型对象
     * @return List<Boolean>型对象
     */
    public static List<Boolean> asList(boolean[] values) {
        List<Boolean> list = new ArrayList<>();
        if (!isEmpty(values)) {
            for (boolean value : values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 将float型数组转为List<Float>类型对象
     *
     * @param values float[]型对象
     * @return List<Float>型对象
     */
    public static List<Float> asList(float[] values) {
        List<Float> list = new ArrayList<>();
        if (!isEmpty(values)) {
            for (float value : values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 将double型数组转为List<Double>类型对象
     *
     * @param values double[]型对象
     * @return List<Double>型对象
     */
    public static List<Double> asList(double[] values) {
        List<Double> list = new ArrayList<>();
        if (!isEmpty(values)) {
            for (double value : values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 将char型数组转为List<Character>类型对象
     *
     * @param values char[]型对象
     * @return List<Character>型对象
     */
    public static List<Character> asList(char[] values) {
        List<Character> list = new ArrayList<>();
        if (!isEmpty(values)) {
            for (char value : values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 将short型数组转为List<Short>类型对象
     *
     * @param values short[]型对象
     * @return List<Short>型对象
     */
    public static List<Short> asList(short[] values) {
        List<Short> list = new ArrayList<>();
        if (!isEmpty(values)) {
            for (short value : values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 将List<Long>类型转为long[]类型对象
     *
     * @param values List<Long>型对象
     * @return long[]型对象
     */
    public static long[] toLongArray(List<Long> values) {
        if (isEmpty(values)) {
            return new long[0];
        }
        int size = values.size();
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }

    /**
     * 将List<Integer>类型转为int[]类型对象
     *
     * @param values List<Integer>型对象
     * @return int[]型对象
     */
    public static int[] toIntArray(List<Integer> values) {
        if (isEmpty(values)) {
            return new int[0];
        }
        int size = values.size();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }


    /**
     * 将List<Boolean>类型转为boolean[]类型对象
     *
     * @param values List<Boolean>型对象
     * @return boolean[]型对象
     */
    public static boolean[] toBooleanArray(List<Boolean> values) {
        if (isEmpty(values)) {
            return new boolean[0];
        }
        int size = values.size();
        boolean[] array = new boolean[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }

    /**
     * 将List<Short>类型转为short[]类型对象
     *
     * @param values List<Short>型对象
     * @return short[]型对象
     */
    public static short[] toShortArray(List<Short> values) {
        if (isEmpty(values)) {
            return new short[0];
        }
        int size = values.size();
        short[] array = new short[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }

    /**
     * 将List<Float>类型转为float[]类型对象
     *
     * @param values List<Float>型对象
     * @return float[]型对象
     */
    public static float[] toFloatArray(List<Float> values) {
        if (isEmpty(values)) {
            return new float[0];
        }
        int size = values.size();
        float[] array = new float[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }

    /**
     * 将List<Double>类型转为double[]类型对象
     *
     * @param values List<Double>型对象
     * @return double[]型对象
     */
    public static double[] toDoubleArray(List<Double> values) {
        if (isEmpty(values)) {
            return new double[0];
        }
        int size = values.size();
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }

    /**
     * 将List<Character>类型转为char[]类型对象
     *
     * @param values List<Character>型对象
     * @return char[]型对象
     */
    public static char[] toCharArray(List<Character> values) {
        if (isEmpty(values)) {
            return new char[0];
        }
        int size = values.size();
        char[] array = new char[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }
}
