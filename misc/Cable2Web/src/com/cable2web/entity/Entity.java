package com.cable2web.entity;

import com.cable2web.html.HtmlNode;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.imageio.ImageIO;

/**
 * 
 * @author Mark Owen
 */
public class Entity
{
	/**
	 * 
	 */
	public Entity()
	{
		mName = null;
		mChildren = new ArrayList<Entity>();
		mText = null;
		mImage = null;
		mStyle = null;

		mDefWidth = 0;
		mDefHeight = 0;

		mOffEvents = new ArrayList<String>();
		mOnEvents = new ArrayList<String>();
		mDownEvents = new ArrayList<String>();		
	}

	/**
	 * 
	 * @return 
	 */
	public HtmlNode getHtml()
	{
		HtmlNode html = new HtmlNode();
		html.setName("div");
		html.setStyle("left", String.valueOf(getLeft()) + "px");
		html.setStyle("top", String.valueOf(getTop()) + "px");
		html.setStyle("width", String.valueOf(getWidth()) + "px");
		html.setStyle("height", String.valueOf(getHeight()) + "px");

		if ( hasImage() )
			html.setStyle("background-image", "url('" + getImage() + "')");
		if ( hasStyle() )
			html.setAttribute("class", getStyle());

		if ( hasOffEvents() )
			html.setAttribute("onMouseOut", getOffEventsString());
		if ( hasOnEvents() )
			html.setAttribute("onMouseOver", getOnEventsString());
		if ( hasDownEvents() )
		{
			if ( hasOnEvents() )
				html.setAttribute("onMouseUp", getOnEventsString());
			else
				html.setAttribute("onMouseUp", getOffEventsString());
			html.setAttribute("onMouseDown", getDownEventsString());
		}

		if ( hasChildren() )
		{
			for(Entity child : children())
				html.addChild(child.getHtml());
		}
		else if ( hasText() )
			html.setText(getText());
	
		return html;
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
	 * @param parent 
	 */
	public void setParent(Entity parent)
	{
		mParent = parent;
	}

	/**
	 * 
	 * @return 
	 */
	public Entity getParent()
	{
		return mParent;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setLeft(int value)
	{
		mLeft = value;
	}

	/**
	 * 
	 * @return 
	 */
	public int getLeft()
	{
		return mLeft;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setTop(int value)
	{
		mTop = value;
	}

	/**
	 * 
	 * @return 
	 */
	public int getTop()
	{
		return mTop;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setWidth(int value)
	{
		mWidth = value;
	}

	/**
	 * 
	 * @return 
	 */
	public int getWidth()
	{
		return mWidth;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setDefaultWidth(int value)
	{
		mDefWidth = value;
	}

	/**
	 * 
	 * @return 
	 */
	public int getDefaultWidth()
	{
		return mDefWidth;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setHeight(int value)
	{
		mHeight = value;
	}

	/**
	 * 
	 * @return 
	 */
	public int getHeight()
	{
		return mHeight;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setDefaultHeight(int value)
	{
		mDefHeight = value;
	}

	/**
	 * 
	 * @return 
	 */
	public int getDefaultHeight()
	{
		return mDefHeight;
	}

	/**
	 * 
	 * @param child 
	 */
	public void addChild(Entity child)
	{
		child.setParent(this);
		mChildren.add(child);
	}

	/**
	 * 
	 * @param index
	 * @param child 
	 */
	public void setChild(int index, Entity child)
	{
		mChildren.set(index, child);
	}

	/**
	 * 
	 * @param index
	 * @return 
	 */
	public Entity getChild(int index)
	{
		return mChildren.get(index);
	}
	
	/**
	 * 
	 * @param name
	 * @return 
	 */
	public Entity getChildByName(String name)
	{
		for(Entity child : mChildren)
		{
			if ( child.getName() != null && child.getName().equals(name) )
				return child;
		}
		return null;
	}

	/**
	 * 
	 * @return 
	 */
	public int getChildCount()
	{
		return mChildren.size();
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
	public Collection<Entity> children()
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
	 * @return 
	 */
	public boolean hasImage()
	{
		return mImage != null;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setImage(String value)
	{
		try
		{
			Image image = ImageIO.read(new File(value));
			mDefWidth = image.getWidth(null);
			mDefHeight = image.getHeight(null);
		}
		catch ( IOException ex )
		{
		}

		mImage = value;
	}

	/**
	 * 
	 * @return 
	 */
	public String getImage()
	{
		return mImage;
	}

	/**
	 * 
	 * @return 
	 */
	public boolean hasStyle()
	{
		return mStyle != null;
	}

	/**
	 * 
	 * @param value 
	 */
	public void setStyle(String value)
	{
		mStyle = value;
	}

	/**
	 * 
	 * @return 
	 */
	public String getStyle()
	{
		return mStyle;
	}	
	
	/**
	 * 
	 * @return 
	 */
	public boolean hasOffEvents()
	{
		return !mOffEvents.isEmpty();
	}

	/**
	 * 
	 * @param eventText 
	 */
	public void addOffEvent(String eventText)
	{
		mOffEvents.add(eventText);
	}
	
	/**
	 * 
	 * @return 
	 */
	public String getOffEventsString()
	{
		StringWriter writer = new StringWriter();
		for(String event : mOffEvents)
			writer.write(event);
		return writer.toString();
	}

	/**
	 * 
	 * @return 
	 */
	public boolean hasOnEvents()
	{
		return !mOnEvents.isEmpty();
	}

	/**
	 * 
	 * @param eventText 
	 */
	public void addOnEvent(String eventText)
	{
		mOnEvents.add(eventText);
	}

	/**
	 * 
	 * @return 
	 */
	public String getOnEventsString()
	{
		StringWriter writer = new StringWriter();
		for(String event : mOnEvents)
			writer.write(event);
		return writer.toString();
	}
	
	/**
	 * 
	 * @return 
	 */
	public boolean hasDownEvents()
	{
		return !mDownEvents.isEmpty();
	}

	/**
	 * 
	 * @param eventText 
	 */
	public void addDownEvent(String eventText)
	{
		mDownEvents.add(eventText);
	}

	/**
	 * 
	 * @return 
	 */
	public String getDownEventsString()
	{
		StringWriter writer = new StringWriter();
		for(String event : mDownEvents)
			writer.write(event);
		return writer.toString();
	}

	/**
	 * 
	 */
	private String mName;
	
	/**
	 * 
	 */
	private Entity mParent;
	
	/**
	 * 
	 */
	private int mLeft;
	
	/**
	 * 
	 */
	private int mTop;
	
	/**
	 * 
	 */
	private int mWidth;
	
	/**
	 * 
	 */
	private int mDefWidth;
	
	/**
	 * 
	 */
	private int mHeight;
	
	/**
	 * 
	 */
	private int mDefHeight;
	
	/**
	 * 
	 */
	private ArrayList<Entity> mChildren;
	
	/**
	 * 
	 */
	private String mText;
	
	/**
	 * 
	 */
	private String mImage;
	
	/**
	 * 
	 */
	private String mStyle;

	/**
	 * 
	 */
	ArrayList<String> mOffEvents;

	/**
	 * 
	 */
	ArrayList<String> mOnEvents;

	/**
	 * 
	 */
	ArrayList<String> mDownEvents;
}
