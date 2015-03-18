import com.ntkachov.channel.Channel;
import com.ntkachov.channel.MyAnnotation;


public class Main {

    int myInt;
    public int myPublicInt;
    private int myPrivateInt;
    protected int myProtectedInt;

    public void setMyInt(int i){
        myInt = i;
    }

    @MyAnnotation
    public static void main(String[] args) {
	    System.out.println("main ");
        new Main().setMyInt(5);
    }

    @Channel({"myInt", "myPublicInt", "myProtectedInt", "myPrivateInt"})
    private void runAfterMyInt(){
        System.out.println(myInt);
    }
}
