package ule.edi.queuewithrep;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.*;

import ule.edi.exceptions.EmptyCollectionException;

public abstract class AbstractQueueWithRefTests {

	protected abstract <T> QueueWithRep<T> createQueueWithRep();
	

	private QueueWithRep<String> S1;

	private QueueWithRep<String> S2;
	
	@Before
	public void setupQueueWithReps() {

		this.S1 = createQueueWithRep();
		
		this.S2 = createQueueWithRep();
		
		S2.add("ABC", 5);
		S2.add("123", 5);
		S2.add("XYZ", 10);
	}

	@Test
	public void testConstructionIsEmpty() {
		assertTrue(S1.isEmpty());
		assertFalse(S2.isEmpty());
	}
	
	@Test
	//Las nuevas instancias del TAD tienen tamaño cero: 
	public void testConstructionCardinality() {
		assertEquals(S1.size(), 0);
	}

	@Test
	public void testToStringInEmpty() {
		assertTrue(S1.isEmpty());
		assertEquals(S1.toString(), "()");
	}
	
	@Test
	public void testToString1elem() {
		assertTrue(S1.isEmpty());
		S1.add("A",3);
		assertEquals(S1.toString(), "(A A A )");
	}
	
	@Test
	//Añadir elementos con una multiplicidad incrementa su contador y el tamaño de la cola: ")
	public void testAddWithCount() {
		S1.add("ABC", 5);
		assertEquals(S1.count("ABC"), 5);
		assertEquals(S1.size(), 5);
		S1.add("ABC", 5);
		assertEquals(S1.count("ABC"), 10);
		assertEquals(S1.size(), 10);
		S1.add("123", 5);		
		assertEquals(S1.count("123"), 5);
		assertEquals(S1.count("ABC"), 10);
		assertEquals(S1.size(), 15);
	}
	
	
	@Test
	//Se pueden eliminar cero instancias de un elemento con remove(x, 0): ")
	public void testRemoveZeroInstances() {
		S1.remove("ABC", 0);
	}
	
	// TODO AÑADIR MAS TESTS
	

	@Test
	public void testIsEmpty() {
		
		Assert.assertTrue(S1.isEmpty());
		S1.add("ASDF", 2);
		Assert.assertFalse(S1.isEmpty());

		
	}
	//SI HEREDAN DE RUNTIME LAS EXCEPCIONES NO HACE FALTA INDICARLAS EN LA SIGNATURA
	
	@Test
	public void testSize() {
		
		Assert.assertEquals(S1.size(), 0);
		S1.add("ASDF",2);
		S1.add("JKLN",7);
		Assert.assertEquals(S1.size(), 9);
		
	}
	
	@Test
	public void testCount() {
		
		Assert.assertEquals(S1.count("PRQS"), 0);		
		
		S1.add("PRQS",7);
		Assert.assertEquals(S1.count("PRQS"), 7);
		
		//que cuente las instancias del ultimo elemento
		
	}
	
	@Test
	public void testContains() {
		
		
		//Voy a llamar al contains con la lista vacia
		Assert.assertEquals(S1.contains("ASDF"), false);
				
		S1.add("ASDF",7);
		Assert.assertTrue(S1.contains("ASDF"));
		Assert.assertFalse(S1.contains("J"));
		
	}
	
	
	@Test
	public void testAdd() {
		S1.add("ASDF",7);
		S1.add("ASDF",3); //si lo vuelvo a aniadir quiero ver que efectivamente me los ha sumado al contador
		S1.add("JKLM",1);
		S1.add("POIU",9);
		S1.add("SER",3); 
		S1.add("NO",7);
		S1.add("SER",5);
		S1.add("PQTY",3);
		S1.add("PQR",2); 
		S1.add("PQR",2);
		S1.add("Hola");
		S1.add("Quetal");
		

		
		Assert.assertEquals(S1.count("ASDF"), 10);
		Assert.assertEquals(S1.count("PQR"), 4);

		
		
	}
	
	@Test
	public void testAddOneSingle() {
		S1.add("ASDF"); //si lo vuelvo a aniadir quiero ver que efectivamente me los ha sumado al contador
		Assert.assertEquals(S1.count("ASDF"), 1);
		
		S1.add("ASDF");
		Assert.assertEquals(S1.count("ASDF"), 2);

		
	}
	
