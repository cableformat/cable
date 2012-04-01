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

class CableValue:
    """
    All the mixed types wrapped within a single interface.
    As well might be guessed, this entire class is worthless in Python.  The 
    interface has been preserved for code transition purposes only; for all
    intents and purposes simply declare a normal variable and code 
    business as usual.
    """
    class _Type:
        Bool = bool
        Numeric = float
        String = unicode
        

    def _createBool(newvalue):
        """ Factory for creating a variable with the specified boolean newvalue.
        Interface compatibility from Java version of Cable.
        """
        return CableValue(bool(newvalue))
    
    def _createNumeric(newvalue):
        """Factory for creating a variable with the specified numeric newvalue.
        Interface compatibility from Java version of Cable.
        """
        return CableValue(float(newvalue))

    def _createString(newvalue):
        """Factory for creating a variable with the specified String newvalue.
        Interface compatibility from Java version of Cable.
        """
        return CableValue(str(newvalue))
        
    def __init__(self, newvalue=0.0):
        self.value = newvalue
        
    def _toString(self):
        """A String representation of the Type and Value of this object
        """
        return type(self.value) + ':' + self.value
    
    def _getType(self):
        """Returns the type of this variable.
        Interface compatibility from Java version of Cable.
        """
        return type(self.value)
        
    def _isBool(self):
        """Returns true if the value of this is of type bool.
        Interface compatibility from Java version of Cable.
        """
        return isinstance(self.value, bool)
        
    def _isNumeric(self):
        """Returns true if the value of this is of type numeric.
        Interface compatibility from Java version of Cable.
        """
        try:
            float(self.value)
            return True
        except:
            return false
            
    def _isString(self):
        """Returns true if the value of this is of type string.
        Interface compatibility from Java version of Cable.
        """
        return isinstance(self.value, basestring)
    
    def _set(self, newvalue):
        """Sets the internal value either by copying another CableValue's data
        or by setting according to passed value.
        """
        try:
            self.value = newvalue.value
        except:
            self.value = newvalue
    
    def _setString(self, newvalue):
        """Sets this variable with the specified String value.
        Interface compatibility from Java version of Cable.
        """
        self.value = str(newvalue)
    
    def _setNumeric(self, newvalue):
        """Sets this variable with the specified numeric value.
        Interface compatibility from Java version of Cable.
        """
        self.value = float(newvalue)
    
    def _setBool(self, newvalue):
        """Sets this variable with the specified boolean newvalue.
        Interface compatibility from Java version of Cable.
        """
        self.value = bool(newvalue)
    
    def _asString(self):
        """Returns this value as a string.
        Interface compatibility from Java version of Cable.
        """
        return unicode(self.value)
    
    def _asNumeric(self):
        """Returns this value as a numeric.
        Interface compatibility from Java version of Cable.
        """
        try:
            return float(self.value)
        except:
            return 0.0

    def _asBool(self):
        """Returns this value as a boolean.
        Interface compatibility from Java version of Cable.
        """
        return bool(self.value)
            

    
    
    
    
    
    




