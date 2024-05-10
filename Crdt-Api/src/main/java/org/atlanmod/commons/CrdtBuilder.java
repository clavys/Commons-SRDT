package org.atlanmod.commons;

import com.netopyr.wurmloch.crdt.*;
import io.reactivex.functions.Function4;
import javaslang.collection.Array;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Iterator;

public class CrdtBuilder {
    public final Crdt crdt;

    public CrdtBuilder(Crdt crdt) {
        this.crdt = crdt;
    }


    public Function4<String, String, Publisher<? extends CrdtCommand>, Subscriber<? super CrdtCommand>, Crdt> getFactory() {
        return (Function4<String, String, Publisher<? extends CrdtCommand>, Subscriber<? super CrdtCommand>, Crdt>) this.crdt.getFactory();
    }

    public String getId() {
        return this.crdt.getId();
    }


    public <T> T get() {
        if (crdt instanceof GCounter ) {
            return (T)((Long) ((GCounter) crdt).get());
        } else if (crdt instanceof PNCounter) {
            return (T)((Long) ((PNCounter) crdt).get());
        } else if (crdt instanceof MVRegister) {
            return (T) ((MVRegister<T>) crdt).get();
        } else if (crdt instanceof LWWRegister) {
            return ((LWWRegister<T>) crdt).get();
        } else {
            throw new UnsupportedOperationException("CRDT type not supported for get() method");
        }
    }

    /*
    public long getLong() {
        if (crdt instanceof GCounter) {
            return ((GCounter) crdt).get();
        } else if (crdt instanceof PNCounter) {
            return ((PNCounter) crdt).get();
        }else {
            throw new UnsupportedOperationException("CRDT type not supported for get()->(long) method");
        }
    }

    public <T> Array<T> getArray() {
        if (crdt instanceof MVRegister) {
            return ((MVRegister<T>) crdt).get();
        }else {
            throw new UnsupportedOperationException("CRDT type not supported for get()->(Array<T>) method");
        }
    }

    public <T> T getObjet() {
        if (crdt instanceof LWWRegister) {
            return ((LWWRegister<T>) crdt).get();
        }else {
            throw new UnsupportedOperationException("CRDT type not supported for get()->(T) method");
        }
    }
*/
    public CrdtBuilder increment() {
        if (crdt instanceof GCounter) {
            ((GCounter) crdt).increment();
            return this;
        } else if (crdt instanceof PNCounter) {
            ((PNCounter) crdt).increment();
            return this;
        } else {
            throw new UnsupportedOperationException("CRDT type not supported for increment() method");
        }
    }

    public CrdtBuilder increment(long value) {
        if (crdt instanceof GCounter) {
            ((GCounter) crdt).increment(value);
            return this;
        } else if (crdt instanceof PNCounter) {
            ((PNCounter) crdt).increment(value);
            return this;
        }else {
            throw new UnsupportedOperationException("CRDT type not supported for increment() method");
        }
    }

    public CrdtBuilder decrement() {
        if (crdt instanceof PNCounter) {
            ((PNCounter) crdt).decrement();
            return this;
        }else{
            throw new UnsupportedOperationException("CRDT type not supported for decrement() method");
        }

    }

    public CrdtBuilder decrement(long value) {
        if (crdt instanceof PNCounter) {
            ((PNCounter) crdt).decrement(value);
            return this;
        }else{
            throw new UnsupportedOperationException("CRDT type not supported for decrement(long value) method");
        }

    }

    public <T> int size() {
        if (crdt instanceof GSet) {
            return ((GSet<T>) crdt).size();
        } else if (crdt instanceof ORSet) {
            return ((ORSet<T>) crdt).size();
        } else if (crdt instanceof RGA) {
            return ((RGA<T>) crdt).size();
        } else {
            throw new UnsupportedOperationException("CRDT type not supported for size() method");
        }
    }

    public <T> Iterator<T> iterator() {
        if (crdt instanceof GSet) {
            return ((GSet<T>) crdt).iterator();
        } else if (crdt instanceof ORSet) {
            return ((ORSet<T>) crdt).iterator();
        } else {
            throw new UnsupportedOperationException("CRDT type not supported for iterator() method");
        }
    }


    public <T> CrdtBuilder add(T element) {
        if (crdt instanceof GSet) {
            ((GSet<T>) crdt).add(element);
            return this;
        } else if (crdt instanceof ORSet) {
            ((ORSet<T>) crdt).add(element);
            return this;
        } else {
            throw new UnsupportedOperationException("CRDT type not supported for add() method");
        }
    }

    public <T> Object get(int index) {
        if (crdt instanceof RGA) {
            return ((RGA<T>) crdt).get(index);
        }
        throw new UnsupportedOperationException("CRDT type not supported for get(int index) method");
    }

    public <E> CrdtBuilder add(int index, E element) {
        if (crdt instanceof RGA) {
           ((RGA<E>) crdt).add(index ,element);
           return this;
        }
        throw new UnsupportedOperationException("CRDT type not supported for add(int index, E element) method");
    }


    public <E> CrdtBuilder remove(int index) {
        if (crdt instanceof RGA) {
            ((RGA<E>) crdt).remove(index);
            return this;
        }
        throw new UnsupportedOperationException("CRDT type not supported for remove(int index) method");
    }

    public <T> CrdtBuilder set(T newValue) {
        if (crdt instanceof LWWRegister) {
            ((LWWRegister<T>) crdt).set(newValue);
            return this;
        } else if (crdt instanceof MVRegister) {
            ((MVRegister<T>) crdt).set(newValue);
            return this;
        } else {
            throw new UnsupportedOperationException("CRDT type not supported for set(T newValue) method");
        }
    }


}