	@Test
	public void testRemove() throws EmptyCollectionException {
		String elemento = "Portatil";
		String elemento1 = "Impresora";
		String elemento2 = "Microfono";
		
		S1.add(elemento, 3);
		S1.add(elemento1, 2);
		S1.add(elemento2, 1);
		
		Assert.assertEquals(S1.size(), 6);
		//elimino y veo que se quita el primero y que el numero de instancias que retorna es 3
		
		Assert.assertEquals(S1.remove(), 3);
		
	}
	
	
	@Test
	public void testRemoveTimes() throws EmptyCollectionException { 
		
		String elemento = "Portatil";
		String elemento1 = "Impresora";
		String elemento2 = "Microfono";
		
		S1.add(elemento, 3);
		S1.add(elemento1, 2);
		S1.add(elemento2, 2);
		
	
		Assert.assertEquals(S1.count(elemento1), 2);
				
		S1.remove(elemento, 2);//le quito 2 ocurrencias al primero

		Assert.assertEquals(S1.count(elemento), 1);
		
		//que contega el elemento a quitar en la ultima posicion
		
		S1.remove(elemento2, 1);
		Assert.assertEquals(S1.count(elemento2), 1);

		
		
		
		
	}
	
	@Test
	public void testIterador() { 
		
		String elemento = "Portatil";
		String elemento1 = "Impresora";
		String elemento2 = "Microfono";

		
		S1.add(elemento);
		S1.add(elemento1,2); //voy a recorrer el iterador y cada vex que me encuentre un elemento lo meto en la lista y al final se comprueba que tienen que tener lo mismo
		S1.add(elemento2,3);
		
		
		Iterator<String> oIterator = S1.iterator(); //el iterador hace Portatil, impresora, impresora, microfono, microfono,microfono
		//creo un objeto iterador y le digo, de esta bolsa, hazme un iterador y voy a crear una lista para meter los objetos del iterador y le digo mientras que tenga un objeto siguiente en la lista a�ademelo
	
		ArrayList<String> listaAuxiliar = new ArrayList<String>();
		while(oIterator.hasNext()) {
			listaAuxiliar.add(oIterator.next());
		}
	
		Assert.assertEquals("[Portatil, Impresora, Impresora, Microfono, Microfono, Microfono]", listaAuxiliar.toString());
	
	}
	
	@Test
	public void testIteradorOnlyOne() { 
		//lista con 1 elemento
		String elemento = "Portatil";

			S1.clear();
			S1.add(elemento);
			Iterator<String> oIterator2 = S1.iterator();


			ArrayList<String> listaAuxiliar1 = new ArrayList<String>();

			while(oIterator2.hasNext()) {
				listaAuxiliar1.add(oIterator2.next());
			}
			
			Assert.assertEquals("[Portatil]", listaAuxiliar1.toString());

		
	}
	
	
	@Test
	public void testIteradorTwoElems() {
		String elemento = "Portatil";
		String elemento1 = "Impresora";
		
		S1.add(elemento);
		S1.add(elemento1,2); 
		
		Iterator<String> oIterator = S1.iterator();
	
		ArrayList<String> listaAuxiliar = new ArrayList<String>();
		while(oIterator.hasNext()) {
			listaAuxiliar.add(oIterator.next());
		}
	
		Assert.assertEquals("[Portatil, Impresora, Impresora]", listaAuxiliar.toString());
	
	
	}

	
	/** TEST DE EXCEPCIONES **/
	
	@Test (expected = NoSuchElementException.class)
	public void testNextNoSuchElementException() {
		Iterator<String> iterador = S1.iterator();
		
		iterador.next(); //le digo que me diga el siguiente estando la lista vacia
		
		
	}

	
	@Test (expected = IllegalArgumentException.class)
	public void testAddIllegal() { 
		
		S2.add("Mesa", -7); //instancias negativas
		
	}
	@Test (expected = NullPointerException.class)
	public void testAddNullPointerException() { 
			
		S2.add(null);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddNullTimesPointerException() { 
		
		S2.add(null, 4); 
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testRemoveNullPointerException() { 
		
		S2.remove(null, 2);
		
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testRemoveNoSuchElementException() { 

		S2.remove("ASDF", 3);

		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveIllegalArgumentException() { 

		S1.add("ASDF");
		S1.remove("ASDF", -5);

		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveIllegalArgumentException2() { 

		S1.add("ASDF");
		S1.remove("ASDF", 2); //El numero de veces a eliminar pasa a ser mayor que las que hay

		
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testRemoveEmptyCollectionException() throws EmptyCollectionException{ 
		
		S1.remove(); //trato de eliminar el primer elemento de una coleccion vacia
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testContainsNullPointerException() { 

		S2.contains(null);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testCountNullPointerException() { 
		
		S2.count(null);
		
	}

	
	
	
	
}
	


