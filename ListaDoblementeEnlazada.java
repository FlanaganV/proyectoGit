package org.intringsoftw.proyectoMaven;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListaDoblementeEnlazada<T> implements Iterable<T>{
	private int n;
	private Nodo pre;
	private Nodo post;

	public ListaDoblementeEnlazada(){
		pre=new Nodo();
		post=new Nodo();
		pre.next=post;
		post.prev=pre;
	}
	
	private class Nodo{
		private T item;
		private Nodo next;
		private Nodo prev;
	}
	
	public boolean isEmpty(){
		return n==0;
	}
	
	public int size(){
		return n;
	}

	public void add(T item){
		Nodo last=post.prev;
		Nodo x=new Nodo();
		x.item=item;
		x.next=post;
		x.prev=last;
		post.prev=x;
		last.next=x;
		n++;		
	}
	
	public ListIterator<T> iterator(){
		return new DoublyLinkedListIterator();
	}
	
	private class DoublyLinkedListIterator implements ListIterator<T>{
		private Nodo current=pre.next;
		private Nodo lastAccessed=null;
		private int index=0;
		
		public boolean hasNext(){
			return index<n;
		}
		public boolean hasPrevious(){
			return index>0;
		}
		public int previousIndex(){
			return index-1;
		}
		public int nextIndex(){
			return index;
		}
		
		public T next(){
			if(!hasNext()){
				throw new NoSuchElementException();				
			}
			lastAccessed=current;
			T item=current.item;
			current=current.next;
			index++;
			return item;
		}
		
		public T previous(){
			if(!hasPrevious()){
				throw new NoSuchElementException();
			}
			current=current.prev;
			index--;
			lastAccessed=current;
			return current.item;
		}
		
		public void set(T item){
			if(lastAccessed==null){
				throw new IllegalStateException();
			}
			lastAccessed.item=item;
		}
		
		public void remove(){
			if(lastAccessed==null){
				throw new IllegalStateException();
			}
			Nodo x=lastAccessed.prev;
			Nodo y=lastAccessed.next;
			x.next=y;
			y.prev=x;
			n--;
			if(current==lastAccessed){
				current=y;
			}else{
				index--;
			}
			lastAccessed=null;
		}
		
		public void add(T item){
			Nodo x=current.prev;
			Nodo y=new Nodo();
			Nodo z=current;
			y.item=item;
			x.next=y;
			y.next=z;
			z.prev=y;
			y.prev=x;
			n++;
			index++;
			lastAccessed=null;
		}
	}
	
	public String toString(){
		StringBuilder s=new StringBuilder();
		for(T item:this){
			s.append(item+" ");
		}
		return s.toString();
	}
}
