/**
 * This class is the Student classes
 * As the name suggests, it deals with individual students
 * Each student is going have a name(email address), a first and second preference
 * The program then generates a schedule for said student and stores it in the String Array plan
**/
package WriterProj;
/**
 *
 * @author eqiu
 */
public class Student {

    private String firstChoice;
    private String secondChoice;
    private String[] plan = new String[3];
    private String name;


    public String getFirst(){
        return firstChoice;
    }
    public void setFirst(String f){
        firstChoice=f;
    }
    public String getSecond(){
        return secondChoice;
    }
    public void setSecond(String s){
        secondChoice=s;
    }
    public String[] getPlan(){
        return plan;
    }
    public void setPlan(int i,String s){
        plan[i]=s;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
