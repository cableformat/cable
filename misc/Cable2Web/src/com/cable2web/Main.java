package com.cable2web;

import com.cable.CableNode;
import com.cable.CableReader;
import com.cable2web.entity.Entity;
import com.cable2web.entity.EntityBuilder;
import com.cable2web.html.HtmlWriter;
import java.util.ArrayList;

/**
 * 
 * @author Mark Owen
 */
public class Main
{
	/**
	 * 
	 * @param text 
	 */
	public static void info(String text)
	{
		System.out.println("info: " + text);
	}
	
	/**
	 * 
	 * @param text 
	 */
	public static void error(String text)
	{
		System.err.println("error: " + text);
	}
	
	/**
	 * 
	 * @param text 
	 */
	public static void warn(String text)
	{
		System.out.println("warn: " + text);
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		if ( args.length != 2 )
		{
			error("invalid arguments");
			return;
		}

		try
		{
			CableReader cr = new CableReader();
			CableNode cd = cr.readFromFile(args[0]);

			ArrayList<String> cssFiles = new ArrayList<String>();
			EntityBuilder eb = new EntityBuilder();
			Entity ent = eb.buildFromCable(cd, cssFiles);

			HtmlWriter hr = new HtmlWriter();
			if ( !hr.writeToFile(args[1], ent.getHtml(), cssFiles) )
				error(hr.getError());
		}
		catch ( Exception ex )
		{
			error(ex.getMessage());
		}
	}
}
