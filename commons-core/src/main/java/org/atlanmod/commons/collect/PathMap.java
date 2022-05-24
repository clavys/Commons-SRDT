package org.atlanmod.commons.collect;

import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static org.atlanmod.commons.Guards.checkNotNull;

@ParametersAreNonnullByDefault
public class PathMap<K, V> {
    private final Node root = new RootNode();

    public V put(Path<K> keys, V value) {
        return root.put(checkNotNull(keys), checkNotNull(value));
    }

    @Contract(pure = true)
    public Optional<V> get(Path<K> keys) {
        return root.get(checkNotNull(keys));
    }

    public Optional<V> computeIfAbsent(Path<K> keys, Function<? super K, ? extends V> mappingFunction) {
        return root.computeIfAbsent(checkNotNull(keys), checkNotNull(mappingFunction));
    }

    /**
     * Apply a consumer to each pair (Parent, Child)
     *
     * @param consumer
     */
    public void apply(BiConsumer<V, V> consumer) {
        root.apply(consumer);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    abstract class Node {
        private List<ContentNode> nodes = emptyList();

        public V put(Path<K> keys, V value) {
            ContentNode child;
            K key = keys.head();
            int position = this.indexOf(key);

            if (position < 0) {
                child = addChild(key);
            } else {
                child = nodes.get(position);
            }

            if (keys.size() == 1) {
                child.setValue(value);
            } else {
                child.put(keys.tail(), value);
            }

            return value;
        }

        public Optional<V> get(Path<K> keys) {
            ContentNode child;
            K key = keys.head();
            int position = indexOf(key);

            if (position < 0) {
                return Optional.empty();
            } else {
                child = nodes.get(position);
            }

            return keys.size() == 1 ? child.getValue() : child.get(keys.tail());
        }

        public Optional<V> computeIfAbsent(Path<K> keys, Function<? super K, ? extends V> mappingFunction) {
            ContentNode child;
            K key = keys.head();

            int position = indexOf(key);

            if (position < 0) {
                child = addChild(key);
                child.setValue(mappingFunction.apply(key));
            } else {
                child = nodes.get(position);
            }

            if (keys.size() == 1) {
                return child.getValue();
            } else {
                return child.computeIfAbsent(keys.tail(), mappingFunction);
            }
        }

        public abstract void apply(BiConsumer<V,V> consumer);

        private int indexOf(K key) {
            for (int i = 0; i < nodes.size(); i++) {
                ContentNode each = nodes.get(i);
                if (each.key.equals(key)) {
                    return i;
                }
            }
            return -1;
        }

        private ContentNode addChild(K key) {
            assert key != null;

            ContentNode newNode = new ContentNode(this, key);
            this.nodes().add(newNode);

            return newNode;
        }

        List<ContentNode> nodes() {
            if (nodes.isEmpty()) {
                nodes = new ArrayList<>(3);
            }

            return nodes;
        }

        @Override
        public String toString() {
            if (nodes.isEmpty()) return "";

            StringBuilder builder = new StringBuilder();
            builder.append(": [").append(nodes.get(0));
            for (int i = 1; i < nodes.size(); i++) {
                builder.append(',').append(nodes.get(i));
            }
            builder.append(']');

            return builder.toString();
        }
    }

    class RootNode extends Node {
        @Override
        public void apply(BiConsumer<V, V> consumer) {
            for (ContentNode each: nodes()) {
                each.apply(consumer);
            }
        }

        @Override
        public String toString() {
            return "root" + super.toString();
        }
    }

    class ContentNode extends Node {
        private final Node parent;
        private final K key;
        private V value;

        ContentNode(Node parent, K key) {
            assert key != null;

            this.parent = parent;
            this.key = key;
        }

        Optional<V> getValue() {
            return Optional.ofNullable(value);
        }

        void setValue(V value) {
            this.value = value;
        }

        @Override
        public void apply(BiConsumer<V, V> consumer) {
            for(ContentNode each: nodes()) {
                consumer.accept(value, each.value);
                each.apply(consumer);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(key)
                    .append(": ")
                    .append(value)
                    .append(super.toString());

            return builder.toString();
        }
    }
}
