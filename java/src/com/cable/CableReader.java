/*
 * CableReader.java - A utility used for converting Cable text into a tree of nodes
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class used for reading text (either from a string or a file) and building
 * a tree of nodes.
 * @author Mark Owen
 */
public class CableReader
{
	/**
	 * Returns true if the specified character is a whitespace.
	 * @param ch The character to test
	 * @return True if test passes
	 */
	private static boolean isws(char ch)
	{
		if ( ch == ' ' )
			return true;
		if ( ch == '\t' )
			return true;
		if ( ch == '\n' )
			return true;
		return false;
	}

	/**
	 * Returns true if the specified character is a letter or underscore.
	 * @param ch The character to test
	 * @return True if test passes
	 */
	private static boolean islu(char ch)
	{
		if ( ch >= 'a' && ch <= 'z' )
			return true;
		if ( ch >= 'A' && ch <= 'Z' )
			return true;
		if ( ch == '_' )
			return true;
		return false;
	}

	/**
	 * Returns true if the specified character is a letter, underscore or number.
	 * @param ch The character to test
	 * @return True if test passes
	 */
	private static boolean islun(char ch)
	{
		if ( ch >= 'a' && ch <= 'z' )
			return true;
		if ( ch >= 'A' && ch <= 'Z' )
			return true;
		if ( ch == '_' )
			return true;
		if ( ch >= '0' && ch <= '9' )
			return true;
		return false;
	}

	/**
	 * Returns true if the specified character is a number.
	 * @param ch The character to test
	 * @return True if test passes
	 */
	private static boolean isn(char ch)
	{
		if ( ch >= '0' && ch <= '9' )
			return true;
		return false;
	}
	
	/**
	 * Converts a string's escape character to their actual value.
	 * @param in The String that needs to be converted
	 * @return The converted string
	 */
	private static String convertFromEscapedString(String in)
	{
		int begin = 0;
		int index = 0;
		int length = in.length();
		String out = "";
		
		while ( index < length )
		{
			if ( in.charAt(index) == '\\' )
			{
				out += in.substring(begin, index);
				index++;
				switch(in.charAt(index))
				{
					case '\\': out += "\\"; break;
					case '\"': out += "\""; break;
					case 'n': out += "\n"; break;
					case 't': out += "\t"; break;
					case 'r': out += "\r"; break;
				}
				index++;
				begin = index;
			}
			else
				index++;
		}
		out += in.substring(begin, index);
		
		return out;
	}
	
	/**
	 * Initializes the CableReader.
	 */
	public CableReader()
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
	 * Reads a Cable string and converts it into a tree of nodes.
	 * @param source The Cable string that needs to be converted
	 * @return The root of the tree
	 */
	public CableNode read(String source)
	{
		mSource = source;
		mTokens = new ArrayList<CableToken>();
		
		try
		{
			tokenize();
			reset();
			return parse();
		}
		catch ( CableException ex )
		{
			mError = ex.getMessage();
			return null;
		}
	}
	
	/**
	 * Reads the Cable text from a file and converts it into a tree of nodes.
	 * @param filename The name of the file
	 * @return The root of the tree
	 */
	public CableNode readFromFile(String filename)
	{
		CableNode node;
		try
		{
			FileInputStream fin = new FileInputStream(filename);
			byte[] data = new byte[fin.available()];
			fin.read(data);
			node = read(new String(data));
			fin.close();
		}
		catch ( IOException ex )
		{
			node = null;
		}
		return node;
	}

