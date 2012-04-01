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

XmlToCable.py
v.0.1
by Ketura

This program is intended to be compiled as a command-line utility for converting 
between XML format files and the C-like ABstraction Layout (Cable) format
""" 
 
import os
import datetime
import codecs

from xml.etree.ElementTree import parse, tostring
from xml.etree.ElementTree import Element, SubElement, ElementTree

from Cable import 

CABLE_VERSION = "1.0"
CABLE_EXTENSION = ".cable"


 
 
