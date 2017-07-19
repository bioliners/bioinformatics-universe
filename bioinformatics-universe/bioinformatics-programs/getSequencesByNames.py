#!/usr/bin/python

import fileinput, sys
from Bio import SeqIO


if sys.argv[1] == "help":
	print "Script extractes sequences from the second file according to sequence names in the first file."
	print "Usage: " + sys.argv[0] + " input-file1 input-file2 > output-file"
	sys.exit(0)

proteinNames = sys.argv[1]
protNamesAndSequences = sys.argv[2]


		  

my_proteins = set()

currentProtName =  ''
isMatch = False

try:
	for line in fileinput.input(proteinNames):
		my_proteins.add(line.strip())
	fileinput.close()

	with open(protNamesAndSequences, "r") as currentFile:
		for record in SeqIO.parse(currentFile, "fasta"):
			currentProtName  = record.description
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

		
	
	
