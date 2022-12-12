import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class Base64Test {
	public static void main(String[] args) {
	String filename = null;
	File imageFile = null;
	byte[] fileContent = null;
	String base64Image = null;
	try {
		filename = "C:/Users/200311/Desktop/Hanabi.png";
		imageFile = new File(filename);
		fileContent = Files.readAllBytes(imageFile.toPath());
		base64Image = Base64.getEncoder().encodeToString(fileContent);
		System.out.println(base64Image);
	} catch(Exception e) {
		e.printStackTrace();
	}
}
}
