package org.atlanmod.commons.CmrdtE;

import com.netopyr.wurmloch.crdt.Crdt;
import com.netopyr.wurmloch.crdt.CrdtCommand;
import com.netopyr.wurmloch.crdt.ORSet;
import com.netopyr.wurmloch.vectorclock.VectorClock;
import io.reactivex.processors.ReplayProcessor;
import javaslang.Function4;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.*;

public class LWWSet<E> extends AbstractSet<E> implements Crdt {
    private final String crdtId;
    private VectorClock clock;
    private final Set<TimestampedElement<E>> elements = new HashSet<>();
    //private final Set<T>
    private final Processor<LWWSetCommand<E>, LWWSetCommand<E>> commands = ReplayProcessor.create();

    public LWWSet(String crdtId) {
        this.crdtId = Objects.requireNonNull(crdtId, "Id must not be null");
    }


    @Override
    public String getId() {
        return crdtId;
    }

    //Que ????
    @Override
    public Function4<String, String, Publisher<? extends CrdtCommand>, Subscriber<? super CrdtCommand>, Crdt> getFactory() {
        return null;
    }

    public void add(E value) {

        boolean bo = !(elements.contains(value));

    }

    public synchronized void doAdd(E value) {

    }

    public void processCommand(LWWSetCommand<T> command) {
        if (command instanceof AddCommand) {
            doadd()
        } else if (command instanceof removeCommand) {

        } else {

        }

    }

    @Override
    public Iterator<E> iterator() {
        return new LWWSetIterator<>();
    }

    @Override
    public int size() {
        return 0;
    }

    public void add(T element, long timestamp) {
        // Ajouter un élément avec isRemoved = false pour indiquer qu'il n'est pas supprimé
        elements.add(new TimestampedElement<>(element, timestamp, false));
    }

    public void remove(T element, long timestamp) {
        // Ajouter un élément avec isRemoved = true pour indiquer qu'il est supprimé
        elements.add(new TimestampedElement<>(element, timestamp, true));
    }

    public boolean contains(T element) {
        // Vérifier si l'élément est présent dans les éléments et n'est pas marqué comme supprimé
        TimestampedElement<T> timestampedElement = new TimestampedElement<>(element, 0, false);
        return elements.contains(timestampedElement);
    }

    public void merge(LWWSet<T> other) {
        // Fusionner les ensembles d'éléments
        elements.addAll(other.elements);
    }

    private static class TimestampedElement<T> {
        private T element;
        private long timestamp;
        private boolean isRemoved;

        public TimestampedElement(T element, long timestamp, boolean isRemoved) {
            this.element = element;
            this.timestamp = timestamp;
            this.isRemoved = isRemoved;
        }

        @Override
        public int hashCode() {
            return element.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TimestampedElement<?> other = (TimestampedElement<?>) obj;
            return element.equals(other.element);
        }
    }

    private class LWWSetIterator<E> implements Iterator<E> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            return null;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }

}