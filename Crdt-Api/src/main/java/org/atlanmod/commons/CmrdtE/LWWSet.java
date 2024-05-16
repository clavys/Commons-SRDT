package org.atlanmod.commons.CmrdtE;

import com.netopyr.wurmloch.crdt.Crdt;
import com.netopyr.wurmloch.crdt.CrdtCommand;
import com.netopyr.wurmloch.crdt.LWWRegister;
import com.netopyr.wurmloch.crdt.ORSet;
import com.netopyr.wurmloch.vectorclock.StrictVectorClock;
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
    private final Map<E,TimestampedVectorClock> elements = new HashMap<>();
    private VectorClock lastClear;
    private final Processor<LWWSetCommand<E>, LWWSetCommand<E>> commands = ReplayProcessor.create();
    //private final Set<T>
   // private final Processor<LWWSetCommand<E>, LWWSetCommand<E>> commands = ReplayProcessor.create();

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

    public void processCommand(CrdtCommand command){
        if (command instanceof LWWSet.AddCommand) {
            E value = ((LWWSetCommand<E>) command).getValue();
            VectorClock clk = ((LWWSetCommand<E>) command).getClock();
            doAdd(value,clk);
        }
    }

   public void add(E value, VectorClock ts) {
        commands.onNext(new AddCommand<>(getId(), value, ts));
        doAdd(value, ts);
    }

    /**
     * Ajout d'un élément dans le set et renvoie true si l'élément a bien été ajouté.
     * @param element
     * @param timestamp
     */
    public boolean doAdd(E element, VectorClock timestamp) {
        // Ajouter un élément avec isRemoved = false pour indiquer qu'il n'est pas supprimé
        //elements.add(new TimestampedElement<>( timestamp, false));
        TimestampedVectorClock tmp = elements.get(element);
        if (tmp != null){
            if (timestamp.compareTo(tmp.timestamp) > 1) {
                tmp.timestamp = timestamp;
                if (tmp.isRemoved == true){
                    tmp.isRemoved = false;
                    return true;
                }
                return false;
            }
        }else{
            tmp = new TimestampedVectorClock(timestamp, false);
            elements.put(element, tmp);
            if(tmp.timestamp.compareTo(lastClear) <= 0){
                tmp.timestamp = lastClear;
                tmp.isRemoved = true;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean doRemove(E value,VectorClock clk){
        TimestampedVectorClock timestampedV = elements.get(value);
        if (timestampedV != null){
            if (timestampedV.timestamp.compareTo(clk) > 0){
                timestampedV.timestamp = clk;
                if(!timestampedV.isRemoved){
                    timestampedV.isRemoved = true;
                    return true;
                }
            }
            return false;
        }else{
            TimestampedVectorClock newTimestampedV = new TimestampedVectorClock(clk, true);
            elements.put(value,newTimestampedV);
            return false;
        }
    }

    public E lwwSetQuery(E key){
        TimestampedVectorClock timestampedV = elements.get(key);
        if(timestampedV != null && !timestampedV.isRemoved){
            return key;
        }else{
            return null;
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


    private static class TimestampedVectorClock {
        public VectorClock timestamp;
        public boolean isRemoved;

        public TimestampedVectorClock(VectorClock timestamp, boolean isRemoved) {
            this.timestamp = timestamp;
            this.isRemoved = isRemoved;
        }

        @Override
        public int hashCode() {
            return timestamp.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TimestampedVectorClock other = (TimestampedVectorClock) obj;
            return timestamp.equals(other.timestamp);
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

    public static class LWWSetCommand<E> extends CrdtCommand{

        private final E value;
        private final VectorClock clock;

        public LWWSetCommand(String crdtId, E value, VectorClock clock) {
            super(crdtId);
            this.value = value;
            this.clock = clock;
        }

        E getValue(){
            return value;
        }

        VectorClock getClock(){
            return clock;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            LWWSet.LWWSetCommand<?> that = (LWWSet.LWWSetCommand<?>) o;

            return new EqualsBuilder()
                    .appendSuper(super.equals(o))
                    .append(value, that.value)
                    .append(clock, that.clock)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .appendSuper(super.hashCode())
                    .append(value)
                    .append(clock)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .appendSuper(super.toString())
                    .append("value", value)
                    .append("clock", clock)
                    .toString();
        }
    }

    public static final class AddCommand<E> extends LWWSetCommand{

        public AddCommand(String crdtId, E value, VectorClock clock) {
            super(crdtId, value, clock);

        }
    }

}
