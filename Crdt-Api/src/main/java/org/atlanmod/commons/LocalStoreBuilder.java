package org.atlanmod.commons;

import com.netopyr.wurmloch.crdt.*;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import io.reactivex.functions.Function4;
import javaslang.control.Option;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class LocalStoreBuilder {
    private LocalCrdtStore store;
    private final String id;



    public LocalStoreBuilder() {
        this.id = null;
        this.store = new LocalCrdtStore();


    }

    public LocalStoreBuilder(String id) {
        this.id = id;
        this.store = new LocalCrdtStore(id);

    }
/*
    public LocalStoreBuilder build() {
        if (id == null) {
            this.store = new LocalCrdtStore();
            return this;
        }
        this.store = new LocalCrdtStore(id);
        return this;
    }
*/
    public LocalStoreBuilder connect(@NotNull LocalStoreBuilder other){
        this.store.connect(other.store);
        return this;
    }
    public LocalStoreBuilder disconnect(@NotNull LocalStoreBuilder other){
        this.store.disconnect(other.store);
        return this;
    }

    public CrdtBuilder findPNCounter(String crtdId) {
        Option<PNCounter> crdtOption = store.findPNCounter(crtdId);
        if (crdtOption.isDefined()) {
            PNCounter crdt = crdtOption.get();
            return new CrdtBuilder(crdt);
        } else {
            return null;
        }
    }

    public <T> CrdtBuilder findLWWRegister(String crtdId) {
        Option<LWWRegister<T>> crdtOption = store.findLWWRegister(crtdId);
        if (crdtOption.isDefined()) {
            LWWRegister crdt = crdtOption.get();
            return new CrdtBuilder(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T> CrdtBuilder findMVRegister(String crtdId) {
        Option<MVRegister<T>> crdtOption = store.findMVRegister(crtdId);
        if (crdtOption.isDefined()) {
            MVRegister crdt = crdtOption.get();
            return new CrdtBuilder(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public CrdtBuilder findGCounter(String crtdId) {
        Option<GCounter> crdtOption = store.findGCounter(crtdId);
        if (crdtOption.isDefined()) {
            GCounter crdt = crdtOption.get();
            return new CrdtBuilder(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T> CrdtBuilder findGSet(String crtdId) {
        Option<GSet<T>> crdtOption = store.findGSet(crtdId);
        if (crdtOption.isDefined()) {
            GSet crdt = crdtOption.get();
            return new CrdtBuilder(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T> CrdtBuilder findORSet(String crtdId) {
        Option<ORSet<T>> crdtOption = store.findORSet(crtdId);
        if (crdtOption.isDefined()) {
            ORSet crdt = crdtOption.get();
            return new CrdtBuilder(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T>CrdtBuilder findRGA(String crtdId) {
        Option<RGA<T>> crdtOption = store.findRGA(crtdId);
        if (crdtOption.isDefined()) {
            RGA crdt = crdtOption.get();
            return new CrdtBuilder(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }


    public CrdtBuilder createLWWRegister(String id) {
        return new CrdtBuilder(this.store.createLWWRegister(id));
    }
    public CrdtBuilder createMVRegister(String id) {
        return new CrdtBuilder(this.store.createMVRegister(id));
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

