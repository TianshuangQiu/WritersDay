/**
 * READ ME
 * Hello, people from the future.
 * I, Tianshuang(Ethan) Qiu, am the author of the original version of the code, written in March, 2019
 * This project is a primitive way to put students in their choices on Writer's Day
 * Feel free to change as you see fit because the working version is backed up on github
 * There are definitely ways that we could implement it, such as the losers concept illustrated here
 * https://stackoverflow.com/questions/6308231/course-assignment-algorithm
 * The roomArr and freeArr should have all the rooms of the session and the rooms of activities included
 * The integer iterators i and j should be set to the lengths of said arrays
 * Should a writer be absent from one of the periods, set the MAXIMUM_CAPACITY to 0 for that room at that period
**/
package WriterProj;
import java.util.*;
import java.io.*;
/**
 *
 * @author eqiu
 */


public class Main {

    static Room[][] SCHEDULE = new Room[3][19];
    static Room[][] ACTIVITIES = new Room[3][15];
    static ArrayList<Student> studentArr;
    static ArrayList<Student> completedStudents = new ArrayList<>();
    //static ArrayList<String> usedNames = new ArrayList<>();
    static PrintWriter printStudent;
    static PrintWriter printRoom;
    static int protectPermaLoop;

    //This method randomly picks one student from the array
    public static Student lottery(){
         return studentArr.remove((int)(studentArr.size()*Math.random()));
    }

    //This method assigns students to an activities room
    public static String assignActivity(int freePeriod, Student student){
        for(Room r:ACTIVITIES[freePeriod])
            if(r.addPerson(student))
                return r.getName();
        System.out.println("Student " + student.getName() + " are NOT placed in an activities session during period " + freePeriod+1);
        return null;
    }

    //This method checks for duplicates within a students schedule
    public static boolean doIHaveTheSameClass(String currentCourse, String[] studentsSchedule){
        for(String selection:studentsSchedule){
            if(!(selection==null) && selection.equals(currentCourse))
                return true;
        }
        return false;
    }

    public static void main(String[]args) throws Exception{

        studentArr = new ArrayList<>();
        printStudent = new PrintWriter(new FileWriter("StudentSchedules.txt"));
        Reader.readStudents();
        studentArr=Reader.writeStudents();

        //Initiating rooms
        String[]roomArr = {"B204", "B113", "B119", "Choir Room", "B151", "B112", "B148", "Multi-purpose", "B147", "B149", "B212", "B215", "B114", "B214", "B150", "B213", "B118", "B116", "B117"};
        for(int j=0;j<19;j++){
            SCHEDULE[0][j] = new Room("PERIOD1 " + roomArr[j]);
            SCHEDULE[1][j] = new Room("PERIOD2 " + roomArr[18-j]);
            SCHEDULE[2][j] = new Room("PERIOD3 " + roomArr[j]);
        }
        String[]freeArr={"B240","B238","B236","B234","B232","B239","B227","B235","A221","A219","A218","A215","A213","A209","A207"};
        for(int i=0;i<15;i++){
            ACTIVITIES[0][i] = new Room("FREEPERIOD-" + freeArr[i]);
            ACTIVITIES[1][i] = new Room("FREEPERIOD-" + freeArr[14-i]);
            ACTIVITIES[2][i] = new Room("FREEPERIOD-" + freeArr[i]);
        }

        while(studentArr.size()>0){
            //declaring variables that will be used
            Student iteration = lottery();
            String iterateName = iteration.getName();
            String firstChoice = iteration.getFirst();
            String secondChoice = iteration.getSecond();
            int coursesPlaced = 0;
            int layerCount =0;

            //checking if the student has submitted the form before
            /*if(usedNames.contains(iterateName))
                continue;*/

            //Iterating through the schedule to see if the class required is available
            outer:
            for(Room layer[]:SCHEDULE){
                if(coursesPlaced==2)
                    break outer;
                inner:
                for(Room s: layer){

                    if(!doIHaveTheSameClass(firstChoice, iteration.getPlan()) &&s.getName().substring(8).equals(firstChoice) && s.addPerson(iteration)){
                        coursesPlaced++;
                        iteration.setPlan(layerCount, firstChoice);
                        break inner;
                    }
                    if(!doIHaveTheSameClass(secondChoice, iteration.getPlan()) && s.getName().substring(8).equals(secondChoice) && s.addPerson(iteration)){
                        coursesPlaced++;
                        iteration.setPlan(layerCount, secondChoice);
                        break inner;
                    }
                    //System.out.println("NOT FOUND IN THIS LAYER");
                }
                layerCount++;
            }

            //Check if the student is in 2 classes
            if(coursesPlaced>=2){
                completedStudents.add(iteration);
                System.out.println("Student " + iteration.getName() + " complete \n");
                continue;
            }

            System.out.println("NOT OPTIMAL: Student " + iterateName + " is placed into a random course");

            //Assign the student to the first open class
            protectPermaLoop=0;
            while(coursesPlaced<2 && protectPermaLoop < 200){
                outer:
                for(int i=0;i<SCHEDULE.length;i++)
                    if(iteration.getPlan()[i]==null)
                        for(int j=0;j<SCHEDULE[i].length;j++)
                            if(!Arrays.asList(iteration.getPlan()).contains(SCHEDULE[i][j].getName())&&SCHEDULE[i][j].addPerson(iteration)){
                                coursesPlaced++;
                                iteration.setPlan(i, SCHEDULE[i][j].getName().substring(8));
                                break outer;
                            }
                protectPermaLoop++;
                if(protectPermaLoop > 197)
                    System.err.println("IMPORTANT: Student " + iteration.getName() + " may have an error");
            }

            //usedNames.add(iteration.getName());
            completedStudents.add(iteration);

            System.out.println("Student " + iteration.getName() + " complete");
            System.out.println();
        }

        //Assgining free periods
        for(Student printedStudent : completedStudents){
            printStudent.println(printedStudent.getName() + ":  ");
            int counter = 0;
            for(String s:printedStudent.getPlan()){
                if(s == null){
                    s = assignActivity(counter, printedStudent);
                    if(s == null)
                        s = "UNSPECIFIED ACTIVITY";
                }
                printStudent.print(s + ", ");
                counter++;
            }
            printStudent.println();
        }
        printStudent.close();

        //Printing a roster for the rooms
        printRoom = new PrintWriter(new FileWriter("RoomRoster.txt"));
        for(Room[] outLayer:SCHEDULE)
            for(Room room:outLayer){
                ArrayList<String> printBuffer = room.getAttendance();
                printRoom.println(room.getName());
                for(String s:printBuffer)
                    printRoom.print(s + " ");
                printRoom.println();
                printRoom.println();
            }

        printRoom.println("----------------------------------------------BELOW ARE ACTIVITY ROOMS----------------------------------------------------------");

        for(Room[] outLayer : ACTIVITIES)
            for(Room room:outLayer){
                ArrayList<String> printBuffer = room.getAttendance();
                printRoom.println(room.getName());
                for(String s:printBuffer)
                    printRoom.println(s);
                printRoom.println();
                printRoom.println();
            }
        printRoom.close();
   }
}
