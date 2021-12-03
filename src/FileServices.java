/**
 * Class FileServices: provides a variety of methods for reading and writing to files
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileServices implements Serializable
{
	String fileNameAndPath; 

	public FileServices()
	{
		fileNameAndPath = ""; 
	}
	/**
	 * Contructor for instance variable fileNameAndPath
	 * @param fileNameAndPath (A string) for the file path and name
	 */
	public FileServices(String fileNameAndPath)
	{
		this.fileNameAndPath = fileNameAndPath; 
	}
	/**
	 * Method getFileNameAndPath: return the data from the instance variable fileNameAndPath
	 * @return - (a String) the name of the file and path
	 */
	public String getFileNameAndPath()
	{
		return fileNameAndPath; 
	}
	/**
	 * Method setFileNameAndPath: 
	 */
	public void setFileNameAndPath(String fileNameAndPath)
	{
		this.fileNameAndPath = fileNameAndPath; 
	}
	/**
	 * Method createFile: Creates a file in which data can be stored
	 */
	public void createFile() throws IOException
	{
		try
		{
			Path filePath = Paths.get(this.fileNameAndPath); 
			Files.createFile(filePath); 
		}
		catch (FileAlreadyExistsException fae)
		{
			System.out.println("File"+ this.fileNameAndPath + " Already Exists."); 
		}
	}
	/**
	 * Method writeStringToFile: Writes a string to a created file
	 * @param contents - (a String) writes the contents passed to the explicit parameter
	 * to the file name provided by the instance variable fileNameAndPath
	 * @return - none
	 * @throws IOException
	 */
	public void writeStringToFile(String contents) throws IOException
	{
		Path path = Paths.get(this.fileNameAndPath); 
		Files.write(path, contents.getBytes("UTF-8")); 
	}
	/**
	 * Method readFile: Converts data from a file to a String and returns this data 
	 * @return - (a String) data from a file
	 */
	public String readFile() throws IOException
	{
		//read data into a string and return string
		String contents = new String(Files.readAllBytes(Paths.get(this.fileNameAndPath))); 
		return contents; 
	}
	/*
	 * Method readFileForNumbers: Return an int[] from an anticipated 3 digit value within a file
	 */
	public int[] readFileForNumbers() throws IOException
	{
		 /*
		  * Character parsing author: Anjana
		  * https://www.tutorialspoint.com/Check-if-a-string-contains-a-number-using-Java
		  */
		 List<Integer> nums = new ArrayList<Integer>();
		 try (BufferedReader br = new BufferedReader(new FileReader(fileNameAndPath)))
	 	 {
	 		 String textLine;
	 		 
	 		 while ((textLine = br.readLine()) != null)
	 		 {
	 			 char[] characters = textLine.toCharArray();
	 			 StringBuilder sb = new StringBuilder();
	 			 for (char c : characters)
	 			 {
	 				 if(Character.isDigit(c))
	 				 {
	 					 sb.append(c);
	 				 }
	 				 
	 			 }
	 			 int number = Integer.valueOf(sb.toString());
	 			 System.out.println(number);
	 			 
	 			 nums.add(number);
	 		 } 
	 		 if (nums.size() > 0)
	 		 {
	 			 int[] numbers = new int[nums.size()];
	 			 
		 		 for (int i = 0; i < nums.size(); i++)
		 		 {
		 			 numbers[i] = nums.get(i);
		 		 }
		 		 return numbers; 
	 		 }
	 		 else {
	 			 int[] numbers = {};
	 			 return numbers;
	 		 }
	 	 }
		 catch (Exception e)
		 {
			 System.out.println("Error. " + e);
		 }
		 int[] numbers = {};
		 return numbers;
	}
	/*
	 * Method readFileForCharacters: Returns a char[] from any alphabetical letters within a file
	 */
	public char[] readFileForLetters()throws IOException
	{
		/*
		  * Character parsing author: Anjana
		  * https://www.tutorialspoint.com/Check-if-a-string-contains-a-number-using-Java
		  */
		 List<Character> letters = new ArrayList<Character>();
		 try (BufferedReader br = new BufferedReader(new FileReader(fileNameAndPath)))
	 	 {
	 		 String textLine;
	 		 
	 		 while ((textLine = br.readLine()) != null)
	 		 {
	 			 char[] characters = textLine.toCharArray();
	 			 for (char c : characters)
	 			 {
	 				 if(Character.isLetter(c))
	 				 {
	 					 letters.add(c);
	 					 System.out.println(c);
	 				 }
	 			 }
	 		 } 
	 		 if (letters.size() > 0)
	 		 {
	 			 char[] letterArr = new char[letters.size()];
		 		 for (int i = 0; i < letters.size(); i++)
		 		 {
		 			 letterArr[i] = letters.get(i);
		 		 }
		 		 return letterArr; 
	 		 }
	 		 else {
	 			 char[] letterArr = {};
	 			 return letterArr;
	 		 }
	 	 }
		 catch (Exception e)
		 {
			 System.out.println("Error. " + e);
		 }
		 char[] letterArr = {};
	     return letterArr;
	}
	/**
	 * Method readLastHalf: Reads data from the remaining half of a file
	 * @param - none
	 * @return - the file data
	 */
	public String readLastHalf() throws IOException
	{
		String fileData = ""; 
		RandomAccessFile rana = new RandomAccessFile(this.fileNameAndPath, "rw"); 
		int halfOfFile = (int) (rana.length() / 2); 
		
		for (long pointer = halfOfFile; pointer >= rana.length(); pointer++)
		{
			rana.seek(pointer);
			fileData = fileData + rana.read(); 
		}
		return fileData; 
	}
	/**
	 * Method readLastHalf: overrides readLastHalf() to allow the user to pass the fileName
	 * displays the length of a file, and half of the length of the file
	 * @param fileName - (a String) the fileName
	 * @return - none
	 * @throws IOException
	 */
	public void readLastHalf(String fileName) throws IOException
	{
		String fileData = ""; 
		RandomAccessFile rana = new RandomAccessFile(fileName, "rw"); 
		
		System.out.println("File size " + rana.length()); 
		int halfOfFile = (int) (rana.length() / 2); 
		System.out.println("Half of File size " + halfOfFile); 
		
		for (long pointer = halfOfFile; pointer >= rana.length(); pointer++)
		{
			rana.seek(pointer);
			fileData = fileData + rana.read(); 
		}
		rana.close();
	}
	/**
	 * Method createDirectory: creates a new directory
	 * @param dirName - (a String) the name of the directory to be created
	 * @return - none
	 */
	public void createDirectory(String dirName) throws IOException
	{
		Path dirPath = Paths.get(dirName); 
		
		try 
		{
			Files.createDirectory(dirPath); 
		}
		catch (FileAlreadyExistsException fae)
		{
			System.out.println("Directory"+ dirName + " Already Exists."); 
		}
	}
	/**
	 * Method deleteFile: deletes a file
	 * @param file - (a String) the name of the file to be deleted
	 * @return - none
	 */
	public void deleteFile(String file) throws IOException
	{
		Path path = Paths.get(file); 
		
		if(Files.exists(path))
		{
			Files.delete(path);
			System.out.println("File Deleted."); 
		}
		else
		{
			System.out.println("File does not exist"); 
		}
	}
	/**
	 * Method deleteFile: overrides the deleteFile()method, 
	 * deletes the instance variable file: fileNameAndPath
	 * @param - none
	 * @return - none
	 * @throws IOException
	 */
	public void deleteFile() throws IOException
	{
		Path path = Paths.get(this.fileNameAndPath); 
		
		if(Files.exists(path))
		{
			Files.delete(path);
			System.out.println("File Deleted."); 
		}
		else
		{
			System.out.println("File does not exist"); 
		}
	}
	/**
	 * Method moveFile: moves the location of a file
	 * @param from - (a String, the fileName) the name/location of the file to be moved
	 * @param to - (a String, the folder/fileName) the new location of the file
	 * @throws IOException
	 */
	public void moveFile(String from, String to) throws IOException
	{
		Path fromPath = Paths.get(from); 
		Path toPath = Paths.get(to); 
		
		try
		{
			Files.move(fromPath, toPath); 
		}
		catch (FileAlreadyExistsException fae)
		{
			System.out.println("File "+ from + " Already Exists."); 
		}
	}
	/**
	 * Method copyFile: makes a copy of a file
	 * @param from - (String, the fileName) a path to copy from
	 * @param to - (String, the folder/fileName) the path to copy to
	 * @throws IOException
	 */
	public void copyFile(String from, String to) throws IOException
	{
		Path fromPath = Paths.get(from); 
		Path toPath = Paths.get(to); 
		
		try
		{
			Files.copy(fromPath, toPath); 
		}
		catch (FileAlreadyExistsException fae)
		{
			System.out.println("File "+ from + " Already Exists."); 
		}
	}
	/**
	 * Method writeObject: writes the data of an Object to a file
	 * @param object - (an Object) the object to be written
	 * @return - none
	 * @throws IOException
	 */
	public void writeObject(Object object) throws IOException
	{
		if (fileExists())
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.fileNameAndPath)); 
			out.writeObject(object);
			//System.out.println("...data was written. New file not created."); 
			out.close(); 
		}
		else
		{
			createFile(); 
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.fileNameAndPath));  
			out.writeObject(object);
			//System.out.println("...data was written. New file was created."); 
			out.close(); 
		}
	}
	/**
	 * Method readObject: reads the data from an Object
	 * @return - (an Object) the Serializable information of the object 
	 * @throws IOException
	 */
	public Object readObject() throws ClassNotFoundException, IOException
	{
		File f = new File(this.fileNameAndPath); 

		if (f.exists())
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.fileNameAndPath));
			return (Object) in.readObject(); 
		} 
		return "No Object Found"; 
	}
	/**
	 * Method fileExists: determines if the instance variable fileNameAndPath already exists
	 * @return - (a boolean) true if the file is found, otherwise false
	 * @throws - IOException
	 */
	public boolean fileExists() throws IOException
	{
		File f = new File(fileNameAndPath); 
		
		if (f.exists())
		{
			return true; 
		}
		return false; 
	}
}
