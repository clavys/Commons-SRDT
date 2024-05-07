package org.atlanmod.commons;

import com.netopyr.wurmloch.crdt.*;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import io.reactivex.functions.Function4;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class LocalStoreBuilder {
    private LocalCrdtStore store;
    private String id;

    public LocalStoreBuilder() {
    }

    public LocalStoreBuilder id(String id) {
        this.id = id;
        return this;
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
    //public <T extends Crdt> T createCrdt(Function4<String, String, Publisher<? extends CrdtCommand>, Subscriber<? super CrdtCommand>, T> factory, String id) {}
    public <T> LWWRegister<T> createLWWRegister(String id) {
        return this.store.createLWWRegister(id);
    }
    public <T> MVRegister<T> createMVRegister(String id) {
        return this.store.createMVRegister(id);
    }
    public GCounter createGCounter(String id) {
        return this.store.createGCounter(id);
    }
    public PNCounter createPNCounter(String id) {
        return this.store.createPNCounter(id);
    }
    public <T> GSet<T> createGSet(String id) {
        return this.store.createGSet(id);
    }
    public <T> ORSet<T> createORSet(String id) {
        return this.store.createORSet(id);
    }
    public <T> RGA<T> createRGA(String id) {
        return this.store.createRGA(id);
    }







}

