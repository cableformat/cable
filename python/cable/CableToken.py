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

class CableToken:
    
    class Type:
        """The token types and names in a psuedo-enum setup.  Note that all tokens are capitalized
        except "true" and "false" to avoid name conflicts.
        """
        Word = 0
        String = 1
        Numeric = 2
        true = 3
        false = 4
        Set = 5
        End = 6
        OpenBracket = 7
        CloseBracket = 8
        TokenNames=["Word","String","Numeric","True","False","Set","End","OpenBracket","CloseBracket"]
        
    def __init__(self, newvalue, newtokentype):
        self.value = newvalue
        self.tokentype = newtokentype
        
    def toString(self):
        """A String representation of the Type and Value of this Token
        """
        return Type.TokenNames[self.tokentype] + ":" + self.value
        
    
    def _getType(self):
        """Returns the type of the Token.
        Interface compatibility from Java version of Cable.
        """
        return self.tokentype
    
    def _getValue(self):
        """Return the value of the Token.
        Interface compatibility from Java version of Cable.
        """
        return self.value
    

