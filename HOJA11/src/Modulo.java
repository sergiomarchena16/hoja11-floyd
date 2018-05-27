
import java.util.ArrayList;
/**
 *
 * @author SergioMarchena
 */
public class Modulo {
    
    private String nombre;
    private ArrayList<String> ciudad2;
    private ArrayList<Double> distancia;
    
    public Modulo(String actual, ArrayList<String> ciudad2, ArrayList<Double> km){
        nombre = actual;
        this.ciudad2 = ciudad2;
        distancia = km;
    }
    public String getNombre(){
        return nombre;
    }
    public ArrayList<Double> getKm() {
        return distancia;
    }
    public ArrayList<String> getDestinos(){
        return ciudad2;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
    public void setKm(int index) {
        distancia.set(index, Double.POSITIVE_INFINITY);
    }   
}