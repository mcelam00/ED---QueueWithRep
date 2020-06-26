package ule.edi.queuewithrep;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.exceptions.EmptyCollectionException;

public class ArrayQueueWithRepImpl<T> implements QueueWithRep<T> { 
	
	// atributos
	
    private final int capacityDefault = 10; //voy a tener 10 espacios constante capacidad de la bolsa
	
	ElemQueueWithRep<T>[] data; //es un array de 10 y en cada sitio tiene un objeto de tipo data. POr ejemplo tengo un XY en la 0 con 5 ocurrencias que es de tipo T que es generico
    private int count; //cuenta el numero de elementos diferentes en la bolsa
    
	// Clase interna 
    
	@SuppressWarnings("hiding")
	public class ElemQueueWithRep<T> {
		T elem;
		int num;
		public ElemQueueWithRep (T elem, int num){
			this.elem=elem;
			this.num=num;
		}
	}
	
	
	///// ITERADOR //////////
	@SuppressWarnings("hiding")
	public class ArrayQueueWithRepIterator<T> implements Iterator<T> {  
		ElemQueueWithRep<T>[] cola;
		int contadorDeElementosDiferentes;
		int countItems;
		int elementosTotales;
		
		public ArrayQueueWithRepIterator(ElemQueueWithRep<T>[] cola, int count){  
			this.cola = cola;
			this.contadorDeElementosDiferentes = 0;
			this.countItems = 0;
			this.elementosTotales = count;
			
		}

		@Override
		public boolean hasNext() { 
			
			if(contadorDeElementosDiferentes<elementosTotales){
				return true;
			}
			return false;
			
		}

		@Override
		public T next() {  
		
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			T elemento = null;
				elemento = cola[contadorDeElementosDiferentes].elem; 
				countItems++;
				if(countItems == cola[contadorDeElementosDiferentes].num) { 
					countItems = 0;
					contadorDeElementosDiferentes++;

				}
				
			
			
			return elemento;
			
			
		}
		
		

	}
	////// FIN ITERATOR
	
	
    // Constructores

