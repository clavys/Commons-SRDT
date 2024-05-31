package org.atlanmod.commons;

import com.netopyr.wurmloch.crdt.*;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import javaslang.control.Option;
import org.jetbrains.annotations.NotNull;

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

    public CrdtWrapper findPNCounter(String crtdId) {
        Option<PNCounter> crdtOption = store.findPNCounter(crtdId);
        if (crdtOption.isDefined()) {
            PNCounter crdt = crdtOption.get();
            return new CrdtWrapper(crdt);
        } else {
            return null;
        }
    }

    public <T> CrdtWrapper findLWWRegister(String crtdId) {
        Option<LWWRegister<T>> crdtOption = store.findLWWRegister(crtdId);
        if (crdtOption.isDefined()) {
            LWWRegister crdt = crdtOption.get();
            return new CrdtWrapper(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T> CrdtWrapper findMVRegister(String crtdId) {
        Option<MVRegister<T>> crdtOption = store.findMVRegister(crtdId);
        if (crdtOption.isDefined()) {
            MVRegister crdt = crdtOption.get();
            return new CrdtWrapper(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public CrdtWrapper findGCounter(String crtdId) {
        Option<GCounter> crdtOption = store.findGCounter(crtdId);
        if (crdtOption.isDefined()) {
            GCounter crdt = crdtOption.get();
            return new CrdtWrapper(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T> CrdtWrapper findGSet(String crtdId) {
        Option<GSet<T>> crdtOption = store.findGSet(crtdId);
        if (crdtOption.isDefined()) {
            GSet crdt = crdtOption.get();
            return new CrdtWrapper(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T> CrdtWrapper findORSet(String crtdId) {
        Option<ORSet<T>> crdtOption = store.findORSet(crtdId);
        if (crdtOption.isDefined()) {
            ORSet crdt = crdtOption.get();
            return new CrdtWrapper(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }

    public <T> CrdtWrapper findRGA(String crtdId) {
        Option<RGA<T>> crdtOption = store.findRGA(crtdId);
        if (crdtOption.isDefined()) {
            RGA crdt = crdtOption.get();
            return new CrdtWrapper(crdt);
        } else {

            return null; // ou throw new SomeException();
        }
    }


    public CrdtWrapper createLWWRegister(String id) {
        return new CrdtWrapper(this.store.createLWWRegister(id));
    }
    public CrdtWrapper createMVRegister(String id) {
        return new CrdtWrapper(this.store.createMVRegister(id));
    }

    public CrdtWrapper createGCounter(String id) {
        return new CrdtWrapper(this.store.createGCounter(id));
    }
    public CrdtWrapper createPNCounter(String id) {
        return new CrdtWrapper(this.store.createPNCounter(id));
    }
    public CrdtWrapper createGSet(String id) {
        return new CrdtWrapper(this.store.createGSet(id));
    }
    public CrdtWrapper createORSet(String id) {
        return new CrdtWrapper(this.store.createORSet(id));
    }
    public CrdtWrapper createRGA(String id) {
        return new CrdtWrapper(this.store.createRGA(id));
    }







}

