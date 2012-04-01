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
by Ketura 31 March 2012

This program is intended to be compiled as a command-line utility for converting 
between XML format files and the C-like ABstraction Layout (Cable) format
""" 
 
import os
import sys

from xml.etree.ElementTree import parse, tostring
from xml.etree.ElementTree import Element, SubElement, ElementTree

from cable.CableWriter import CableWriter
from cable.CableNode import CableNode

CABLE_VERSION = "1.0"
CABLE_EXTENSION = ".cable"


def _isws(string):
    """Returns true if the specified character is a whitespace.
    """
    if string is None:
        return True
    if "".join(string.split()) != "":
        return False
    return True
        
def _doNodes(xml):
    cablenode = CableNode(xml.tag)
    
    for prop in xml.keys():
        cablenode.values[prop] = xml.attrib[prop]

    if _isws(xml.text) == False:
        cablenode.values["__text_"] = xml.text    
        
        
    for child in xml:
        cablenode.addChild(_doNodes(child))
       
    return cablenode
        
def convertXmlFile(filename, newfilename=""):
    
    if not os.path.exists(filename):
        return "XML file invalid!  Provide a valid XML file."
        
    xmlfile = parse(filename)
    xmlroot = xmlfile.getroot()
    
    cablenode = _doNodes(xmlroot)
    
    if newfilename == "":
        newfilename = filename + CABLE_EXTENSION
#    print cablenode.toDebugXml()
    
    writer = CableWriter()
    
    writer.writeToFile(newfilename, cablenode)
    
    return "Successfully wrote CABLE structure to " + newfilename

        
if len(sys.argv) == 1:
    print """
    Usage of this program: 
cable2xml xmlfilename cablefilename
    where:
        xmlfilename is the relative path to the XML file to be converted
        cablefilename is the relative path of the CABLE file to be generated.

Note that if cablefilename is not provided, output will be saved to
xmlfilename.cable instead.

Filenames with spaces should be escaped with quotations, e.g.:
    cable2xml "monthly report.xml" "new report.cable"

FILES WILL BE AUTOMATICALLY OVERWRITTEN.  MAKE SURE YOUR FILES ARE BACKED
UP BEFORE PROCEEDING."""
elif (len(sys.argv) > 2):
    print convertXmlFile(sys.argv[1], sys.argv[2])

else:
    print convertXmlFile(sys.argv[1])


