package org.atlanmod.commons;

import com.netopyr.wurmloch.crdt.Crdt;
import com.netopyr.wurmloch.crdt.CrdtCommand;
import com.netopyr.wurmloch.crdt.GCounter;
import io.reactivex.functions.Function4;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class CrdtBuilder {
    private final Crdt crdt;

    public CrdtBuilder(Crdt crdt) {
        this.crdt = crdt;
    }


    public Function4<String, String, Publisher<? extends CrdtCommand>, Subscriber<? super CrdtCommand>, Crdt> getFactory() {
        return (Function4<String, String, Publisher<? extends CrdtCommand>, Subscriber<? super CrdtCommand>, Crdt>) this.crdt.getFactory();
    }

    public String getId() {
        return this.crdt.getId();
    }

/*
    //Counter G+PN

    public long get() {}
    public void increment() {}
    public void increment(long value) {}

    //+ PNCounter
    public void decrement() {}
    public void decrement(long value) {}
    //GSet OrSet
    public int size() {}
    public Iterator<T> iterator() {}
    public boolean add(T element) {}

    //RGA
    public E get(int index) {}
    public int size() {}
    public void add(int index, E element) {}
    public E remove(int index) {}

    //LWWRegister
    public T get() {}
    public void set(T newValue) {}

    //MVR
    public Array<T> get() {}
    public void set(T newValue) {}
*/
}
