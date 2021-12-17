package fr.istic;

public class Tuple3<K, V, U> {

    public final K _1;
    public final V _2;
    public final U _3;

    public Tuple3(K _1, V _2, U _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public static <K, V, U> Tuple3<K, V, U> of(K _1, V _2, U _3) {
        return new Tuple3<>(_1, _2, _3);
    }

    @Override
    public String toString() {
        return _1 + ": " +_2 + ", " + _3 ;
    }
}
