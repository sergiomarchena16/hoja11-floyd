/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author SergioMarchena
 */
public class Grafos {
    
    private int[][] matrizAdjacencia= new int[100][100];
	private int[][] matrizRutas= new int[100][100];
	private int tamano=0;
	private ArrayList diccionario= new ArrayList();
	
	public void agregar(String ciudad, String destino, int kilometros){
		if (diccionario.indexOf(ciudad)==-1){
			diccionario.add(ciudad);
			tamano++;
		}
		if (diccionario.indexOf(destino)==-1){
			diccionario.add(destino);
			tamano++;
		}
		int x = diccionario.indexOf(ciudad);
		int y = diccionario.indexOf(destino);
		matrizAdjacencia[x][y]=kilometros;
	}
	
	public void Grafo() throws IOException {
		for(int x=0;x<100;x++){
			for(int y=0;y<100;y++){
				matrizAdjacencia[x][y]=999999999;
			}
		}
		String palabras="";
		BufferedReader br = new BufferedReader(new FileReader("guategrafo.txt"));//leer archivo diccionario.txt
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();										//leer linea
		    while (line != null) {												//si hay linea
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        String[] words = line.split("\\s+");
		        agregar(words[0],words[1],Integer.parseInt(words[2]));
		        line = br.readLine();											//leer linea
		    }
		} finally {
		    br.close();
		}
	}
	
	public String buscarRuta(String ciudad, String destino) throws IOException {
		ArrayList ruta = new ArrayList();
		matrizRutas=matrizAdjacencia;
		for (int k=0;k<tamano;k++){
			for (int j=0;j<tamano;j++){
				for (int i=0;i<tamano;i++){
					if(matrizRutas[i][j]>(matrizRutas[i][k] + matrizRutas[k][j])){
						matrizRutas[i][j]=(matrizRutas[i][k] + matrizRutas[k][j]);
						ruta.add(diccionario.get(k));
					}
				}
			}
		}
		String cadena="";
		for (int i=0;i<ruta.size()-1;i++){
			cadena+="-->"+ruta.get(i);
		}
		cadena+="\n Recorido total: "+matrizRutas[diccionario.indexOf(ciudad)][diccionario.indexOf(destino)];
		return cadena;
	}
	
	public String buscarCentro() throws IOException{
		String centro=(String)diccionario.get(0);
		int menor=999999999;
		for(int i=0;i<tamano-1;i++){
			int suma=0;
			for(int j=0;j<tamano-1;j++){
				suma+=matrizRutas[i][j];
			}
			if (suma<=menor){
				menor=suma;
				centro=(String)diccionario.get(i);
			}
		}
		return centro;
	}
	
	public void Interrupcion(String ciudad1, String ciudad2)throws IOException{
		matrizAdjacencia[diccionario.indexOf(ciudad1)][diccionario.indexOf(ciudad2)]=999999999;
	}
	
	public void Crear(String ciudad1, String ciudad2,int distancia)throws IOException{
		matrizAdjacencia[diccionario.indexOf(ciudad1)][diccionario.indexOf(ciudad2)]=distancia;
	}
    
}
