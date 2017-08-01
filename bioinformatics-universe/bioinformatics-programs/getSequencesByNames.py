#!/usr/bin/python

import fileinput, sys
from Bio import SeqIO


if sys.argv[1] == "help":
	print "Script extractes sequences from the second file according to sequence names in the first file."
	print "Usage: " + sys.argv[0] + " input-file1 input-file2 > output-file"
	sys.exit(0)

proteinNames = sys.argv[1]
protNamesAndSequences = sys.argv[2]
firstFileDelim = None
secondFileDelim = None
firstFileCol = None
secondFileCol = None

if len(sys.argv) > 3 and (sys.argv[3] != ""):
	firstFileDelim = sys.argv[3]
if len(sys.argv) > 4 and (sys.argv[4] != ""):
	firstFileCol = int(sys.argv[4])
if len(sys.argv) > 5 and (sys.argv[5] != ""):
	secondFileDelim = sys.argv[5]
if len(sys.argv) > 6 and (sys.argv[6] != ""):
	secondFileCol = int(sys.argv[6])


my_proteins = set()

currentProtName =  ''
isMatch = False

try:
	for line in fileinput.input(proteinNames):
		if (firstFileDelim != None and firstFileCol == None) or (firstFileDelim == None and firstFileCol != None):
			raise ValueError("firstFileCol is None!")
		elif firstFileDelim != None:
			line = line.split(firstFileDelim)[firstFileCol-1]
		my_proteins.add(line.strip())
	fileinput.close()

	with open(protNamesAndSequences, "r") as currentFile:
		for record in SeqIO.parse(currentFile, "fasta"):
			currentProtName  = record.description
            		if (secondFileDelim != None and secondFileCol == None) or (secondFileDelim == None and secondFileCol != None):
                		raise ValueError("secondFileCol is None!")
			elif secondFileDelim != None:
				currentProtName = currentProtName.split(secondFileDelim)[secondFileCol-1]
				#currentProtName  = record.description.split("|")[3]
				#currentProtName = "_".join(record.description.split("_")[:2]) 
				#currentProtName  = record.description.split("|")[1].split(":")[1]
			if currentProtName in my_proteins:
				print ">" + record.description
				print  record.seq

except Exception, e:
	print "Problem occured: ", e
finally:
	fileinput.close()

		
	
	
