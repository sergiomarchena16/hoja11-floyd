
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author SergioMarchena
 */
public class Principal {
    
    static ArrayList<Modulo> doc = new ArrayList<>();
    static ArrayList<Modulo> initCitiesList = new ArrayList<>();
    static ArrayList<String> cities = new ArrayList<>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("guategrafo.txt"));    
        StringBuilder sb = new StringBuilder();
        String line;
        Scanner userInput = new Scanner(System.in);
        int contador1 = 0;
        try {
            while ((line=br.readLine())!=null) { 
                String initC;
                String des1;
                double kmLong;
                Modulo city2;
                sb.append(line);
                sb.append(System.lineSeparator()); 
                int name = line.indexOf(",");
                initC = line.substring(0, name).toUpperCase();
                String allD = line.substring(name+2, line.length()).toUpperCase();
                int holaa2 = allD.indexOf(",");
                des1 = allD.substring(0, holaa2).toUpperCase();
                kmLong = Double.parseDouble(allD.substring(holaa2+1, allD.length()));
                ArrayList<String> finalDest = new ArrayList<>();
                finalDest.add(des1);
                ArrayList<Double> distancesFinal = new ArrayList<>();
                distancesFinal.add(kmLong);
                city2 = new Modulo (initC, finalDest, distancesFinal);
                doc.add(city2);
                cities.add(initC);
                cities.add(des1);
            } 
            checkInstances();
            RutaFloyd ciudadesGuatemala = new RutaFloyd(initCitiesList.size(), initCitiesList);
            while (contador1 ==0) {
                String exit = "";
                System.out.println("Bienvenido, que opcion desea elegir");
                System.out.println("1. Encontrar la ruta mas corta entre dos ciudades de Guatemala.");
                System.out.println("2. Agregar una ruta nueva.");
                System.out.println("3. Eliminar una ruta entre dos ciudades.");
                System.out.println("4. nada, salir");
                String entry = userInput.nextLine();
                switch (entry){
                    case ("1"):
                        System.out.println("\nciudad de inicio: ");
                        String initCity11 = userInput.nextLine().toUpperCase();
                        System.out.println("\nciudad final: ");
                        String finalCity22 = userInput.nextLine().toUpperCase();
                        shortestPath(ciudadesGuatemala, initCity11, finalCity22);
                        break;
                    case ("2"):
                        System.out.println("\nciudad 1: ");
                        String startCity = userInput.nextLine().toUpperCase();
                        System.out.println("\nciudad 2: ");
                        String endCity = userInput.nextLine().toUpperCase();
                        System.out.println("\ndistancia entre ciudad 1 y ciudad 2: ");
                        double km = Double.parseDouble(userInput.nextLine());
                        ArrayList<String> destinos = new ArrayList<>();
                        destinos.add(endCity);
                        ArrayList<Double> distancias = new ArrayList<>();
                        distancias.add(km);
                        Modulo ciudad = new Modulo (startCity, destinos, distancias);
                        doc.add(ciudad);
                        cities.add(startCity);
                        cities.add(endCity);
                        ciudadesGuatemala.addCiudad(checkInstances());
                        break;
                    case ("3"):
                        System.out.println("\nciudad 1: ");
                        String ci1 = userInput.nextLine().toUpperCase();
                        System.out.println("\nciuadad 2: ");
                        String ci2 = userInput.nextLine().toUpperCase();
                        ciudadesGuatemala.deleteRoute(ci1, ci2);
                        break;
                    case ("4"):
                        contador1 = 1;
                        break;
                }
            }
        }
        finally {
            br.close();
        }  
    }
    static public ArrayList<Modulo> checkInstances() {
        initCitiesList = new ArrayList<>();
        int size = cities.size();
        ArrayList<String> citiesParcial = new ArrayList<>();
        for (String ciudad : cities) {
            citiesParcial.add(ciudad);
        }
        for (String ciudad : cities) {
            if (Collections.frequency(cities, ciudad) > 1){
                citiesParcial.removeAll(Collections.singleton(ciudad));
                citiesParcial.add(ciudad);
            }
        }
        cities = citiesParcial; 
        for (String ciudad : cities){
            ArrayList<String> dest22 = new ArrayList<>();
            ArrayList<Double> dist22 = new ArrayList<>();
            for (Modulo delDocumento: doc){
                if (delDocumento.getNombre().equals(ciudad)) {
                    dist22.add(delDocumento.getKm().get(0));
                    dest22.add(delDocumento.getDestinos().get(0));
                }
            }
            initCitiesList.add(new Modulo(ciudad, dest22, dist22)); 
        }
        return initCitiesList;
    }
    static public void shortestPath(RutaFloyd GuateCities, String initialC, String endCity){
        if (GuateCities.checkDirections(initialC, endCity) == true && (GuateCities.getDistance(initialC, endCity) != 0.0)) {
            System.out.println("\nLa ruta más corta: ");
            ArrayList<String> stopsGuate = GuateCities.getPath(initialC, endCity);
            String printboy = initialC +" - ";
            for (String waitSt: stopsGuate){
                printboy += waitSt + " - ";
            }
            printboy +=" "+endCity;
            System.out.println(printboy);
            System.out.println("Con una distancia de: ");
            System.out.println(GuateCities.getDistance(initialC, endCity)); 
            System.out.println("-------------------------------------------------------------------");
        }
        else {
            System.out.println("\nno hay rutas desde "+ initialC+ " hasta " +endCity);
        }
    }
}
