package org.intringsoftw.projectoMaven;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class listaDoblementeEnlazada<Item> implements Iterable<Item> {
    private int N;        
    private Nodo pre;     
    private Nodo post;    

    public listaDoblementeEnlazada() {
        pre  = new Nodo();
        post = new Nodo();
        pre.next = post;
        post.prev = pre;
    }
    private class Nodo {
        private Item item;
        private Nodo next;
        private Nodo prev;
    }
    public boolean isEmpty()    { return N == 0; }
    public int size()           { return N;      }
    
    public void add(Item item) {
        Nodo last = post.prev;
        Nodo x = new Nodo();
        x.item = item;
        x.next = post;
        x.prev = last;
        post.prev = x;
        last.next = x;
        N++;
    }

    public ListIterator<Item> iterator()  { return new DoublyLinkedListIterator(); }
    
    private class DoublyLinkedListIterator implements ListIterator<Item> {
        private Nodo current      = pre.next;  
        private Nodo lastAccessed = null;      
        private int index = 0;

        public boolean hasNext()      { return index < N; }
        public boolean hasPrevious()  { return index > 0; }
        public int previousIndex()    { return index - 1; }
        public int nextIndex()        { return index;     }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastAccessed = current;
            Item item = current.item;
            current = current.next; 
            index++;
            return item;
        }

        public Item previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            current = current.prev;
            index--;
            lastAccessed = current;
            return current.item;
        }

        public void set(Item item) {
            if (lastAccessed == null) throw new IllegalStateException();
            lastAccessed.item = item;
        }

        public void remove() { 
            if (lastAccessed == null) throw new IllegalStateException();
            Nodo x = lastAccessed.prev;
            Nodo y = lastAccessed.next;
            x.next = y;
            y.prev = x;
            N--;
            if (current == lastAccessed)
                current = y;
            else
                index--;
            lastAccessed = null;
        }

        public void add(Item item) {
            Nodo x = current.prev;
            Nodo y = new Nodo();
            Nodo z = current;
            y.item = item;
            x.next = y;
            y.next = z;
            z.prev = y;
            y.prev = x;
            N++;
            index++;
            lastAccessed = null;
        }

    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
}