	@SuppressWarnings("unchecked")
	public ArrayQueueWithRepImpl() {
		data = new ElemQueueWithRep[capacityDefault];
		count=0;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayQueueWithRepImpl(int capacity) {	//constructor que cambia la capacidad
		data =  new ElemQueueWithRep[capacity];
		count=0;
	}
	
	
	 @SuppressWarnings("unchecked")
	 private void expandCapacity() { //metodo que expande la capacidad, si tengo 6 y no tengo espacio aumenta la capacidad al doble
		
			ElemQueueWithRep<T>[] nuevo= (ElemQueueWithRep<T>[]) new ElemQueueWithRep[data.length*2];	//casting
			
			
			//si voy a meter algo en la bolsa y no tengo espacio porque tiene 6 y voy a meter un septimo creea una bolsa nueva de tamanio 12 y se apunta a los elementos del anterior array para finalmente cambiar la flecha de data al nuevo array de 12 
			for(int i = 0; i < data.length; i++) {
				nuevo[i] = data[i];
			}
			
			data = nuevo;
						
		}
	 
	
			@Override
			public void add(T element, int times) {  

				//si el elemento que me pasan es nulo fallo y si las ocurrencias del elemento no puedan ser 0 ni negativas
				if(times<=0) {
					throw new IllegalArgumentException();
				}
				if(element == null) {
					throw new NullPointerException();

				}
				if(!(contains(element))) {//el elemento no esta contenido
					if(count == data.length) {//compruebo si tengo espacio en la bolsa
						expandCapacity();//si no tengo espacio la amplio
					}
					data[count] = new ElemQueueWithRep<T>(element, times); 
					count++;

				}else {
					//el elemento esta en la cola, primero busco el elemento
					for(int i = 0 ; i < this.count; i++ ) {
						if(element.equals(data[i].elem)) {
							data[i].num = data[i].num + times;
						}


					}


				}
				
			}
			

			@Override
			public void add(T element) {
				
				if(element == null) {
					throw new NullPointerException();
				}
							
				this.add(element, 1);
				
			}

			@Override
			public void remove(T element, int times)  { 
				
				if (element == null) {
					throw new NullPointerException();
				}
				
				if(times < 0) { 
					throw new IllegalArgumentException();
				}
				
				if(times == 0) {
					return; //si hay cero instancias que no haga nada
				}
					
				if(contains(element) == false) {
					throw new NoSuchElementException();
				}
				
				//precondicion: contiene al elemento y toda la entrada esta bien
				
				for(int i= 0; i < data.length; i++) {
					if(data[i] != null) { //si la posicion esta ocupada y quien la ocupa es igual que quien quiero pues le borrare las ocurrencias
						
						if(element.equals(data[i].elem)) {
							
							//miro los elementos que tengo y si tengo mas ocurrencias le quito las que se pasan por parametro
							if(times < data[i].num) {
								data[i].num = data[i].num - times;
								
							}else {
								
								throw new IllegalArgumentException();
							}
							
						}
					}
					
					
				}
				
				
				
				
			}

			@Override 
			public int remove() throws EmptyCollectionException { 
				if(isEmpty()) {
					throw new EmptyCollectionException("ERROR: No es posible eliminar, array vacio");
				}
				
				//creo un contador para guardar el numero de instancias que tengo, entonces he de eliminarlo y mover todos los elementos
				int contador = data[0].num;
				
					for(int i = 0; i < count-1; i++) { //porque la ultima de las ocupadas iria a buscar a una que esta a null y daria fallo entonces la seteo por separado
						
						data[i].elem = data[i+1].elem;
						data[i].num = data[i+1].num;
					
					}
				
					data[count-1] = null; //he pisado la primera y movido todas entonces la ultima esta duplicada en la ultima y penultima, entonces quito la ultima
					count--;
				
				return contador;
				
			}

			@Override
			public void clear() {
				//guardo la capacidad antigua antes de vaciarla
				int capacidadAntigua = this.data.length;
				
				this.count = 0; //reseteo el contador a 0
				data = new ElemQueueWithRep[capacidadAntigua];	//creo de nuevo la lista con la misma capacidad			
				
				
				
			}
			

			@Override
			public boolean contains(T element) {
				boolean testigo = false;
				
				if(element == null) {
					throw new NullPointerException();
				}
				for(int i = 0; i < data.length; i++) {
					
					if(data[i] != null) {
							if(data[i].elem.equals(element)) { 
								testigo = true;
							
							}
					}
			
				}
				
				return testigo;

			}

			@Override
			public boolean isEmpty() { 
				boolean testigo = false;
				
				if(this.count == 0){
					testigo = true;
				}
				return testigo;
			}

			@Override
			public long size() {
				//recorro el vector y si no es nulo, miro el dato cuantas instancias tiene. 
				long contador = 0;
				for(int i = 0; i < data.length; i++) { 					
					if(data[i] != null){
						contador = contador + data[i].num;
					}
					
				}
				
				return contador;				
				
				
				
				
			}

			@Override
			public int count(T element) {
				
				if(element == null) {
					throw new NullPointerException();
				}
				
				int total = 0;
				
					for(int i = 0; i < data.length; i++) {
					
						if(data[i] != null) {
								if(data[i].elem.equals(element)) { //si el elemento es como el que se me pide el total es el numero de instancias (ocurrencias)
									total = data[i].num; //salvo el numero de ocurrencias del elemento que se le pasa para retornarlas
								}
						}
				
					}
				return total;
				
				
			}

			@Override  
			public Iterator<T> iterator() {
				Iterator<T> oIterator = new ArrayQueueWithRepIterator<T>(data,this.count); 
				return oIterator;
			}
			
			
			@Override 
			public String toString() {
				
				final StringBuffer buffer = new StringBuffer();
				
				buffer.append("(");

				
				for(int i = 0; i < this.count; i++) {
					for(int j = 1; j <= data[i].num; j++) {
					
							buffer.append(data[i].elem + " ");
					}
					
				}
				
				
				
				
				buffer.append(")");
				
				return buffer.toString();
			}

	
}
