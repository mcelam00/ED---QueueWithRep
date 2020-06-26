package ule.edi.queuewithrep;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import ule.edi.exceptions.EmptyCollectionException;


public class ArrayQueueWithRepRefTests extends AbstractQueueWithRefTests {

	@Override
	protected <T> QueueWithRep<T> createQueueWithRep() {
		
		return new ArrayQueueWithRepImpl<T>();
	}
	
	// tests solo para implementación Array, que no aplican a la implementación  Linked
	
	@Test
	public void expandCapacityTest() {
		ArrayQueueWithRepImpl<Integer> aq = new ArrayQueueWithRepImpl<Integer>(2); //una varialbe que llama a aq y solamente a�ade elementos para ver la capacidad de la lsita
		aq.add(1);
		aq.add(2);
		aq.add(3);
		aq.add(4);
		aq.add(5);
		aq.add(6);
		aq.add(7);
		aq.add(8);
		aq.add(15);
		aq.add(16);
		aq.add(17);
		aq.add(18);
		
	}
	
}