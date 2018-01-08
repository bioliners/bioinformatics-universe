#!/usr/bin/python

import sys, getopt
import os
import collections
from Bio import SeqIO

FILE_WITH_TAXA_NAMES = None
PATH = "extractSequencesByGroups_Output"
OUTPUT_FILENAME = "result.txt"
TAXON_TO_SEQ_MAP = collections.OrderedDict()
FILENUMBER_TO_STRING_LENGTH = []
TAXON_TO_FILENUMBER = {}



alignedFilesNames = set()
taxaList = []
ribosomList=[]



USAGE = "Script concatenate alignments based on given taoxnomy file and a bunch of alignet files in the given folder." + "\n" + "python" + sys.argv[0] + '''
-i file with taxa
-f folder with aligned files 
[-o output file name (default result.txt)]'''


def initialyze(argv):
	global FILE_WITH_TAXA_NAMES, PATH, OUTPUT_FILENAME
	try:
		opts, args = getopt.getopt(argv[1:],"hi:f:o:",["inputFileName=", "folder=", "outputFileName="])
	except getopt.GetoptError:
		print USAGE + " Error"
		sys.exit(2)
	for opt, arg in opts:
		if opt == '-h':
			print USAGE
			sys.exit()
		elif opt in ("-i", "--inputFileName"):
			FILE_WITH_TAXA_NAMES = str(arg)
		elif opt in ("-f", "--folder"):
			PATH = str(arg).strip()
		elif opt in ("-o", "--outputFileName"):
			OUTPUT_FILENAME = str(arg)

	#initialize TaxonToSequenceMap
	with open(FILE_WITH_TAXA_NAMES, 'r') as inputFile:
		for taxonName in inputFile:
			TAXON_TO_SEQ_MAP[taxonName.strip()] = ""
			TAXON_TO_FILENUMBER[taxonName.strip()] = -1
		

	
def createListOfFiles():
	os.chdir(PATH)
	filesInTheDirectory = os.listdir(os.getcwd())
	for fileName in filesInTheDirectory:
		if fileName.split(".")[1] == "fasta":
			alignedFilesNames.add(fileName)
			

def concatenate():
	fileNumberCounter = -1
	isFirstFile = True
   
	for everyFile in alignedFilesNames:
		currentFile = open(everyFile, "r")
		fileNumberCounter+=1

		for record in SeqIO.parse(currentFile, "fasta"):
			taxonOfSequence = record.description.strip().split("|")[1]
			sequence = str(record.seq).strip()

			difference = fileNumberCounter - TAXON_TO_FILENUMBER[taxonOfSequence]
			numberOfDashes = sum(FILENUMBER_TO_STRING_LENGTH[fileNumberCounter-difference+1:fileNumberCounter])

			TAXON_TO_SEQ_MAP[taxonOfSequence] = TAXON_TO_SEQ_MAP[taxonOfSequence] + "-"*numberOfDashes + sequence
			TAXON_TO_FILENUMBER[taxonOfSequence] = fileNumberCounter
			

		currentFile.close()
		FILENUMBER_TO_STRING_LENGTH.append(len(sequence))
		isFirstFile = False
		
		
def saveResult():
	os.chdir("..")
	with open(OUTPUT_FILENAME, 'w') as outputFile:
		for taxon, seq in TAXON_TO_SEQ_MAP.items():
			outputFile.write(">" + taxon.replace(" ", "_") + "\n")
			outputFile.write(seq)
		
def main(argv):
	initialyze(argv)
	createListOfFiles()
	concatenate()
	saveResult()
	
		
		
main(sys.argv)
	
			
		




