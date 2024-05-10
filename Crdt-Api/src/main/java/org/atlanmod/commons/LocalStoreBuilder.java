package org.atlanmod.commons;

import com.netopyr.wurmloch.crdt.*;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import io.reactivex.functions.Function4;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class LocalStoreBuilder {
    private LocalCrdtStore store;
    private final String id;

    public LocalStoreBuilder() {
        this.id = null;


    }

    public LocalStoreBuilder(String id) {
        this.id = id;


    }

    public LocalStoreBuilder build() {
        if (id == null) {
            this.store = new LocalCrdtStore();
            return this;
        }
        this.store = new LocalCrdtStore(id);
        return this;
    }

    public LocalStoreBuilder connect(@NotNull LocalStoreBuilder other){
        this.store.connect(other.store);
        return this;
    }
    public LocalStoreBuilder disconnect(@NotNull LocalStoreBuilder other){
        this.store.disconnect(other.store);
        return this;
    }

    public CrdtBuilder createLWWRegister(String id) {
        return new CrdtBuilder(this.store.createLWWRegister(id));
    }
    public <T> MVRegister<T> createMVRegister(String id) {
        return this.store.createMVRegister(id);
    }

    public CrdtBuilder createGCounter(String id) {
        return new CrdtBuilder(this.store.createGCounter(id));
    }
    public CrdtBuilder createPNCounter(String id) {
        return new CrdtBuilder(this.store.createPNCounter(id));
    }
    public CrdtBuilder createGSet(String id) {
        return new CrdtBuilder(this.store.createGSet(id));
    }
    public CrdtBuilder createORSet(String id) {
        return new CrdtBuilder(this.store.createORSet(id));
    }
    public CrdtBuilder createRGA(String id) {
        return new CrdtBuilder(this.store.createRGA(id));
    }







}

