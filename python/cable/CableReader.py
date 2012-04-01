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
import codecs
from cable.CableToken import CableToken 
from cable.CableNode import CableNode

def _isws(char):
    """Returns true if the specified character is a whitespace.
    """
    if "".join(char.split()) == "":
        return True
    
    return False
    
def _islu(char):
    """Returns true if the specified character is a letter or underscore.
    """
    if char >= 'a' and char <= 'z':
        return True
    if char >= 'A' and char <= 'Z':
        return True
    if char == '_':
        return True
    return False
    
def _isn(char):
    """Returns true if the specified character is a number.
    """
    if char >= '0' and char <= '9':
        return True

def _islun(char):
    """Returns true if the specified character is a letter, underscore or number.
    """
    return _isn(char) or _islu(char)
    
def _convertFromEscapedString(string):
    """Converts a string's escape character to their actual value.
    """
    begin = 0
    index = 0
    length = len(string)
    out = ""
    
    while index < length:
        if string[index] == '\\':
            out += string[begin:index-1]
            index+= 1
            
            if string[index] == '\\':
                out += '\\'
            elif string[index] == '"':
                out += '"'
            elif string[index] == 'n':
                out += '\n'
            elif string[index] == 't':
                out += '\t'
            elif string[index] == 'r':
                out += '\r'
                
            index+= 1
            begin = index
        else:
            index+= 1
    
    out += string[begin:index]
    
    return out
        
class CableReader:

    def __init__(self):
        self.source = ""
        self.tokens = []
        self.next = 0
        self.error = ""
        
    def _read(self, string):
        """Reads a Cable string and converts it into a tree of nodes.
        """

        self.source = string
        self.tokens = []
        
        self._tokenize()
        self._reset()
        return self._parse()

            
    def readFromFile(self, filename):
        """Publically-facing function to read the Cable text from a file and convert it
        into a tree of nodes.
        """

        fin = codecs.open(filename, 'rb', "utf-8-sig")
        node = self._read(fin.read())
        fin.close()

        return node
    
    def _tokenize(self):
        """Converts the source text into an array of tokens.
        """
        print "tokenizing"
        length = len(self.source)
        index = 0
        
        while index < length:
            #print "looping..."
            c = self.source[index]
            
            if _isws(c):
                index+= 1
                while index < length and _isws(self.source[index]):
                    index+= 1
                    
            elif c == '#':
                index+= 1
                while index < length and self.source[index] != '\n':
                    index+= 1
            
            elif _isn(c) or c == '.' or c == '-':
                b = index
                index+= 1
                while index < length and _isn(self.source[index]):
                    index+= 1
                
                if index < length and self.source[index] == '.':
                    index+= 1
                    while index < length and _isn(self.source[index]):
                        index+= 1
                self.tokens.append(CableToken(self.source[b:index]), CableToken.Type.Numeric)
            
            elif c == '"':
                b = index
                index+= 1
                while index < length and not (self.source[index] == '"' and self.source[index-1] != '\\'):
                    index+= 1
                if index >= length:
                    print "FATAL ERROR: CLOSING QUOTE NOT FOUND"
                    return False
                index+= 1
                self.tokens.append(CableToken(_convertFromEscapedString(self.source[b+1:index-1]), CableToken.Type.String))
                
            elif c == '=':
                index+= 1
                self.tokens.append(CableToken('"', CableToken.Type.Set))
                
            elif c == ';':
                index+= 1
                self.tokens.append(CableToken(';', CableToken.Type.End))
            
            elif c == '{':
                index+= 1
                self.tokens.append(CableToken('{', CableToken.Type.OpenBracket))
            
            elif c == '}':
                index+= 1
                self.tokens.append(CableToken('{', CableToken.Type.CloseBracket))
            
            elif _islu(c):
                b = index
                index+= 1
                while index < length and _islun(self.source[index]):
                    index+= 1
                
                value = self.source[b:index]
                if value == "true":
                    self.tokens.append(CableToken("true", CableToken.Type.true))
                elif value == "false":
                    self.tokens.append(CableToken("false", CableToken.Type.false))
                else:
                    self.tokens.append(CableToken(value, CableToken.Type.Word))
        print "finished tokenizing"
                
        
    def _parse(self):
        """Parses the array of tokens and builds the tree.
        """
        if self._current().tokentype != CableToken.Type.Word:
            pass
            #throw CableException("invalid node identifier")
        
        node = CableNode(self._current().value)
        self.next+= 1
        
        while self._current().tokentype == CableToken.Type.Word:
            name = self._current().value
            self.next+= 1
            if self._current().tokentype != CableToken.Type.Set:
                print "FATAL ERROR: PROPERTY/VALUE MISMATCH; = MISSING"
            
            self.next+= 1
            _currenttype = self._current().tokentype
            if _currenttype == CableToken.Type.true:
                node.values[name] = "true"
                
            elif _currenttype == CableToken.Type.false:
                node.values[name] = "false"
                
            else:# _currenttype == CableToken.Type.String or _currenttype == CableToken.Type.Numeric:
                node.values[name] = self._current().value
                
            self.next+= 1
                
        if(self._current().tokentype == CableToken.Type.End):
            self.next+= 1
            return node
            
        if(self._current().tokentype == CableToken.Type.OpenBracket):
            self.next+= 1
            while self._current().tokentype != CableToken.Type.CloseBracket:
                node.addChild(self._parse())
            
            self.next+= 1
            
        return node
            
        
    def _reset(self):
        """_resets the position of the next.
        """
        self.next = 0
    
    def _current(self):
        if self.next >= len(self.tokens): #if done
            pass
            #throw CableException("end not expected")
        return self.tokens[self.next]

    def _getError(self):
        """Returns the error message.
        Interface compatibility from Java version of Cable.
        """
        return self.error
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
            
