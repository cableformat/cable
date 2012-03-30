/*
 * CableValue - A property value of a Cable node
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
 * A mixed value type, used to store the value of a property in a CableNode.
 * @author Mark Owen
 */
public class CableValue
{
	/**
	 * All the Mixed types
	 */
	public enum Type
	{
		Bool,
		Numeric,
		String,
	}
	
	/**
	 * Factory for creating a variable with the specified boolean value.
	 * @param value The value to set
	 * @return The newly created variable
	 */
	public static CableValue createBool(boolean value)
	{
		CableValue var = new CableValue();
		var.setBool(value);
		return var;
	}

	/**
	 * Factory for creating a variable with the specified numeric value.
	 * @param value The value to set
	 * @return The newly created variable
	 */
	public static CableValue createNumeric(double value)
	{
		CableValue var = new CableValue();
		var.setNumeric(value);
		return var;		
	}

	/**
	 * Factory for creating a variable with the specified String value.
	 * @param value The value to set
	 * @return The newly created variable
	 */
	public static CableValue createString(String value)
	{
		CableValue var = new CableValue();
		var.setString(value);
		return var;		
	}

	/**
	 * Initialize the variable as a numeric with the value zero.
	 */
	public CableValue()
	{
		mType = Type.Numeric;
		NumericPtr ptr = new NumericPtr();
		ptr.value = 0.0;
		mPtr = ptr;
	}
	
	@Override
	public String toString()
	{
		return mType + ":" + asString();
	}

	/**
	 * Returns the type of this variable.
	 * @return The type
	 */
	public Type getType()
	{
		return mType;
	}
	
	/**
	 * Returns true if the value of this is of type bool.
	 * @return True if type is bool
	 */
	public boolean isBool()
	{
		return mType == Type.Bool;
	}

	/**
	 * Returns true if the value of this is of type numeric.
	 * @return True if type is numeric
	 */
	public boolean isNumeric()
	{
		return mType == Type.Numeric;
	}

	/**
	 * Returns true if the value of this is of type string.
	 * @return True if type is string
	 */
	public boolean isString()
	{
		return mType == Type.String;
	}

	/**
	 * Set this variable with the specified value.
	 * @param value The value to set
	 * @throws Error
	 */
	public void set(CableValue value) throws Error
	{
		switch(value.mType)
		{
			case Bool: setBool(value.asBool()); break;
			case Numeric: setNumeric(value.asNumeric()); break;
			case String: setString(value.asString()); break;
		}
	}

	/**
	 * Sets this variable with the specified boolean value.
	 * @param value The value to set
	 */
	public void setBool(boolean value)
	{
		mType = Type.Bool;
		BoolPtr ptr = new BoolPtr();
		ptr.value = value;
		mPtr = ptr;
	}

	/**
	 * Sets this variable with the specified numeric value.
	 * @param value The value to set
	 */
	public void setNumeric(double value)
	{
		mType = Type.Numeric;
		NumericPtr ptr = new NumericPtr();
		ptr.value = value;
		mPtr = ptr;
	}

	/**
	 * Sets this variable with the specified String value.
	 * @param value The value to set
	 */
	public void setString(String value)
	{
		mType = Type.String;
		mPtr = value;
	}
	
	/**
	 * Returns this value as a boolean.
	 * @return This value as a boolean
	 * @throws Error
	 */
	public boolean asBool()
	{
		switch(mType)
		{
			case Bool: return ((BoolPtr)mPtr).value;
			case Numeric: return ((NumericPtr)mPtr).value != 0 ? true : false;
			case String: return ((String)mPtr).length() > 0 ? true : false;
		}

		return false;
	}
	
	/**
	 * Returns this value as a numeric.
	 * @return This value as a numeric
	 * @throws Error
	 */
	public double asNumeric() throws NumberFormatException
	{
		switch(mType)
		{
			case Bool: return ((BoolPtr)mPtr).value ? 1.0 : 0.0;
			case Numeric: return ((NumericPtr)mPtr).value;
			case String: return Double.parseDouble((String)mPtr);
		}

		return 0;
	}
	
	/**
	 * Returns this value as a String.
	 * @return This value as a String
	 * @throws Error
	 */
	public String asString() throws Error
	{
		switch(mType)
		{
			case Bool: return ((BoolPtr)mPtr).value ? "true" : "false";
			case Numeric: return String.valueOf(((NumericPtr)mPtr).value);
			case String: return (String)mPtr;
		}
		
		return "";
	}

	/**
	 * Bool Reference
	 */
	private static class BoolPtr { public boolean value; }

	/**
	 * Numeric Reference
	 */
	private static class NumericPtr { public double value; }

	/**
	 * Type code
	 */
	private Type mType;

	/**
	 * Pointer to the actual data
	 */
	private Object mPtr;
}
