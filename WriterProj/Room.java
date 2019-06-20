/**
 * This is the room class
 * It deals with each session as an object of the Room class
 * Each room has a name and the number of people in it
 * When you add a person to a room, it
 *                                  adds one to the amount of people
 *                                  checks if the room is full, if yes,set the boolean to true
 *                                  adds the person to the String ArrayList attendance
**/

package WriterProj;
import java.util.*;
/**
 *
 * @author eqiu
 */
public class Room {
    private String className;
    private boolean full=false;
    private int numPerson=0;
    private final int MAXIMUM_CAPACITY;
    private ArrayList <String> attendance = new ArrayList<>();

    public Room(String a){
        className=a;
        if(a.equals("Multi-purpose")||a.equals("Choir Room"))
            MAXIMUM_CAPACITY=30;
        else
            MAXIMUM_CAPACITY=18;
    }

    public Room(String a, int i){
        className=a;
        MAXIMUM_CAPACITY=i;
    }

    public void setName(String s){
        className=s;
    }
    public String getName(){
        return className;
    }
    public int getit(){
        return MAXIMUM_CAPACITY;
    }

    public boolean addPerson(Student s){
        if(!full){
            numPerson++;
            attendance.add(s.getName());
            System.out.println("added " + s.getName() + " to " + getName());
            if(numPerson>=MAXIMUM_CAPACITY)
                full=true;
            return true;
        }
        else{
            System.out.println("Room " + className + " is full.");
            return false;
        }
    }

    public ArrayList<String> getAttendance(){
        return attendance;
    }

}
