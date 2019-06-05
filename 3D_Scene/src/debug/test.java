package debug;
import Scene.*;

public class test {
    public Scene.Vector[] vertices;


    public void implement(Vector[] arr){
        Vector[] b = new Vector[vertices.length + arr.length];
        for(int i = 0; i < arr.length; i++){
            b[i + vertices.length] = arr[i];
        }
        vertices = b;
    }

    public static void main(String[] arg){
        test ts = new test();
        ts.vertices = new Scene.Vector[0];
        Scene.Vector[] a = new Scene.Vector[]{
                new Vector(0, 2),
                new Vector(1, 2),
                new Vector(2, 2)
        };
        ts.implement(a);
        System.out.println(ts.vertices[2].toString());

    }
}
