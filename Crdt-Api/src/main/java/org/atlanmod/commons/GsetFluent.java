package org.atlanmod.commons;

import com.netopyr.wurmloch.crdt.GSet;

import java.util.Iterator;

public class GsetFluent {

    private final GSet gst;

    public GsetFluent(GSet gst_){
        this.gst = gst_;
    }

    public String getId(){
        return gst.getId();
    }

    public GsetFluent add(String str){
        this.gst.add(str);
        return this;
    }

    public GsetFluent remove(String str){
        this.gst.remove(str);
        return this;
    }

    public Object[] toArray(){
        return this.gst.toArray();
    }

    public Iterator iterator(){
        return this.gst.iterator();
    }

    public int size(){
        return this.gst.size();
    }


}
