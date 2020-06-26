package ule.edi.queuewithrep;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.exceptions.EmptyCollectionException;

//CLASE QUE IMPLEMENTA LA COLA CON LISTAS ENLAZADAS

public class LinkedQueueWithRepImpl<T> implements QueueWithRep<T> {
 
	// Atributos
	private QueueWithRepNode<T> front; //Es la referencia al primer elemento de la lista (de tipo nodo, pero inicialmente esta a null porque el contructor no lo crea)
	int count; //cuenta el numero de nodos que tiene la lista enlazada
	
	
	// Clase interna
	@SuppressWarnings("hiding")
	public class QueueWithRepNode<T> {   //CLASE NODO: Cada nodo (=elemento diferente de la lista) contiene, una flecha al siguiente elemento (a null por defecto ya que en el constructor no se inicializa), el elemento en si, y su contador de ocurrencias todo dentro del nodo
		T elem; //tipo generico, podemos hacer una lista de nodos string, de nodos int...
		int num; 
		QueueWithRepNode<T> next; //de tipo nodo, la referencia al siguiente
		
		public QueueWithRepNode (T elem, int num){
			this.elem=elem;
			this.num=num;
		}
		
	}
	
	///// ITERADOR //////////
	@SuppressWarnings("hiding")
	public class LinkedQueueWithRepIterator<T> implements Iterator<T> {
		
		QueueWithRepNode<T> aux;
		int marcadorElemento;
		int ocurrencias;
       	
		public LinkedQueueWithRepIterator(QueueWithRepNode<T> nodo) {
			aux = nodo; //el aux se convierte en el front
			marcadorElemento = 0;
			ocurrencias = 0;
			
		}
		
		@Override
		public boolean hasNext() {
			boolean testigo = true;
			
			if(aux == null) {
				testigo = false;
			}
			
			return testigo;
		}

		@Override
		public T next() {
		
			if(hasNext() == false) {
				throw new NoSuchElementException();				
			}			
			T element = aux.elem;
				ocurrencias++;
				
				if(ocurrencias == aux.num) {
					marcadorElemento++; 
					ocurrencias = 0;
					aux = aux.next; //cuando se acaba el penultimo se entra aqui y se pone en el ultimo y cuando se acabe el ultimo pasará el aux.next al siguiente que sera null antes de llamar al hasnext
				}
			return element;
		}

		

	}
	////// FIN ITERATOR
	
	public LinkedQueueWithRepImpl() { //CONSTRUCTOR: Vacio, con lo cual count se inicializa a 0 por ser tipo basico y el front a null cuando se crea una lista enlazada
		}

	/////////////
	
	
	
	@Override
	public void add(T element) {
		
		if(element == null) {
			throw new NullPointerException();
		}
		
		this.add(element, 1);
		
	}
	
	@Override
	public void add(T element, int times) {
		
		//Comprobacion de la entrada:
		
			if(element == null) {
				throw new NullPointerException();
			}
			if(times <= 0) {
				throw new IllegalArgumentException();
			}
		
				
		//Vamos a aniadir el elemento, distinguimos que no este sea por que la lista este vacia o no o que este
		
			QueueWithRepNode<T> nuevo = new QueueWithRepNode<T>(element, times); 
			QueueWithRepNode<T> aux = this.front;  
			
			if(isEmpty() == true) { //la lista esta vacia
				
				this.front = nuevo;
				this.count++;
				
			}else {  //La lista no esta vacia
				
				if(this.contains(element) == false) {	//no esta el elemento, lo aniadimos por el final
				
					while(aux.next != null) { //miro si hay siguiente nodo, cuando no lo haya, no muevo el auxiliar una posicion porque me iria fuera, entonces lo dejo y ya lo tengo en el ultimo
						aux = aux.next;
					}
				
					//PRECONDICION: aux apunta al ultimo
				aux.next = nuevo;
				this.count++;

				}else { //si que esta en la cola, hay que buscarlo
					
					while(element.equals(aux.elem) == false) { //mientras que no lo encuentre que siga, PORQUE SE QUE ESTA EN LA COLA
						
						aux = aux.next; //mientras que no lo encuentre que siga recorriendo					
						
					}
				
					//PRECONDIDION: tengo mi aux en el que es igual
				
					aux.num = aux.num + times;			
				}
			}
	}


