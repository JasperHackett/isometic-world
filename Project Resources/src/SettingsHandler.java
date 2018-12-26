import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author Jasper
 *
 */
public class SettingsHandler {
	
	//Load settings from file
		public void loadSettings() {
			List<String> settingsList = new ArrayList<String>();
			
			try {
				File settingsFile = new File("settings.txt");
				FileReader fileReader = new FileReader(settingsFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String lineIterator;
				try {
					lineIterator = bufferedReader.readLine();
		
					while(lineIterator != null) {
						settingsList.add(lineIterator);
						lineIterator = bufferedReader.readLine();
					}
					bufferedReader.close();
				} catch (IOException e) {
					System.out.println("Unable to read settings.txt properly");
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("Unable to find settings.txt file");
			}
			
//			bufferedReader.close();
			int settingsDelimiterPos; //Stores position of ':' in a setting String
			
			//Iterate through settings.txt checking for entries that match: [valid setting name]:[setting value] and applying them
			for(String setting : settingsList) {
				if((settingsDelimiterPos = setting.indexOf(":") ) != -1) {
					switch(setting.substring(0, settingsDelimiterPos)){
						
					case ("windowheight"):
							Game.height = Integer.parseInt(setting.substring(settingsDelimiterPos+1));
						break;
					case ("windowwidth"):
						Game.width = Integer.parseInt(setting.substring(settingsDelimiterPos+1));
						break;
					case ("worldwidth"):
						Game.worldWidth = Integer.parseInt(setting.substring(settingsDelimiterPos+1));
						break;
					case ("worldheight"):
						Game.worldHeight = Integer.parseInt(setting.substring(settingsDelimiterPos+1));
						break;
					
					
					default:
						System.out.println("Invalid setting"); //Occurs when a setting contains a ':' but no valid setting name or value
						break;
					
					}
				}
			}
			System.out.println("Loaded settings.txt");
		}
		
		

}
