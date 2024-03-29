"""This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

guify.py
v.0.1
by Ketura

This program is intended to take *.xml files produced by Glade
and transfer them to the new, experimental Cable format.
"""

import os
import datetime
import codecs

from shutil import *
from shutil import ignore_patterns
from xml.etree.ElementTree import parse, tostring
from xml.etree.ElementTree import Element, SubElement, ElementTree

CDS_VERSION = "0.1"
CDS_EXTENSION = ".cds"

GLADE_WINDOW = "GtkWindow"
GLADE_PANEL = "GtkLayout"
GLADE_SPLIT_PANEL = "GtkPaned"
GLADE_TABBED_PANEL = "GtkNotebook"

GLADE_LABEL = "GtkLabel"

GLADE_IMAGE = "GtkImage"
GLADE_TEXTBOX = "GtkEntry"
GLADE_COMBO = "GtkComboBox"
GLADE_CHECK = "GtkCheckButton"
GLADE_RADIO = "GtkRadioButton"
GLADE_BUTTON = "GtkButton"

GLADE_NAMES = { GLADE_WINDOW:"form",
                GLADE_PANEL:"panel",
                GLADE_SPLIT_PANEL:"splitpanel",
                GLADE_TABBED_PANEL:"tabpanel",
                GLADE_LABEL:"label",
                GLADE_IMAGE:"image",
                GLADE_TEXTBOX:"textbox",
                GLADE_COMBO:"combobox",
                GLADE_CHECK:"checkbox",
                GLADE_RADIO:"radiobutton",
                GLADE_BUTTON:"button" }

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

def generateXML(node, interxml):
    newelement = None
    for child in node.getchildren():
        #first read the object tag and pull the properties from there
        if child.tag == "object": 
            print "found new %s %s" %(GLADE_NAMES[child.attrib["class"]], child.attrib["id"])
            newelement = SubElement(interxml, GLADE_NAMES[child.attrib["class"]])
            
            newelement.attrib["id"] = child.attrib["id"]
            for prop in child.getchildren():
                if prop.tag == "property":
                    newelement.attrib[prop.attrib["name"]] = prop.text
                    print "adding new property %s" %prop.attrib["name"]
                elif prop.tag == "child":
                    anotherelement = generateXML(prop, newelement)
                    
    if newelement is not None:
        for child in node.getchildren():
            #then loop again and grab the packing tag
            if child.tag == "packing":
                for prop in child.getchildren():
                    print "adding new property %s" %prop.attrib["name"]
                    newelement.attrib[prop.attrib["name"]] = prop.text
                    
    return newelement
    
    
def filterprop(controltype, attrib, value):
    if controltype is None or attrib is None
        return ""
        
    CONTROL_ATTRIBUTES = {"id":"id",
                        "left":"left",
                        "right":"right",
                        "top":"top",
                        "bottom":"bottom",
                        "leftanchor":"leftanchor",
                        "rightanchor":"rightanchor",
                        "topanchor":"topanchor",
                        "width":"width",
                        "height":"height",
                        "text":"text",
                        "textwidth":"textwidth",
                        "textheight":"textheight",
                        "scrollable":"scrollable" }
                        
    PANEL_ATTRIBUTES = copy.deepcopy(CONTROL_ATTRIBUTES)
    PANEL_ATTRIBUTES.update({ "minwidth":"minwidth", 
                            "minheight":"minheight",
                            "maxwidth":"maxwidth",
                            "maxheight":"maxheight"})
                        
    if controltype == GLADE_NAMES[GLADE_WINDOW]:
        


    return ' '+ name + '=' + '"' + value + '"'

    
def generateCDS(xml, cds="", level=0):
    if level == 0:
        cds = "//Generated by guify.py v.%s on %s.\n" %(CDS_VERSION, str(datetime.datetime.now()))
        
    tab = "    "level
    for element in xml.getchildren():
        
        cds +=  '\n' + tab + element.tag
        cds += writeprop("id", element.attrib["id"])
        for attribute in element.keys():
            if attribute is not "id":
                cds += filterprop(element.tag, attribute, element.attrib[attribute])
        
        cds += ';'
                
        if len(element.getchildren()):
            cds += "\n%s{" %tab
            cds = generateCDS(element, cds, level+1)
            cds += "\n%s}" %tab
        
    return cds
    
def gladeToCDS(path):
    xmlfile = parse(path)
    xmlroot = xmlfile.getroot()
        
    if xmlroot.tag == "interface": #to ensure it's a glade file we're dealing with

        interxml = Element("cds") #the root tag of our intermediary *.xml structure
        print "now looping through windows"

        for child in xmlroot.getchildren():
            print "new window looping..."
            generateXML(xmlroot, interxml)
        
        indent(interxml)
        tree = ElementTree(interxml)

        print "\n\nwriting intermediary *.xml %s ..." %(path+".xml")
        tree.write(path+".xml")
        print "write successful"
        
        cds = generateCDS(interxml)
        
        #print cds
        

        root, ext = os.path.splitext(path)
        cdsfile = path.replace(ext, CDS_EXTENSION)
        print "writing *.cds file %s ..." %cdsfile
        f = codecs.open(cdsfile,'w','utf-8')
        f.write(cds)
        print "write successful"
        
        
    else:
        print "invalid *.xml glade file %s" %path

def writeprop(name, value):
    return ' '+ name + '=' + '"' + value + '"'


def xml2cds(xml, cds="", level=0):
    if level==0:
        cds = "//Generated by guify.py v.%s on %s.\n" %(CDS_VERSION, str(datetime.datetime.now()))
        
    tab = "    "level
    
    for element in xml.getchildren():
        cds +=  '\n' + tab + element.tag
        for attribute in element.keys():
            cds += writeprop(attribute, element.attrib[attribute])
        
        if len(element.getchildren()):
            cds += "\n%s{" %tab
            cds = generateCDS(element, cds, level+1)
            cds += "\n%s}" %tab
        else:
            cds += ';'
                    
    return newelement

def file2cds(path):
    return xml2cds(parse(path).getroot())

def cds2xml(cds, xml=None, level=0):
    pass
    
def file2xml(path):
    return cds2xml(parse(path).getroot())


#entry point:
gladeToCDS("guitest.glade")