	@Override
	public void remove(T element, int times) {

		//verificacion de la entrada
		
		if(element == null) {
			throw new NullPointerException();
		}		
		if(times < 0) {
			throw new IllegalArgumentException();
		}else if(times == 0) {
			return; 
		}
				
		//ahora sabemos que las times son positivas y el elemento no es null
		//si la lista esta vacia ya lo gestiona el contains, entonces vamos a ver el caso en que no esta vacia
		
		if(contains(element) == true) { //si el elemento lo contiene vamos a recorrer hasta encontrarlo y ver su times
			
			QueueWithRepNode<T> aux = this.front;
			
			while(element.equals(aux.elem) == false) {
				aux = aux.next;			
				
			}
			
			//ya lo encontró
			
			if(times >= aux.num) { //si se quieren eliminar mas instancias de las que hay o las que hay eso no puede ser
				throw new IllegalArgumentException();
			}else {
				
				aux.num = aux.num - times;
				
			}
			
		}else {
			throw new NoSuchElementException(); //si el elemento no esta en la cola
		}
		
		
	}

	
	@Override
	public boolean contains(T element) {
		
		//Comprobacion de la Entrada
		if(element == null) {
			throw new NullPointerException();
		}
		
		//Entrada correcta
		
		QueueWithRepNode<T> aux = this.front;
		boolean testigo = false; //por si es vacia la cola		
		
		if(isEmpty() == false) {  //si la lista esta vacia directamente no va a estar
			
			while(aux != null) {
				
				if(element.equals(aux.elem)) { //lo ha encontrado
					testigo = true;
					break;
				}			
				aux = aux.next; //si el elemento del nodo no es igual desplazo el auxiliar al siguiente de la cola hasta llegar a encontrarlo o al final de la cola en cuyo caso se retorna el valor de inicializacion del testigo
			}
			
		}	
		
		return testigo;
				
	}

	@Override
	public long size() {
		
		int contador = 0;
		
		if(isEmpty() == false) { //si la cola no esta vacia
			
			QueueWithRepNode<T> aux = this.front;
			
			while(aux != null) {
				
				contador = contador + aux.num; //voy almacenando en el contador las instancias de todos los elementos
				aux = aux.next;
				
			}			
		}
				
		return contador;	
	}

	@Override
	public boolean isEmpty() {
		
		boolean testigo = false;
		
		if(this.count == 0) {
			testigo = true;
		}
		
		return testigo;
	}

	@Override
	public int remove() throws EmptyCollectionException {

		int instancias = 0;
		
		if(isEmpty() == true) {
			throw new EmptyCollectionException("La cola está vacía");
		}
		instancias = front.num;
		this.front = this.front.next;
		this.count--;
				
		return instancias;
		
		
	}

	@Override
	public void clear() {
		this.front = null;
		this.count = 0;	

	}

	@Override
	public int count(T element) {

		//verifico la entrada
		if(element == null) {
			throw new NullPointerException();
		}
				
		//sabiendo que la entrada es correcta

		QueueWithRepNode<T> aux = this.front;
		int instancias = 0;
		
		while(aux != null) { //recorro elemento a elemento y cuando de con el eque es igual salvo las instancias. Si es vacia o no lo encuentra es 0 que es con lo que la he inicializado
			
			if(element.equals(aux.elem)) {
				instancias = aux.num;
				break;
				
			}
			
			aux = aux.next;
			
		}
		
		return instancias;
	
	}
	
	@Override
	public Iterator<T> iterator() {
		//Vamos a crear el iterador, para ello llamamos al constructor de la clase y le pasaremos la referencia al primero con la cual podremos recorrer toda la lista de uno en uno
		LinkedQueueWithRepIterator<T> iterador = new LinkedQueueWithRepIterator<T>(this.front);
		return iterador;
	
	
	}


	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("(");
		

		QueueWithRepNode<T> aux = this.front;
				
		while(aux != null) { //recorro nodo a nodo la cola
		
			//en cada nodo, salvo sus instancias y con un for pinto tantas veces como diga su numero de instancias antes de pasar al siguiente nodo
			int instancias = aux.num;
			
			for(int i = 0; i < instancias; i++) {
				
				buffer.append(aux.elem + " ");
				
			}
		
			aux = aux.next;	
		}
			
		buffer.append(")");
		return buffer.toString();
	}

	


}