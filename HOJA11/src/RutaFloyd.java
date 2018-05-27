import java.util.ArrayList;
/**
 *
 * @author SergioMarchena
 * 
 */
public class RutaFloyd {
    
    private double[][] matrix1;
    private ArrayList<Modulo> allcities;
    private String[][] distances;
    private int hola;
    
    public RutaFloyd(int hola, ArrayList<Modulo> lista) {
        this.hola = hola;
        allcities = lista;
        makeMatrixes(hola);   
    }
    
    public void addCiudad(ArrayList<Modulo> cities){
        allcities = cities;
        hola ++;
        makeMatrixes(hola);
    }
    public void deleteRoute(String initialC, String finalC){
        for (Modulo city : allcities){
            for (String destino : city.getDestinos()){
                if (city.getNombre().equals(initialC) && destino.equals(finalC)){
                    int index = city.getDestinos().indexOf(destino);
                    city.setKm(index);
                }
            }
        }
        makeMatrixes(hola);
    }
    public void setNodos(int hola2){
        hola = hola2;
    }
    
    public double getDistance(String initCity, String finalCity){
        double minD = 0.0;
        for (int i=0; i<hola; i++){
            for (int j=0; j<hola; j++){
                if (finalCity.equals(allcities.get(j).getNombre()) && initCity.equals(allcities.get(i).getNombre())){
                    minD = matrix1[i][j];
                }
            }
        }
        return minD;
    }
    public final void makeMatrixes(int hola){
        this.matrix1 = new double[hola][hola];
        this.distances = new String[hola][hola];
        for (int i =0;i<hola; i++){
            for (int j =0;j<hola; j++){
                if (i == j){
                    matrix1[i][j] = 0;
                }
                else{
                    matrix1[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        for (int i = 0; i<hola; i++){
            for (int j = 0; j<hola; j++){
                if (i == j){
                    matrix1[i][j] = 0;
                }
                else{
                    for (Modulo city: allcities){
                        for (String destino: city.getDestinos()){
                            int indexof = allcities.get(i).getDestinos().indexOf(destino);
                            if (indexof != -1){
                                if (destino.equals(allcities.get(j).getNombre()) && city.getNombre().equals(allcities.get(i).getNombre())) {
                                    matrix1[i][j] = city.getKm().get(indexof);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i<hola; i++){
            for (int j = 0; j<hola; j++){
                distances[i][j] = allcities.get(j).getNombre();
            }
        }
        Floyd();
    }
    public final void Floyd(){
        for (int k = 0; k < matrix1.length; k++){
            for (int i = 0; i < matrix1.length; i++){
                for (int j = 0; j < matrix1.length; j++){
                    if ((matrix1[i][k] != Double.POSITIVE_INFINITY)&&(matrix1[k][j] != Double.POSITIVE_INFINITY)&&(Math.min(matrix1[i][j], matrix1[i][k] + matrix1[k][j]) != matrix1[i][j])) {
                        matrix1[i][j] = matrix1[i][k] + matrix1[k][j];
                        distances[i][j] = distances[i][k];
                    }
                }
            }
        }
    }
    public boolean checkDirections(String initC, String finalC) {
        boolean ruta = true;
        for (int i=0; i<hola; i++){
            for (int j=0; j<hola; j++) {
                if (finalC.equals(allcities.get(j).getNombre()) && initC.equals(allcities.get(i).getNombre())) {
                    if (matrix1[i][j] == Double.POSITIVE_INFINITY) {
                        ruta = false;
                    }
                }
            }
        }
        return ruta;
    }
    public ArrayList<String> getPath(String initC, String des) {
        ArrayList<String> stops = new ArrayList<>();
        Modulo city1 = null;
        Modulo city2 = null;
        for (Modulo city: allcities) {
            if (city.getNombre().equals(des)) {
                city2 = city;
            }
            else if (city.getNombre().equals(initC)) {
                city1 = city;
            }
        }
        int i = allcities.indexOf(city1);
        int contador =0;
        boolean loop = true; 
        for (int j = allcities.indexOf(city2); j>0; j--) {
            if (!distances[i][j].equals(des)) {
                stops.add(distances[i][j]);
                des = distances[i][j];
                j = searchList(des);
                if (stops.size() > 5) {
                    break;
                }
            }   
            else {
                break;
            }
        }
        return stops;
    }    
    public int searchList(String name) {
        Modulo city1 = null;
        int indexof =0;
        for (Modulo city: allcities) {
            if (city.getNombre().equals(name)) {
                city1 = city;
            }
        }
        indexof = allcities.indexOf(city1);
        return indexof;
    }
}
