/*
 * CableWriter - A utility used for generating Cable text from a tree of nodes
 * Copyright (C) 2012 Mark Owen and Christian McCarty
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package com.cable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map.Entry;

/**
 * A class used to write the Cable text (to either a string or a file) from a tree
 * of nodes.
 * @author Mark Owen
 */
public class CableWriter
{
	/**
	 * Initializes the CableWriter.
	 */
	public CableWriter()
	{
	}

	/**
	 * Returns the error message.
	 * @return The error message
	 */
	public String getError()
	{
		return mError;
	}
	
	/**
	 * Generates the Cable text from the node.
	 * @param node The root of the tree
	 * @return The Cable text
	 */
	public String write(CableNode node)
	{
		mWriter = new StringWriter();
		writeNode(node, "");
		return mWriter.toString();
	}
	
	/**
	 * Generates the Cable text from the node and writes it to the specified file.
	 * @param filename The name of the file
	 * @param node The root of the node
	 * @return True if the write was a success, false otherwise
	 */
	public boolean writeToFile(String filename, CableNode node)
	{
		try
		{
			FileOutputStream out = new FileOutputStream(filename);
			out.write(write(node).getBytes());
			out.close();
		}
		catch ( IOException ex )
		{
			mError = ex.getMessage();
			return false;
		}
		
		return true;
	}
	
	/**
	 * The recursive function used in generating the Cable text.
	 * @param node The current node
	 * @param indent A string with the current indentation
	 */
	private void writeNode(CableNode node, String indent)
	{
		mWriter.write(indent + node.getName());
		if ( node.hasProperties() )
		{
			for(Entry<String, CableValue> entry : node.properties())
			{
				String name = entry.getKey();
				String value;
				if ( entry.getValue().isString() )
					value = "\"" + entry.getValue().asString() + "\"";
				else
					value = entry.getValue().asString();
				
				mWriter.write(" " + name + "=" + value);
			}
		}

		if ( node.hasChildren() )
		{
			mWriter.write("\n" + indent + "{\n");
			for(CableNode childNode : node.children())
				writeNode(childNode, indent + "\t");
			mWriter.write(indent + "}\n");
		}
		else
			mWriter.write(";\n");
	}
	
	/**
	 * The StringWriter used in generating the Cable text
	 */
	private StringWriter mWriter;
	
	/**
	 * The error message
	 */
	private String mError;
}