	/**
	 * Converts the source text into an array of tokens.
	 */
	private void tokenize() throws CableException
	{
		int length = mSource.length();
		int index = 0;

		while ( index < length )
		{
			char c = mSource.charAt(index);

			if ( isws(c) )
			{
				do {
					index++;
				} while ( index < length && isws(mSource.charAt(index)) );
			}
			else if ( c == '#' )
			{
				do {
					index++;
				} while ( index < length && mSource.charAt(index) != '\n' );
			}
			else if ( islu(c) )
			{
				int b = index;
				do {
					index++;
				} while ( index < length && islun(mSource.charAt(index)) );
				
				String value = mSource.substring(b, index);
				if ( value.equals("true") )
					mTokens.add(new CableToken("true", CableToken.Type.True));
				else if ( value.equals("false") )
					mTokens.add(new CableToken("false", CableToken.Type.False));
				else
					mTokens.add(new CableToken(value, CableToken.Type.Word));
			}
			else if ( isn(c) || c == '.' )
			{
				int b = index;
				do {
					index++;
				} while ( index < length && isn(mSource.charAt(index)) );
				if ( index < length && mSource.charAt(index) == '.' )
				{
					do {
						index++;
					} while ( index < length && isn(mSource.charAt(index)) );					
				}
				
				mTokens.add(new CableToken(mSource.substring(b, index), CableToken.Type.Numeric));
			}
			else if ( c == '\"' )
			{
				int b = index;
				do {
					index++;
				} while ( index < length && !(mSource.charAt(index) == '\"' && mSource.charAt(index-1) != '\\') );
				if ( index >= length )
					throw new CableException("closing \" not found");
				index++;

				mTokens.add(new CableToken(convertFromEscapedString(mSource.substring(b+1, index-1)), CableToken.Type.String));
			}
			else if ( c == '=' )
			{
				index++;
				mTokens.add(new CableToken("=", CableToken.Type.Set));
			}
			else if ( c == ';' )
			{
				index++;
				mTokens.add(new CableToken(";", CableToken.Type.End));
			}
			else if ( c == '{' )
			{
				index++;
				mTokens.add(new CableToken("{", CableToken.Type.OpenBracket));
			}
			else if ( c == '}' )
			{
				index++;
				mTokens.add(new CableToken("}", CableToken.Type.CloseBracket));
			}
			else
				throw new CableException("invalid token");
		}
	}

	/**
	 * Parses the array of tokens and builds the tree.
	 * @return The current node
	 * @throws CableException
	 */
	private CableNode parse() throws CableException
	{
		if ( current().getType() != CableToken.Type.Word )
			throw new CableException("invalid node identifier");
		
		CableNode node = new CableNode(current().getValue());
		next();

		while ( current().getType() == CableToken.Type.Word )
		{
			String name = current().getValue();
			next();
			if ( current().getType() != CableToken.Type.Set )
				throw new CableException("expecting = after property identifier");
			
			next();
			if ( current().getType() == CableToken.Type.True )
				node.setBool(name, true);
			else if ( current().getType() == CableToken.Type.False )
				node.setBool(name, false);
			else if ( current().getType() == CableToken.Type.String )
				node.setString(name, current().getValue());
			else if ( current().getType() == CableToken.Type.Numeric )
				node.setNumeric(name, Double.parseDouble(current().getValue()));
			else
				throw new CableException("expecting bool, numeric or string as property value");
			
			next();
		}
		
		if ( current().getType() == CableToken.Type.End )
		{
			next();
			return node;
		}
		
		if ( current().getType() == CableToken.Type.OpenBracket )
		{
			next();
			while ( current().getType() != CableToken.Type.CloseBracket )
				node.addChild(parse());
			next();
		}
		
		return node;
	}

	/**
	 * Resets the position of the cursor.
	 */
	private void reset()
	{
		mCursor = 0;
	}

	/**
	 * Returns true if the cursor is at the end of the array of tokens.
	 * @return True if cursor is at the end
	 */
	private boolean done()
	{
		return mCursor >= mTokens.size();
	}

	/**
	 * Returns the current token.
	 * @return The current token
	 * @throws CableException
	 */
	private CableToken current() throws CableException
	{
		if ( done() )
			throw new CableException("end not expected");
		return mTokens.get(mCursor);
	}
	
	/**
	 * Moves the cursor to the next token.
	 */
	private void next()
	{
		mCursor++;
	}
	
	/**
	 * The source text
	 */
	private String mSource;

	/**
	 * The array of tokens
	 */
	private ArrayList<CableToken> mTokens;
	
	/**
	 * The index of the cursor
	 */
	private int mCursor;
	
	/**
	 * The error message
	 */
	private String mError;
}
