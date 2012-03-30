/*
 * Token - Any token found in Cable text
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

/**
 * A class used to store the value of a token, as generated during the parsing
 * process.
 * @author Mark Owen
 */
public class CableToken
{
	/**
	 * The token types
	 */
	public enum Type
	{
		Word,
		String,
		Numeric,
		True,
		False,
		Set,
		End,
		OpenBracket,
		CloseBracket,
	}

	/**
	 * Initialize the token based on the specified value and type.
	 * @param value The token value (ie the text)
	 * @param type The token type
	 */
	public CableToken(String value, Type type)
	{
		mValue = value;
		mType = type;
	}
	
	@Override
	public String toString()
	{
		return mType + ":" + mValue;
	}

	/**
	 * Returns the value of the token.
	 * @return The token value
	 */
	public String getValue()
	{
		return mValue;
	}

	/**
	 * Returns the type of the token
	 * @return The token type
	 */
	public Type getType()
	{
		return mType;
	}

	/**
	 * The value of the token
	 */
	private String mValue;

	/**
	 * The type of the token
	 */
	private Type mType;
}
