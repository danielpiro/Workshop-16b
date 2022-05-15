package com.example.demo.Generic;

public class ThreeGenerics<T, V,S > {
    T ob1;

    V ob2;

    S ob3;



    public ThreeGenerics(T ob1, V ob2, S ob3) {

        this.ob1 = ob1;

        this.ob2 = ob2;

        this.ob3 = ob3;

    }

    public T getOb1() {
        return ob1;
    }

    public V getOb2() {
        return ob2;
    }

    public S getOb3() {
        return ob3;
    }
}