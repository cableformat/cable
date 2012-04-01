"""
CableWriter - Used for writing Cable text from the nodes
Copyright (C) 2012  Mark Owen and Christian McCarty

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 """ 

class cable:
    
    class CableNode:
        """An abstract node class used to represent the nodes of a syntax tree. 
        Ported from Java.  Many classes are held over to preserve interface
        integrity but are not necessary for fresh Python coding at all.
        """
        
        
        
        def __init__(self, newname):
            """Initialize the node based on the specified name.
	        """
            self.name = str(newname)
            self.parent = None
            self.index = -1
            self.values = {}
            self.children = []    
        
        def insert(self, node):
            """Inserts the specified node above this one.
            """
            if self.parent is not None and type(node) is CableNode:
                self.setChild(self.index, node)
                node.addChild(self)
        
        def addChild(self, child):
            """Adds a child to this node.
            """
            setChild(self, len(children)-1, child)
        
        def setChild(self, childIndex, child):
            """Sets the child node at the specified index.
            """
            if type(child) is str:
                child = CableNode(child)
                
            if type(child) is CableNode:
                child.setParent(self)
                child.index = childIndex
                self.children.append(child)

        def _toDebugXml(self,indent):
            """Internal function used in exporting this node to xml.
            """
            xml = indent + '<' + self.name
            for value in self.values:
                xml += ' ' + value + '="' + self.values[value] + '"'
            xml += '>'
            
            if len(self.children) == 0:
                xml += "</" + self.name + ">\n"
            else:
                xml += '\n'
                for child in self.children:
                    xml += child._toDebugXml(indent + "    ")
                xml += indent + "</" + self.name + ">\n"
            
            return xml
        
        def toDebugXml(self):
            """Exports the values in this node to xml.
            """
            return toDebugXml(self, "")

        
        #kept for interface compatibility for other versions of the Cable implementation; 
        #not actually needed in python
        
        
        def _getName(self):
            """
            Returns the name of the node.
            Interface compatibility from Java version of Cable.
            """
            return self.name
            
        def _setParent(self, newparent):
            """
            Sets the parent of this node.
            Interface compatibility from Java version of Cable.
            """
            self.parent = newparent
            
        def _getParent(self):
            """
            Returns the parent node of this node.
            Interface compatibility from Java version of Cable.
            """
            return self.parent
        
        def _isSet(self, key):
            """
            Used to determine if the value with the specified name[key] has been set.
            Interface compatibility from Java version of Cable.
            """
            if key in self.values:
                return True
            else:
                return False
        
        def _set(self, key, value):
            """
            Sets the value with the specified name[key].
            Interface compatibility from Java version of Cable.
            """
            self.values[key] = value
            
        def _get(self, key):
            """
            Returns the value with the specified name[key] (or [None] if it doesn't exist)
            Interface compatibility from Java version of Cable.
            """
            if key in self.values:
                return self.values[key]
            else:
                return None
                
        def _setBool(self, key, value):
            """
            Sets the value with the type boolean.
            Interface compatibility from Java version of Cable.
            """
            if type(value) is bool:
                self.values[key] = value
        
        def _setNumeric(self, key, value):
            """
            Sets the value with the type double.
            Interface compatibility from Java version of Cable.
            """
            try:
                float(value)
                self.values[key] = value
            except ValueError:
                pass
            
        def _setString(self, key, value):
            """
            Sets the value with the type String.
            Interface compatibility from Java version of Cable.
            """
            if type(value) is str:
                self.values[key] = value
            
        def _getChild(self, childindex):
            """
            Returns the child node at the specified index.
            Interface compatibility from Java version of Cable.
            """
            return self.children[childindex]
            
        def _removeChild(self, childIndex):
            """
            Removes the child node at the specified index.
            Interface compatibility from Java version of Cable.
            """
            del self.children[childIndex]
            
        def _getChildCount(self):
            """
            Returns the number of children this node has.
            Interface compatibility from Java version of Cable.
            """
            return len(self.children)









