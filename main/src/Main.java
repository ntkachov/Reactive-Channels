import com.ntkachov.channel.MyAnnotation;


public class Main {

    int myInt;

    public void setMyInt(int i){
        myInt = i;
    }

    @MyAnnotation
    public static void main(String[] args) {
	    System.out.println("main ");
    }
}
