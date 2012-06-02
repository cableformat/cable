package com.cable2web.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Mark Owen
 */
public class HtmlNode
{
	/**
	 * 
	 */
	public HtmlNode()
	{
		mAttributes = new HashMap<String, String>();
		mStyles = new HashMap<String, String>();
		mChildren = new ArrayList<HtmlNode>();
		mName = null;
		mText = null;
	}

	/**
	 * 
	 * @param name 
	 */
	public void setName(String name)
	{
		mName = name;
	}

	/**
	 * 
	 * @return 
	 */
	public String getName()
	{
		return mName;
	}

	/**
	 * 
	 * @param name
	 * @param value 
	 */
	public void setAttribute(String name, String value)
	{
		mAttributes.put(name, value);
	}

	/**
	 * 
	 * @param name
	 * @return 
	 */
	public String getAttribute(String name)
	{
		return mAttributes.get(name);
	}

	/**
	 * 
	 * @param name
	 * @param value 
	 */
	public void setStyle(String name, String value)
	{
		mStyles.put(name, value);
	}

	/**
	 * 
	 * @param name
	 * @return 
	 */
	public String getStyle(String name)
	{
		return mStyles.get(name);
	}

	/**
	 * 
	 * @param child 
	 */
	public void addChild(HtmlNode child)
	{
		mChildren.add(child);
	}

	/**
	 * 
	 * @param index
	 * @return 
	 */
	public HtmlNode getChild(int index)
	{
		return mChildren.get(index);
	}

	/**
	 * 
	 * @return 
	 */
	public boolean hasAttributes()
	{
		return !mAttributes.isEmpty();
	}

	/**
	 * 
	 * @return 
	 */
	public boolean hasStyles()
	{
		return !mStyles.isEmpty();
	}

	/**
	 * 
	 * @return 
	 */
	public boolean hasChildren()
	{
		return !mChildren.isEmpty();
	}
	
	/**
	 * 
	 * @return 
	 */
	public Collection<Entry<String, String>> attributes()
	{
		return mAttributes.entrySet();
	}
	
	/**
	 * 
	 * @return 
	 */
	public Collection<Entry<String, String>> styles()
	{
		return mStyles.entrySet();
	}

	/**
	 * 
	 * @return 
	 */
	public Collection<HtmlNode> children()
	{
		return mChildren;
	}

	/**
	 * 
	 * @return 
	 */
	public boolean hasText()
	{
		return mText != null;
	}

	/**
	 * 
	 * @param text 
	 */
	public void setText(String text)
	{
		mText = text;
	}

	/**
	 * 
	 * @return 
	 */
	public String getText()
	{
		return mText;
	}

	/**
	 * 
	 */
	public String mName;

	/**
	 * 
	 */
	private HashMap<String, String> mAttributes;

	/**
	 * 
	 */
	private HashMap<String, String> mStyles;

	/**
	 * 
	 */
	private ArrayList<HtmlNode> mChildren;

	/**
	 * 
	 */
	private String mText;
}
