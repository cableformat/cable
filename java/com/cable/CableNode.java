/*
 * CableNode.java - A class used to represent an individual node in the Cable tree
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * An abstract node class used to represent the nodes of a syntax tree.
 * @author Mark Owen
 */
public class CableNode
{
	/**
	 * Initialize the node based on the specified name.
	 * @param name The name of the node
	 */
	public CableNode(String name)
	{
		mName = name;
		mParent = null;
		mIndex = -1;
		mProperties = new HashMap<String, CableValue>();
		mChildren = new ArrayList<CableNode>();
	}

	/**
	 * Returns the name of the node.
	 * @return The name of the node
	 */
	public String getName()
	{
		return mName;
	}

	/**
	 * Sets the parent of this node.
	 * @param parent The parent node
	 */
	public void setParent(CableNode parent)
	{
		mParent = parent;
	}

	/**
	 * Returns the parent node of this node.
	 * @return The parent node
	 */
	public CableNode getParent()
	{
		return mParent;
	}

	/**
	 * Returns true if this node has properties.
	 * @return True if has properties
	 */
	public boolean hasProperties()
	{
		return !mProperties.isEmpty();
	}
	
	/**
	 * Used to determine if the value with the specified name has been set.
	 * @param name The name of the value
	 * @return True if the value has been set, false otherwise
	 */
	public boolean isSet(String name)
	{
		return mProperties.containsKey(name);
	}

	/**
	 * Sets the value with the specified name.
	 * @param name The name of the value
	 * @param value The value to be set
	 */
	public void set(String name, CableValue value)
	{
		mProperties.put(name, value);
	}

	/**
	 * Returns the value with the specified name (or null if it doesn't exist).
	 * @param name The name of the value.
	 * @return The value.
	 */
	public CableValue get(String name)
	{
		return mProperties.get(name);
	}

	/**
	 * Sets the value with the type boolean.
	 * @param name The name of the value
	 * @param value The boolean value
	 */
	public void setBool(String name, boolean value)
	{
		mProperties.put(name, CableValue.createBool(value));
	}	

	/**
	 * Sets the value with the type double.
	 * @param name The name of the value
	 * @param value The double value
	 */
	public void setNumeric(String name, double value)
	{
		mProperties.put(name, CableValue.createNumeric(value));
	}
	
	/**
	 * Sets the value with the type String.
	 * @param name The name of the value
	 * @param value The String value
	 */
	public void setString(String name, String value)
	{
		mProperties.put(name, CableValue.createString(value));
	}
	
	/**
	 * Inserts the specified node above this one.
	 * @param node The node to insert
	 */
	public void insert(CableNode node)
	{
		mParent.setChild(mIndex, node);
		node.addChild(this);
	}
	
	/**
	 * Returns true if this node has children.
	 * @return True if has children
	 */
	public boolean hasChildren()
	{
		return !mChildren.isEmpty();
	}

	/**
	 * Adds a child to this node.
	 * @param child The child node that is to be added
	 */
	public void addChild(CableNode child)
	{
		child.setParent(this);
		child.mIndex = mChildren.size();
		mChildren.add(child);
	}
	
	/**
	 * Removes the child node at the specified index.
	 * @param childIndex The index of the node that is to be removed
	 */
	public void removeChild(int childIndex)
	{
		mChildren.remove(childIndex);
	}

	/**
	 * Sets the child node at the specified index.
	 * @param childIndex The index to set the child
	 * @param child The child node that is to be set
	 */
	public void setChild(int childIndex, CableNode child)
	{
		child.setParent(this);
		child.mIndex = childIndex;
		mChildren.set(childIndex, child);
	}

	/**
	 * Returns the child node at the specified index.
	 * @param childIndex The index of the child node
	 * @return The Child node
	 */
	public CableNode getChild(int childIndex)
	{
		return mChildren.get(childIndex);
	}

	/**
	 * Returns the number of children this node has.
	 * @return The number of children
	 */
	public int getChildCount()
	{
		return mChildren.size();
	}

	/**
	 * Returns a Collection of the properties.
	 * @return Properties Collection
	 */
	public Collection<Entry<String, CableValue>> properties()
	{
		return mProperties.entrySet();
	}
	
	/**
	 * Returns a Collection of the children.
	 * @return Children Collection.
	 */
	public Collection<CableNode> children()
	{
		return mChildren;
	}

	/**
	 * Exports the values in this node to xml.
	 * @return The xml text
	 */
	public String toDebugXml()
	{
		return toDebugXml("");
	}
	
	/**
	 * Internal function used in exporting this node to xml.
	 * @param indent The current indentation in the xml text
	 * @return A portion of the total xml text
	 */
	private String toDebugXml(String indent)
	{
		String xml = indent + "<" + mName;
		for(Entry<String, CableValue> value : mProperties.entrySet())
			xml += " " + value.getKey() + "=\"" + value.getValue().toString() + "\"";
		xml += ">";
		
		if ( mChildren.isEmpty() )
			xml += "</" + mName + ">\n";
		else
		{
			xml += "\n";
			for(int i=0; i<mChildren.size(); i++)
				xml += mChildren.get(i).toDebugXml(indent + "   ");
			xml += indent + "</" + mName + ">\n";
		}
		
		return xml;
	}
	
	/**
	 * The name of this node
	 */
	private String mName;
	
	/**
	 * This node's index in the parent node
	 */
	private int mIndex;
	
	/**
	 * Parent node
	 */
	private CableNode mParent;
	
	/**
	 * Map of values at this node
	 */
	private HashMap<String, CableValue> mProperties;
	
	/**
	 * Array of children nodes
	 */
	private ArrayList<CableNode> mChildren;
}
