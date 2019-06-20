/**
 * This class is the Reader classes
 * As the name suggests, it reads the csv
 * The String "fileName" should be set to the name of the csv file
 * It reads and returns an Array of students with their names, first and second choices initialized.
 * This class is an abstract class because you shouldn't really need to create an object of this class.
**/

package WriterProj;
import java.io.*;
import java.util.*;
/**
 *
 * @author eqiu
 */
public abstract class Reader  {
    private static ArrayList<Student> studentCollection= new ArrayList<>();
    private static String fileName = "Writers' Day (Responses) - Form Responses 1.csv";
    private static List<List<String>> lines=new ArrayList<>();

    public static void readStudents(){
        File file= new File(fileName);
        // this gives you a 2-dimensional array of strings
        Scanner inputStream;
        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line= inputStream.nextLine();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }
            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Student> writeStudents(){
        // the following code lets you iterate through the 2-dimensional array
        int lineNo = 1;
        for(List<String> line: lines) {
            int columnNo = 1;
            for (String value: line) {
                System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                columnNo++;
            }
            if(lineNo>1){
                Student s = new Student();
                s.setName(line.get(1));
                s.setFirst(line.get(2));
                s.setSecond(line.get(3));
                studentCollection.add(s);
            }
            lineNo++;
        }
        System.out.println("READ COMPLETE");
        System.out.println();
        return studentCollection;
    }

}
