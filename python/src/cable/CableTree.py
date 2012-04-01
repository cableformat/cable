"""
CableTree toolkit; to be used with the C-like ABstraction Layout
(Cable) data format and made to closely imitate the native Python
xml.ElementTree handling of the XML format.

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


__all__ = [
    # public symbols
    "Comment",
    "dump",
    "Node", "CableTree",
    "fromstring",
    "isnode", "iterparse",
    "parse",
    "SubNode",
    "tostring",
    "TreeBuilder",
    "VERSION", 
    "CableTreeBuilder",
    ]
    
    
VERSION = "1.0"

import sys
