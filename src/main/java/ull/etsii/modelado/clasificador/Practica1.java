package ull.etsii.modelado.clasificador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ull.etsii.modelado.clasificador.herramientas.Aleatorio;
import ull.etsii.modelado.clasificador.herramientas.MatrizConfusion;
import ull.etsii.modelado.clasificador.modelo.*;
import ull.etsii.modelado.clasificador.modelo.Clasificador.MetodoClasificacion;
import ull.etsii.modelado.clasificador.modelo.Clasificador.TipoDistancia;
import ull.etsii.modelado.clasificador.modelo.Clasificador.TipoPesadoAtributos;
import ull.etsii.modelado.clasificador.modelo.Clasificador.TipoProcesado;

import java.util.*;

/**
 * Práctica de clasificación que implementa como clase principal la clase Clasificator
 * que utiliza ficheros csv como base de conocimiento para clasificar objetos.
 * @author José Oliver Martínez Novo
 *
 */
public class Practica1 
{
	private static Clasificador m_Clasificator;
	private static String m_NombreFichero;
	private static TipoProcesado m_TipoProcesado;
	private static TipoPesadoAtributos m_TipoPesadoAtributos;
	private static MetodoClasificacion m_MetodoClasificacion;
	private static TipoDistancia m_TipoDistancia;
	private static InputStreamReader in = null;
	private static BufferedReader br = null;
	private static Aleatorio m_Aleatorio = null;
	
	/**
	 * @param args (String) Array de argumentos que acepta el método main.
	 */
	public static void main(String[] args) 
	{	
		new Practica1().run();
	}
	
