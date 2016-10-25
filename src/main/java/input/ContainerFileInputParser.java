package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.gson.Gson;

import entity.Container;

public class ContainerFileInputParser {

	public Container readFile(File file) throws FileNotFoundException{
		Scanner s = new Scanner(file);
		String content = s.useDelimiter("\\Z").next();
		s.close();
		System.out.println(content);
		
		return new Gson().fromJson(content, Container.class);
		
	}
	
}
