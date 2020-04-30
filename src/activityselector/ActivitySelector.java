package activityselector;

// Imports
import java.io.*; 
import java.lang.*;
import java.util.*;

/*
Class: Activity Selector
Description: The brain of the entire program. Loads the files, selects the random entry.
    This class was written with the idea in mind that you can close the entire program
    (or even shutdown your computer) and still resume where you left off and not select
    the same activity again because the program doesn't remember anything from a previous
    run. The method used for this might be considered a bit unconventional but it is works!
*/
public class ActivitySelector {
    
    // A linked list that stores the activities after each read
    LinkedList<String> activityList = new LinkedList<String>(); 
    
    // A linked list that stores all chosen activites
    LinkedList<String> chosenList = new LinkedList<String>();
    
    // The file we will read
    File file;
    
    // Constructor: Load 'file' with the specified file when the class was initalized
    public ActivitySelector(File x){
        file = x;
    }
    
    // Open, read, and store the file's contents in this instance
    public void getFile(){
        
        // Clear both arrays
        activityList.clear();
        chosenList.clear();
        
        try{
            // Read the file of activities and convert it to a linked list
            System.out.println("Finding file to read activities...");
            BufferedReader br = new BufferedReader(new FileReader(file));
            System.out.println("File found. Reading into list.");
            
            String st;
            
            // Read each line. If line contains '--'
            // then ignore it since it has been chosen before
            while ((st = br.readLine()) != null) {
                if (!st.contains("--")){
                    activityList.add(st);
                }
            }
            
            // Close the file
            br.close();
            
            // If the activity array list is empty, then all activities have been done
            // So let's do a reset
            if (activityList.size() == 0){
                br = new BufferedReader(new FileReader(file));
                String line;
                String fileContent = "";
                
                // Append the entire list into a String (can be very inefficient 
                // based on size of document)
                while ((line = br.readLine()) != null)
                {
                    fileContent = fileContent + line + System.lineSeparator();
                }
                
                // Remove all the '--' from the file (resetting all activities)
                String newFileContent = fileContent.replaceAll("--", "");

                // Save the file
                FileWriter wr = new FileWriter(file);
                wr.write(newFileContent);

                // Close the reader and the writer
                br.close();
                wr.close();
                
                // Recursive call to get all updated information in the arrays
                // Could be optimized, but it is not worth it for the small lists.
                getFile();
            }
            
            System.out.println("File reading and importing complete!");
        } catch (Exception e){
            System.out.println("Error: Could not open file. \nMaybe the file does not exist or access is not granted?");
            System.exit(0);
        }
    }
    
    // Selects an activity from the list. Once selected, a '--' is added to
    // the front of the activitiy in the text file as a method to prevent
    // the same activity from being selected again
    public void chooseActivity(){
        
        // Setup random number generator
        Random random = new Random();
        
        // Generate random number
        int value = random.nextInt(activityList.size());
        
        // Get the contents of the randomly generated location index
        String rtn = activityList.get(value);
        
        // Remove the activity from the list
        activityList.remove(value);
        
        // Add activity to the chosen list
        chosenList.add(rtn);
    }
    
    // Rewrites everything to the file to keep it up to date between calls.
    public void updateText(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String fileContent = "";
            
            // Read the file in as a string
            while ((line = br.readLine()) != null)
            {
                fileContent = fileContent + line + System.lineSeparator();
            }
            
            // Add '--' infront of any activity in the chosen list that is found
            // in the original document
            for (int i = 0; i < chosenList.size(); i++){
                fileContent = fileContent.replaceAll(chosenList.get(i), "--" + chosenList.get(i));
            }
            
            // Save the file
            FileWriter wr = new FileWriter(file);
            wr.write(fileContent);
            
            // Close the reader and the writer
            br.close();
            wr.close();

        } catch (Exception e){
            System.out.println("Error: Could not update file.");
        }
    }
    
    // Add activity to list | Not functional
    public void addActivity(String activity){
    }
    
    // Delete activity from list | Not functional
    public void deleteActivity(String activity){ 
    }
    
    
}
