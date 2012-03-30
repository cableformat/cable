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
    
    class CableReader:
    
        def _isws(char):
            if type(char) is not str:
                return False
                
            if char == ' ':
                return True
            if char == '\t':
                return True
            if char == '\n':
                return True
            
            return False
            
        def _islu(char):
            if type(char) is not str:
                return False
                
            if char >= 'a' and char <= 'z'
                return True
            if char >= 'A' and char <= 'Z'
                return True
            if char == '_'
                return True
            return False
            
        def _islun(char):
            if char >= '0' and char <= '9'
                return True
            return islu(char)
            
        def _removeEscapeChars(string)
            begin = 0
            index = 0
            length = len(string)
            out = ""
            
            while index < length:
                if string[index] == '\\':
                    out += string[begin:index]
                    index++
                    
                    if string[index] == '\\':
                        out += '\\'
                    elif string[index] == '"':
                        out += '"'
                    elif string[index] == '\n':
                        out += '\n'
                    elif string[index] == '\t':
                        out += '\t'
                    elif string[index] == '\r':
                        out += '\r'
                        
                    index++
                    begin = index
                else:
                    index++
            
            out += string[begin:index]
            
            return out
            
            
        def __init__(self):
            self.source = ""
            self.tokens = []
            self.cursor = 0
            self.error = ""
            
        def read(self, string):
            self.source = string
            self.tokens = []
            
            try:
                tokenize()
                reset()
                return parse()
            except:
                return None
                
        def readFromFile(self, filename):
            try:
                fin = open(filename, 'rb')
                node = read(self, fin.read())
                fin.close()
            except:
                node = None
                
            return node
        
        def tokenize(self):
            length = len(self.source)
            index = 0
            
            while index < length:
                c = self.source[index]
                
                if isws(c):
                    index++
                    while index < length and isws(self.source[index]):
                        index++
                elif c == '#'
                    index++
                    while index < length and self.source[index] != '\n':
                        index++
                
                elif islu(c)
                    b = index
                    index++
                    while index < length and islun(self.source[index]:
                        index++
                    
                    value = self.source[b:index]
                    if value == "true":
                        self.tokens.append(CableToken("true", CableToken.Type.True))
                    elif value == "false":
                        self.tokens.append(CableToken("false", CableToken.Type.False))
                    else:
                        self.tokens.append(CableToken(value, CableToken.Type.Word))
                
                elif isn(c) or c == '.':
                    b = index
                    index++
                    while index < length and isn(self.source[index])
                        index++
                    
                    if index < length and self.source[index] == '.':
                        index++
                        while index < length and isn(self.source[index]):
                            index++
                    self.tokens.append(CableToken(self.source[b:index]), CableToken.Type.Numeric)
                
                elif c == '"':
                    b = index
                    index++
                    while index < length and !(self.source[index] == '"' and self.source[index-1] != '\\'):
                        index++
                    if index >= length:
                        pass
                        #new exception 'closing " not found'
                    index++
                    self.tokens.append(CableToken(convertFromEscapedString(self.source[b+1, index-1]), CableToken.Type.String))
                    
                elif c == '=':
                    index++
                    self.tokens.append(CableToken('=', CableToken.Type.Set))
                    
                elif c == ';':
                    index++
                    self.tokens.append(CableToken(';', CableToken.Type.End))
                
                elif c == '{':
                    index++
                    self.tokens.append(CableToken('{', CableToken.Type.OpenBracket))
                
                elif c == '}':
                    index++
                    self.tokens.append(CableToken('{', CableToken.Type.CloseBracket))
                
                else:
                    pass
                    #throw CableException("invalid token")
                    
            
        def parse(self):
            if self.current.getType() != CableToken.Type.Word:
                pass
                #throw CableException("invalid node identifier")
            
            node = CableNode(self.current().getValue())
            self.next()
            
            while self.current().getType() == CableToken.Type.Word:
                name = self.current().getValue()
                next()
                
                currenttype = self.current().getType()
                if currenttype == CableToken.Type.true:
                    node.value = True
                    
                elif currenttype == CableToken.Type.false:
                    node.value = False
                    
                elif currenttype == CableToken.Type.String or currenttype == CableToken.Type.Numeric:
                    node.value = self.current().getValue()
                
                else:
                    pass
                    #throw CableException("expecting bool, numeric, or string as property value")
                    
            if(self.current().getType() == CableToken.Type.End):
                self.next()
                return node
                
            if(self.current().getType() == CableToken.Type.OpenBracket):
                self.next()
                while self.current.getType() != CableToken.Type.CloseBracket:
                    node.addChild(self.parse())
                
                self.next()
                
            return node
                
            
        def reset(self):
            self.cursor = 0
        
        def current(self):
            if self.cursor >= len(self.tokens): #if done
                pass
                #throw CableException("end not expected")
            return self.tokens[self.cursor]
        
        def next(self):
            self.cursor++
        

        def _getError(self):
            """Return error.
            Interface compatibility from Java version of Cable.
            """"
            return self.error
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
                
