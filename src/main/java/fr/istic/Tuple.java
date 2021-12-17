package fr.istic;

public class Tuple<K, V> {

    public final K _1;
    public final V _2;

    public Tuple(K _1, V _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static <K, V> Tuple<K, V> of(K _1, V _2) {
        return new Tuple<>(_1, _2);
    }

    @Override
    public String toString() {
        return _1 + ": " +_2;
    }

    public K get_1() {
        return _1;
    }

    public V get_2() {
        return _2;
    }
}
