
import java.util.ArrayList;
public class MusicTests {
    public static void main(String [] args){
MusicPlayer one = new MusicPlayer();
MusicPlayer two = new MusicPlayer();
        one.insertTrack("a");
        one.insertTrack("b");
        one.insertTrack("c");
        two.insertTrack("d");
        two.insertTrack("e");
        two.insertTrack("f");
        one.moveFirstNode(one, two);
        System.out.println(two.firstTrack());


    }
}