	public void run()
	{
		m_NombreFichero = "diabetes_training.csv";
		
		m_TipoProcesado = TipoProcesado.NORMALIZACIÓN;
		m_MetodoClasificacion = MetodoClasificacion.DEFECTO;
		m_TipoPesadoAtributos = TipoPesadoAtributos.ESTATICO;
		m_TipoDistancia = TipoDistancia.EUCLIDEA;
		
		String value = null;
		in = new InputStreamReader(System.in);
		br = new BufferedReader(in);
				
		//System.out.print("Introduzca el fichero a cargar...:");
		//System.out.println();
		try 
		{
			/*
			value = br.readLine();
			m_NombreFichero = value;
			
			System.out.print("Introduzca el tipo de procesado, (0 = Normalización, 1 = Estandarización)...:");
			System.out.println();
			value = br.readLine();
			m_TipoProcesado = TipoProcesado.values()[Integer.parseInt(value)];
			
			System.out.print("Introduzca el método de clasificación, (0 = Por defecto, 1 = Cercanía, 2 = Voto fijo)...:");
			System.out.println();
			value = br.readLine();
			
			m_MetodoClasificacion = MetodoClasificacion.values()[Integer.parseInt(value)];
			
			System.out.print("Introduzca el tipo de pesado para los atributos, (0 = Estático, 1 = Dinámico)...:");
			System.out.println();
			value = br.readLine();
			m_TipoPesadoAtributos = TipoPesadoAtributos.values()[Integer.parseInt(value)];
			
			System.out.print("Introduzca el tipo de cálculo para las distancias, (0 = Euclidea, 1 = Manhattan, 2 = Chebychef)...:");
			System.out.println();
			value = br.readLine();
			m_TipoDistancia = TipoDistancia.values()[Integer.parseInt(value)];
			*/
			
			m_Clasificator = new Clasificador(m_NombreFichero, m_TipoProcesado);
			//m_Clasificator.setMetodoClasificacion(m_MetodoClasificacion);
			//m_Clasificator.setTipoPesadoAtributos(m_TipoPesadoAtributos);
			//m_Clasificator.setTipoDistancia(m_TipoDistancia);
			
			//System.out.print("Introduzca nº de vecinos...:");
			//System.out.println();
			//m_Clasificator.setVecinos(Integer.parseInt(br.readLine()));
			m_Clasificator.setVecinos(5);
			
			//System.out.print("Desea crear un conjunto de entrenamiento, [s/n]...:");
			//value = br.readLine();
			value = "s";
			if((value.equals("s")) || (value.equals("S")))
			{
				//System.out.print("Desea crear generar el conjunto de forma aleatoria, [s/n]...:");
				//value = br.readLine();
				value = "n";
				if((value.equals("s")) || (value.equals("S")))
				{
					m_Aleatorio = new Aleatorio(0, m_Clasificator.getMuestras().length-1);
					//System.out.print("Desea introducir una semilla, [s/n]...:");
					//value = br.readLine();
					value = "n";
					if((value.equals("s")) || (value.equals("S")))
					{
						//System.out.print("Introduzca el valor de la semilla...:");
						//value = br.readLine();
						//value = "1234";
						//m_Aleatorio.setSeed(Long.parseLong(value));
					}
					else
					{
						//m_Aleatorio.setSeed(Calendar.getInstance().getTimeInMillis());
					}
					//System.out.print("Introduzca la cantidad de muestras del conjunto...:");
					//value = br.readLine();
					//value = "154";
					//m_Clasificator.crearConjuntoEntrenamiento(m_Aleatorio.generarAleatorios(Integer.parseInt(value)));
					//m_Clasificator.clasificarConjuntoEntrenamiento();
					//System.out.println();
					
					//imprimirResumenDataSet();
					
					//imprimirResultadosConjuntoEntrenamiento();
				}
				else
				{
					//System.out.print("Desea cargar un fichero de pruebas, [s/n]...:");
					//value = br.readLine();
					value = "s";
					if((value.equals("s")) || (value.equals("S")))
					{
						//System.out.print("Introduzca el fichero de pruebas a cargar...:");
						//value = br.readLine();
						value = "diabetes_test.csv";
						m_Clasificator.setConjuntoEntrenamiento(new Clasificador(value, m_TipoProcesado).getMuestras());
						m_Clasificator.clasificarConjuntoEntrenamiento();
						System.out.println();
						
						imprimirResumenDataSet();
						
						imprimirResultadosConjuntoEntrenamiento();
						System.out.println();
						imprimirMuestras(0, m_Clasificator.getVecinos());
						
						
						
						return;
					}
					
					System.out.println("** Nota: nº de orden 1 representa la primera muestra.");
					System.out.print("Introduzca los números de orden de las muestras, " + 
							"separados por comas,\ndel fichero " + m_NombreFichero + 
							" que desea tomar como conjunto de entrenamiento...:");
					System.out.println();
					value = br.readLine();
					String[] values = value.split(",");
					int[] valuesInt = new int[values.length];
					for(int r = 0; r < values.length; r++)
					{
						valuesInt[r] = Integer.parseInt(values[r]);
					}
					if(m_Clasificator.crearConjuntoEntrenamiento(valuesInt))
					{
						m_Clasificator.clasificarConjuntoEntrenamiento();
						imprimirResumenDataSet();
						System.out.println();
						imprimirResultadosConjuntoEntrenamiento();
					}
					else
					{
						System.err.println("No se ha podido generar el conjunto de entrenamiento.");
						return;
					}	
				}
			}
			else
			{
				//pedirValoresMuestra();
			}
			br.close();
			in.close();
		} 
		catch (IOException e) 
		{
			System.err.println(e.getMessage());
			return;
		}
		System.out.println();
	
	}
		
	/**
	 * Método que imprime el resumen de datos de la clase Clasificator.
	 */
	private static void imprimirResumenDataSet()
	{
		System.out.println(m_Clasificator.toString());
	}
		
