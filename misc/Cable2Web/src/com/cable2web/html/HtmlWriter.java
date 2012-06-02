package com.cable2web.html;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 *
 * @author Mark Owen
 */
public class HtmlWriter
{
	/**
	 * 
	 */
	public HtmlWriter()
	{
		mError = null;
	}

	/**
	 * 
	 * @return 
	 */
	public String getError()
	{
		return mError;
	}

	/**
	 * 
	 * @param filename
	 * @param node
	 * @return 
	 */
	public boolean writeToFile(String filename, HtmlNode node, ArrayList<String> cssFiles)
	{
		try
		{
			mWriter = new FileWriter(filename);
			mWriter.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
			mWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
			mWriter.write("\t<head>\n");
			mWriter.write("\t\t<title>Affinity Counts</title>\n");
			mWriter.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"></meta>\n");
			for(String cssFile : cssFiles)
				mWriter.write("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssFile + "\"></link>\n");
			mWriter.write("\t</head>\n");
			mWriter.write("\t<body>\n");
			writeNode(node, "\t\t");
			mWriter.write("\t</body>\n");
			mWriter.write("</html>\n");			
			mWriter.close();
		}
		catch ( Exception ex )
		{
			mError = ex.getMessage();
			return false;
		}
		
		return true;		
	}

	/**
	 * 
	 * @param node
	 * @throws IOException 
	 */
	private void writeNode(HtmlNode node, String indent) throws Exception
	{
		mWriter.write(indent + "<" + node.getName());
		if ( node.hasStyles() )
		{
			mWriter.write(" style=\"position:absolute;");
			for(Entry<String, String> style : node.styles())
				mWriter.write(style.getKey() + ":" + style.getValue() + ";");
			mWriter.write("\"");
		}
		if ( node.hasAttributes() )
		{
			for(Entry<String, String> attr : node.attributes())
				mWriter.write(" " + attr.getKey() + "=\"" + attr.getValue() + "\"");
		}
		mWriter.write(">");

		if ( node.hasChildren() )
		{
			if ( node.hasText() )
				throw new Exception("cannot write html, node has children and inner text");

			mWriter.write("\n");
			for(HtmlNode child : node.children())
				writeNode(child, indent + "\t");
			mWriter.write(indent + "</" + node.getName() + ">\n");
		}
		else
		{
			if ( node.hasText() )
				mWriter.write(node.getText());
			mWriter.write("</" + node.getName() + ">\n");
		}
	}

	/**
	 * 
	 */
	FileWriter mWriter;

	/**
	 * 
	 */
	String mError;
}
