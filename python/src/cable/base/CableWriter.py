"""CableWriter - A utility used for generating Cable text from a tree of nodes
Copyright (C) 2012 Mark Owen and Christian McCarty

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
import sys
import codecs

class CableWriter:

    def __init__(self):
        self.error = ""
        self.string = ""
        
    def _getError():
        """Returns the error message.
        Interface compatibility from Java version of Cable.
        """
        return self.error
        
    def write(self, node):
        """Generates the Cable text from the node.
        """
        self.string = ""
        self.writeNode(node, "")
        return self.string
        
    def writeToFile(self, filename, node):
        """Generates the Cable text from the node and writes it to the specified file.
        """
        fout = codecs.open(filename, 'w', "utf-8-sig")
        fout.write(self.write(node))
        fout.close()

                
    def writeNode(self, node, indent): 
        """The recursive function used in generating the Cable text.
        """   
        
        self.string += indent + node.name
        for name in node.values:
            if name == "__text_":
                value = '"' + node.values[name] + '"'
                self.string += ' ' + name + '=' + value
                
        for name in node.values:
            if name != "__text_":
                value = '"' + node.values[name] + '"'
                self.string += ' ' + name + '=' + value
        
        if len(node.children) > 0: 
            self.string += '\n' + indent + "{\n"
            for child in node.children:
                self.writeNode(child, indent + '\t')
            self.string += indent + "}\n"
            
        else:
            self.string += ";\n"
    