	/**
	 * Método para pedir al usuario los valores por pantalla y clasificar la muestra.
	 */
	/*
	private static void pedirValoresMuestra()
	{
		int tam = m_Clasificator.getCabeceras().length - 1;
		Atributo[] atributos = new Atributo[tam];
		Muestra m = null; 
		Double value = null;
		String resultado = null;	
		
		for(int r = 0; r < tam; r++)
		{
			System.out.print("Introduzca valor para " + m_Clasificator.getCabeceras()[r] + "...:");
			try 
			{
				value = Double.parseDouble(br.readLine());
				if(value > m_Clasificator.getMuestras()[0].getAtributos()[r].getMaxValue())
				{
					m_Clasificator.normalizarColumna(r, 
							m_Clasificator.getMuestras()[0].getAtributos()[r].getMinValue(), value);
				}
				else if(value < m_Clasificator.getMuestras()[0].getAtributos()[r].getMinValue())
				{
					m_Clasificator.normalizarColumna(r, 
							value, m_Clasificator.getMuestras()[0].getAtributos()[r].getMaxValue());
				}
				Atributo a = new Atributo(m_Clasificator.getCabeceras()[r], value);
				a.setMinValue(m_Clasificator.getMuestras()[0].getAtributos()[r].getMinValue());
				a.setMaxValue(m_Clasificator.getMuestras()[0].getAtributos()[r].getMaxValue());
				if(m_TipoProcesado == TipoProcesado.NORMALIZACIÓN)
				{
					atributos[r] = a.normalizar();
				}
				else
				{
					atributos[r] = a.estandarizar();
				}
			} 
			catch (IOException e) 
			{
				System.err.println(e.getMessage());
				return;
			}
		}
		m = new Muestra();
		m.setAtributos(atributos);
		imprimirResumenDataSet();
		System.out.println();
		resultado = m_Clasificator.knn(m);
		System.out.println();
		System.out.println("* Listado de las " + m_Clasificator.getVecinos() + " muestras más próximas");
		imprimirMuestras(0, m_Clasificator.getVecinos());
		System.out.println(resultado);		
	}
	*/
	
	/**
	 * Imprime los resultados del conjunto de entrenamiento.
	 */
	private static void imprimirResultadosConjuntoEntrenamiento()
	{
		Double porcentaje = null;
		int tam = m_Clasificator.getConjuntoEntrenamiento().length;
		MatrizConfusion mc = new MatrizConfusion(m_Clasificator.getConjuntoEntrenamiento(), 
				m_Clasificator.getDataSetInfo());
		int[][] valores = mc.getDatos();
		
		System.out.println("* Resultados del conjunto de entrenamiento");
		for(int r = 0; r < tam; r++)
		{
			System.out.println("\t" + (r+1) + " - " + m_Clasificator.getConjuntoEntrenamiento()[r].toString() + 
					"\t    " + m_Clasificator.getConjuntoEntrenamiento()[r].isCoincidente());
			/*
			System.out.println("\t* Nº" + m_Aleatorio.getNumerosGenerados()[r] + 
					"\t" + m_Clasificator.getConjuntoEntrenamiento()[r].toString() + 
					"\t    " + m_Clasificator.getConjuntoEntrenamiento()[r].isCoincidente());
			*/
		}
		System.out.println();
		System.out.println("* Matriz  de  confusión");
		
		for(int r = 0; r < valores.length; r++)
		{
			System.out.print("\t");
			
			for(int j = 0; j < valores.length; j++)
			{
				System.out.print(" " + valores[r][j]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("* Leyenda: primera fila y columna de la matriz");
		System.out.println("\t0 = La primera fila indica el tipo real, la primera columna la predicción.");
		
		for(int r = 0; r < mc.getLeyendas().length; r++)
		{
			System.out.println("\t" + (r + 1) + " = " + mc.getLeyendas()[r]);
		}
		porcentaje = (double)mc.getAciertos()/(tam-mc.getEmpates());
		System.out.println();
		System.out.println("* Precisión predictiva del algoritmo");
		System.out.println("\t* Tamaño.........: " + tam);
		System.out.println("\t* Aciertos.......: " + mc.getAciertos());
		System.out.println("\t* Empates........: " + mc.getEmpates());
		System.out.println("\t* Fallos.........: " + mc.getFallos());
		System.out.println("\t* % de acierto...: " + porcentaje*100);
		System.out.println();
	}
	
	/**
	 * Método para imprimir por pantalla las muestras de la clase Clasificator.
	 * @param ini (int) Valor que indica el inicio del array de muestras que se desea obtener.
	 * @param fin (int) Valor que indica el final del array de muestras que se desea obtener.
	 */
 	private static void imprimirMuestras(int ini, int fin)
	{
		System.out.println();
		System.out.println("* Muestras obtenidas");
		for(int r = 0; r < fin; r++)
		{
			System.out.println("\t" + m_Clasificator.getMuestras()[r].getClasificacion() + 
					" - " + m_Clasificator.getMuestras()[r].getDistancia());
			for(Atributo atr : m_Clasificator.getMuestras()[r].getAtributos())
			{
				System.out.println("\t" + atr.toString());		
			}
			System.out.println();
		}
	}
	
}
