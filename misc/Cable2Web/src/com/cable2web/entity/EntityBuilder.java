package com.cable2web.entity;

import com.cable.CableNode;
import com.cable.CableValue;
import java.util.ArrayList;

/**
 *
 * @author Mark Owen
 */
public class EntityBuilder
{
	/**
	 * 
	 */
	public EntityBuilder()
	{
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
	 * @param cd
	 * @return 
	 */
	public Entity buildFromCable(CableNode cd, ArrayList<String> cssFiles)
	{
		try
		{
			mCssFiles = cssFiles;
			Entity ent = new Entity();
			initEntity(ent, cd);
			return ent;
		}
		catch ( EntityBuilderException ex )
		{
			mError = ex.getMessage();
			return null;
		}
	}

	/**
	 * 
	 * @param entity
	 * @param cableData 
	 */
	private void initEntity(Entity entity, CableNode cableData) throws EntityBuilderException
	{
		if ( cableData.isSet("name") )
			entity.setName(cableData.get("name").asString());

		if ( cableData.isSet("style") )
			initStyle(entity, cableData);
		if ( cableData.isSet("image") )
			initImage(entity, cableData);

		initLeftAndWidth(entity, cableData);
		initTopAndHeight(entity, cableData);

		if ( cableData.hasChildren() )
		{
			if ( cableData.isSet("text") )
				throw new EntityBuilderException("entity cannot have both children and inner text");

			for(CableNode child : cableData.children())
			{
				if ( child.getName().equals("css") )
				{
					if ( !child.isSet("url") )
						throw new EntityBuilderException("css node requires a url");

					mCssFiles.add(child.get("url").asString());
					continue;
				}

				Entity childEnt = new Entity();
				entity.addChild(childEnt);
				initEntity(childEnt, child);
			}
		}
		else if ( cableData.isSet("text") )
			entity.setText(cableData.get("text").asString());
	}

	/**
	 * 
	 * @param entity
	 * @param cableData
	 * @throws EntityBuilderException 
	 */
	private void initLeftAndWidth(Entity entity, CableNode cableData) throws EntityBuilderException
	{
		boolean hasLeft;
		int left;

		boolean hasRight;
		int right;

		boolean hasWidth;
		int width;

		if ( cableData.isSet("left") )
		{
			hasLeft = true;
			CableValue value = cableData.get("left");
			if ( value.isString() )
				left = resolveLink(entity, value.asString());
			else
				left = (int)value.asNumeric();
		}
		else
		{
			hasLeft = false;
			left = 0;
		}

		if ( cableData.isSet("right") )
		{
			hasRight = true;
			CableValue value = cableData.get("right");
			if ( value.isString() )
				right = resolveLink(entity, value.asString());
			else
				right = (int)value.asNumeric();
		}
		else
		{
			hasRight = false;
			right = 0;
		}

		if ( cableData.isSet("width") )
		{
			hasWidth = true;
			CableValue value = cableData.get("width");
			if ( value.isString() )
				width = resolveLink(entity, value.asString());
			else
				width = (int)value.asNumeric();
		}
		else
		{
			hasWidth = false;
			width = 0;
		}

		if ( hasLeft )
		{
			if ( hasWidth )
			{
				if ( hasRight )
				{
					throw new EntityBuilderException("left, right and width are ALL set");
				}
				else
				{
					entity.setLeft(left);
					entity.setWidth(width);
				}
			}
			else
			{
				if ( hasRight )
				{
					entity.setLeft(left);
					entity.setWidth(right - left);
				}
				else
				{
					entity.setLeft(left);
					entity.setWidth(entity.getDefaultWidth());
				}				
			}
		}
		else
		{
			if ( hasWidth )
			{
				if ( hasRight )
				{
					entity.setLeft(right - width);
					entity.setWidth(width);
				}
				else
				{
					entity.setLeft(0);
					entity.setWidth(width);
				}
			}
			else
			{
				if ( hasRight )
				{
					entity.setLeft(right - entity.getDefaultWidth());
					entity.setWidth(entity.getDefaultWidth());
				}
				else
				{
					entity.setLeft(0);
					entity.setWidth(entity.getDefaultWidth());
				}				
			}			
		}
	}

	/**
	 * 
	 * @param entity
	 * @param cableData
	 * @throws EntityBuilderException 
	 */
	private void initTopAndHeight(Entity entity, CableNode cableData) throws EntityBuilderException
	{
		boolean hasTop;
		int top;
		
		boolean hasBottom;
		int bottom;
		
		boolean hasHeight;
		int height;
		
		if ( cableData.isSet("top") )
		{
			hasTop = true;
			CableValue value = cableData.get("top");
			if ( value.isString() )
				top = resolveLink(entity, value.asString());
			else
				top = (int)value.asNumeric();
		}
		else
		{
			hasTop = false;
			top = 0;
		}

		if ( cableData.isSet("bottom") )
		{
			hasBottom = true;
			CableValue value = cableData.get("bottom");
			if ( value.isString() )
				bottom = resolveLink(entity, value.asString());
			else
				bottom = (int)value.asNumeric();
		}
		else
		{
			hasBottom = false;
			bottom = 0;
		}

		if ( cableData.isSet("height") )
		{
			hasHeight = true;
			CableValue value = cableData.get("height");
			if ( value.isString() )
				height = resolveLink(entity, value.asString());
			else
				height = (int)value.asNumeric();
		}
		else
		{
			hasHeight = false;
			height = 0;
		}
		
		if ( hasTop )
		{
			if ( hasHeight )
			{
				if ( hasBottom )
				{
					throw new EntityBuilderException("top, bottom and height are ALL set");
				}
				else
				{
					entity.setTop(top);
					entity.setHeight(height);
				}
			}
			else
			{
				if ( hasBottom )
				{
					entity.setTop(top);
					entity.setHeight(bottom - top);
				}
				else
				{
					entity.setTop(top);
					entity.setHeight(entity.getDefaultHeight());
				}				
			}
		}
		else
		{
			if ( hasHeight )
			{
				if ( hasBottom )
				{
					entity.setTop(bottom - height);
					entity.setHeight(height);
				}
				else
				{
					entity.setTop(0);
					entity.setHeight(height);
				}
			}
			else
			{
				if ( hasBottom )
				{
					entity.setTop(bottom - entity.getDefaultHeight());
					entity.setHeight(entity.getDefaultHeight());
				}
				else
				{
					entity.setTop(0);
					entity.setHeight(entity.getDefaultHeight());
				}				
			}			
		}
	}
	
	/**
	 * 
	 * @param cd
	 * @param entity
	 */
	private void initStyle(Entity entity, CableNode cd)
	{
		String[] text = cd.get("style").asString().split(":");
		if ( text.length > 1 )
		{
			String offStyle = text[1].replace("$", "off");
			entity.setStyle(offStyle);
			entity.addOffEvent("this.className='" + offStyle + "';");
			
			if ( text[0].indexOf('o') != -1 )
				entity.addOnEvent("this.className='" + text[1].replace("$", "on") + "';");
			if ( text[0].indexOf('d') != -1 )
				entity.addDownEvent("this.className='" + text[1].replace("$", "down") + "';");
		}
		else
			entity.setStyle(text[0]);
	}

	/**
	 * 
	 * @param cd
	 * @param entity  
	 */
	private void initImage(Entity entity, CableNode cd)
	{
		String[] text = cd.get("image").asString().split(":");
		if ( text.length > 1 )
		{
			String offImage = text[1].replace("$", "off");
			entity.setImage(offImage);
			entity.addOffEvent("this.style.backgroundImage='url(" + offImage + ")';");
			
			if ( text[0].indexOf('o') != -1 )
				entity.addOnEvent("this.style.backgroundImage='url(" + text[1].replace("$", "on") + ")';");
			if ( text[0].indexOf('d') != -1 )
				entity.addDownEvent("this.style.backgroundImage='url(" + text[1].replace("$", "down") + ")';");
		}
		else
			entity.setImage(text[0]);
	}	
	
	/**
	 * 
	 * @param ent
	 * @param text
	 * @return 
	 */
	private int resolveLink(Entity ent, String text) throws EntityBuilderException
	{
		String[] col = text.split(":");
		String[] dot = col[0].split("\\.");
		
		if ( dot.length != 2 )
			throw new EntityBuilderException("invalid property");

		int value;
		if ( dot[0].equals("parent") )
		{
			if ( dot[1].equals("left") )
				value = 0;
			else if ( dot[1].equals("top") )
				value = 0;
			else if ( dot[1].equals("right") || dot[1].equals("width") )
				value = ent.getParent().getWidth();
			else if ( dot[1].equals("bottom") || dot[1].equals("height") )
				value = ent.getParent().getHeight();
			else if ( dot[1].equals("center-x") )
				value = ent.getParent().getWidth() / 2;
			else if ( dot[1].equals("center-y") )
				value = ent.getParent().getHeight() / 2;
			else
				throw new EntityBuilderException("invalid property name " + dot[1]);
		}
		else
		{
			Entity child = ent.getParent().getChildByName(dot[0]);
			if ( child == null )
				throw new EntityBuilderException("unknown entity '" + dot[0] + "'");
			
			if ( dot[1].equals("left") )
				value = child.getLeft();
			else if ( dot[1].equals("top") )
				value = child.getTop();
			else if ( dot[1].equals("right") )
				value = child.getLeft() + child.getWidth();
			else if ( dot[1].equals("width") )
				value = child.getWidth();
			else if ( dot[1].equals("bottom") )
				value = child.getTop() + child.getHeight();
			else if ( dot[1].equals("height") )
				value = child.getHeight();
			else if ( dot[1].equals("center-x") )
				value = child.getLeft() + child.getWidth() / 2;
			else if ( dot[1].equals("center-y") )
				value = child.getTop() + child.getHeight() / 2;
			else
				throw new EntityBuilderException("invalid property name " + dot[1]);
		}
		
		if ( col.length == 2 )
			value += Integer.parseInt(col[1]);
		
		return value;
	}

	/**
	 * 
	 * @param filename 
	 */
	private void addCssFile(String filename)
	{
		mCssFiles.add(filename);
	}
	
	/**
	 * 
	 */
	private String mError;

	/**
	 * 
	 */
	ArrayList<String> mCssFiles;
}
