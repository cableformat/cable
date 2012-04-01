#!/usr/bin/env python
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

Cable2Xml.py
v.0.1
by Ketura 31 March 2012

This program is intended to be compiled as a command-line utility for converting 
between C-like ABstraction Layout (Cable) format and XML format files 
""" 
 
import os
import sys
import codecs

from xml.etree.ElementTree import parse, tostring
from xml.etree.ElementTree import Element, SubElement, ElementTree

from cable.CableReader import CableReader
from cable.CableWriter import CableWriter
from cable.CableNode import CableNode


def indent(elem, level=0):
#because ElementTree doesn't have a prettyprint() -_-
    i = "\n" + level*"    "
    if len(elem):
        if not elem.text or not elem.text.strip():
            elem.text = i + "    "
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
        for elem in elem:
            indent(elem, level+1)
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
    else:
        if level and (not elem.tail or not elem.tail.strip()):
            elem.tail = i
        
def _doNodes(cable):
    xml = Element(cable.name)
    
    for name in cable.values:
        if name == "__text_":
            xml.text = cable.values[name]
        else:
            xml.attrib[name] = cable.values[name]
        
    for child in cable.children:
        xml.append(_doNodes(child))
       
    return xml
        
def convertCableFile(filename, newfilename=""):
    
    if not os.path.exists(filename):
        return "Cable file invalid!  Provide a valid Cable file."
        
    reader = CableReader()
    cableroot = reader.readFromFile(filename)

    xml = _doNodes(cableroot)
    
    if newfilename == "":
        newfilename = filename + ".xml"
    
    indent(xml)
    tree = ElementTree(xml)
    tree.write(newfilename, encoding="utf-8")

    return "Successfully wrote XML structure to " + newfilename

        
if len(sys.argv) == 1:
    print """
    Usage of this program: 
cable2xml cablefilename [xmlfilename]
    where:
        cablefilename is the relative path of the CABLE file to be converted.
        xmlfilename is the relative path to the XML file to be generated
        

Note that if xmlfilename is not provided, output will be saved to
cablefilename.xml instead.

Filenames with spaces should be escaped with quotations, e.g.:
    cable2xml "monthly report.xml" "new report.cable"

FILES WILL BE AUTOMATICALLY OVERWRITTEN.  MAKE SURE YOUR FILES ARE BACKED
UP BEFORE PROCEEDING."""
elif (len(sys.argv) > 2):
    print convertCableFile(sys.argv[1], sys.argv[2])

else:
    print convertCableFile(sys.argv[1])


