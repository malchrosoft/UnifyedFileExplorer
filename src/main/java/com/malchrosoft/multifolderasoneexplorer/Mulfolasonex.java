/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malchrosoft.multifolderasoneexplorer;

import com.malchrosoft.debug.Log;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author amalchrowicz
 */
public class Mulfolasonex
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Log.get().setLevel(extractLogLevelFromArguments(args, Log.Level.DEBUG));
		Log.info("Current LOG LEVEL selected 1 line before : in " + Mulfolasonex.class.getName());
		Log.debug("Working directory : " + new File("").getAbsolutePath());

		try ( Stream<Path> paths = Files.walk(Paths.get("/home/amalchrowicz/Images")))
		{
			paths
				.filter(Files::isRegularFile)
				.filter((Path f) -> f.toString().endsWith(".png"))
				.forEach(System.out::println);
		} catch (IOException ex)
		{
			Logger.getLogger(Mulfolasonex.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		
		try ( Stream<Path> paths = Files.walk(Paths.get("smb://malchro-nas.local/fichiers/Aymeric")))
		{
			paths
				.filter(Files::isRegularFile)
				.filter((Path f) -> f.toString().endsWith(".pdf"))
				.forEach(System.out::println);
		} catch (IOException ex)
		{
			Logger.getLogger(Mulfolasonex.class.getName()).log(Level.SEVERE, null, ex);
		}

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			String lafName;
			for (int i = 0; i < UIManager.getInstalledLookAndFeels().length; i++)
			{
				UIManager.LookAndFeelInfo laf = UIManager.getInstalledLookAndFeels()[i];
				lafName = laf.getName();
				Log.debug("   -> l&f[" + i + "] : " + lafName);
				if (lafName.contains("Nimbus"))
				{
					UIManager.setLookAndFeel(laf.getClassName());
					Log.debug("      * Nimbus exists !");
				}
			}
			Log.info("Look and feel : ******** " + UIManager.getLookAndFeel().getName() + " ********");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException ex)
		{
			Log.error(ex);
		}

		Log.info("Load main frame");

		MulfolasonexMainFrame mainFrame = new MulfolasonexMainFrame();
		
		java.awt.EventQueue.invokeLater(() -> mainFrame.setVisible(true));

	}

	public void stop()
	{

	}

	private static Log.Level extractLogLevelFromArguments(String[] args, Log.Level defaultLevel)
	{
		for (String arg : args)
		{
			if (arg.startsWith("-logLevel") || arg.startsWith("-log") || arg.startsWith("-ll") || arg.startsWith("-l"))
			{
				String[] splitted = arg.split("=");
				if (splitted.length > 1)
				{
					String value = splitted[1];
					Log.Level level = Log.Level.valueOf(value.toUpperCase());
					if (level != null) return level;
					switch (value.toUpperCase())
					{
						case "D":
							return Log.Level.DEBUG;
						case "I":
							return Log.Level.INFO;
						case "W":
							return Log.Level.WARN;
						case "E":
							return Log.Level.ERROR;
						case "NOT":
							return Log.Level.NO_LOG;
					}
				}
			}
		}
		return defaultLevel;
	}

}
