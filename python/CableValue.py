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
    
    class CableValue:
        """
        All the mixed types wrapped within a single interface.
        As well might be guessed, this entire class is worthless in Python.  The 
        interface has been preserved for code transition purposes only; for all
        intents and purposes simply declare a normal variable and code 
        business as usual.
        """
        class _Type:
            Bool = type(True)
            Numeric = type(1.0)
            String = type("")
            
   
        __init__(self):
            self.value = 0.0
        
        def _createBool(newvalue):
            """ Factory for creating a variable with the specified boolean newvalue.
            """
            return CableValue()
        
        def _createNumeric(newvalue):
            """Factory for creating a variable with the specified numeric newvalue.
            """
            var = CableValue()
            var.value = newvalue
            return var
        
        def _createString(newvalue):
            """Factory for creating a variable with the specified String newvalue.
            """
            var = CableValue()
            var.value = newvalue
            return var
        
        def _toString(self):
            return type(self.value) + ':' + self.value
        
        def _getType(self):
            """Returns the type of this variable.
            """
            return type(self.value)
        
        def _set(self, newvalue):
            self.value = newvalue
        
        def _setString(self, newvalue):
            """Sets this variable with the specified String value.
            """
            self.value = newvalue
        
        def _setNumeric(self, newvalue):
            """Sets this variable with the specified numeric value.
            """
            self.value = newvalue
        
        def _setBool(self, newvalue):
            """Sets this variable with the specified boolean newvalue.
            """
            self.value = newvalue
            
        
        def _asString(self):
            return self.value
        
        def _asNumeric(self):
            try:
                if type(self.value) == _Type.Numeric:
                    return self.value
                elif type(self.value) == _Type.Bool:
                    if self.value is True:
                        return 1.0
                    else:
                        return 0.0
                elif type(self.value) == _Type.String:
                    return float(self.value)
                
            except ValueError:
                return 0.0
        
        def _asBool(self):
            if type(self.value) == _Type.Numeric:
                return self.value > 0
                
            elif type(self.value) == _Type.Bool:
                return self.value
                
            elif type(self.value) == _Type.String:
                return len(self.value) > 0
                

        
        
        
        
        
        
    
    
    
    
