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
 

class cable:

    class CableWriter:
    
        def __init__(self):
            self.error = ""
            self.string = ""
            
        def _getError():
            return self.error
            
        def write(self, node):
            self.string = ""
            writeNode(node, "")
            return self.string
            
        def writeToFile(self, filename, node):
            fout = None
            try:
                fout = open(filename, 'w')
                fout.write(self.write(node))
            except:
                return false
                #self.error = ex.getMessage()
            finally:
                fout.close()
            
            return true
                    
        def writeNode(self, node, indent):    
            self.string += indent + node.getName()
            if node.hasProperties():
                for entry in node.properties():
                    name = entry.getKey()
                    value = ""
                    if type(entry.getValue()) is str:
                        value = '"' + entry.getValue() + '"'
                    else:
                        value = entry.getValue()
                        
                    self.string += ' ' + name + '=' + value
            
            if node.hasChildren(): 
                self.string += '\n' + indent + "{\n"
                for child in node.children():
                    writeNode(child, indent + '\t'
                self.string += indent + "}\n"
                
            else:
                self.string += ";\n"
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        
