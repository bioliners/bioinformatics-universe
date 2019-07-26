#!/usr/bin/python
#author: Vadim M. Gumerov; 06/20/2019
#assistant: Robert M. Morganti; 07/26/2019

import sys, getopt, fileinput, os, traceback
import collections


'''Script cretaes COGs based on results of blast all vs all. Input: bunch of blast files in 6 outformat.
'''
#usage:

INPUT_DIR = "../bioinformatics-programs-workingDir2/blast_output"
INPUT_PROTS_DIR = "/media/sf_Shared/COGs/new_output2"
OUTPUT_FILENAME = "COGs.txt"
# add arguments here like
OUTPUT_PROTEINS_FILENAME = "COGs_Proteins.txt"
OUTPUT_SIF_FILENAME = "COGs.sif"
OUTPUT_SIF_UNIQUE_CONNECTIONS_FILENAME = "UniqueCOGs.sif"
DELIM="@"
IDENTITY_THRESHOLD=0
EVAL_THRESHOLD = 0.000005
COVERAGE_THRESHOLD = 80.0
DO_MERGE=True
BEST_HIT=True
UNIQUE_NETWORK=True

# Network of organism connections. Think of it like [(1,2),(1,2)....]
network = []

# List of proteins used, for .fa file output

listOfProtsUsed = []

# Network of organisms that are unique; used in printAllSifConnections and printProtein

UNIQUE_CONNECTIONS_NETWORK = dict()

#{g1:2, g2:10, ...}
GENOMENM_TO_NUM_OF_PROTS = dict()

#{ prot11:[(prot21, eval), (prot23, eval), ...], prot12:[(prot25, eval), (prot29, eval), ...] }
#FIRST_GENOME_TO_SEC_GENOME = collections.defaultdict(list)

#{ Genome1ToGenome2:{prot11:[(prot21, eval), (prot23, eval), ...], prot12:[(prot25, eval), (prot29, eval), ...]}, Genome2ToGenome1: {...}, ... ], ...}
GENOMENM_TO_PROT_TOPROT_LIST_MAP = dict()

PROT_TO_CLUSTER = dict()
PROT_TO_CLUSTERSET = collections.defaultdict(set)
CLUSTER_TO_PROTSET = collections.defaultdict(set)

GENOME_PAIRS = list()
GENOME_SET = set()

USAGE = sys.argv[0] + ' -i input directory -n input proteins directory -o output file name -f output file name of unique sif -p output file name of proteins -d delimeter -t identity threshold -e E-value threashold -v coverage threshold -m merge clusters or not (yes|no) -b focus on best ortholog hits (yes|no) -u display only unique network'
#DEFAULT_PARAMS = ["INPUT_DIR ", "INPUT_PROTS_DIR", "OUTPUT_FILENAME ", "OUTPUT_SIF_UNIQUE_CONNECTIONS_FILENAME", "OUTPUT_PROTEINS_FILENAME", "DELIM ", "COLUMN_NUM ", "IDENTITY_THRESHOLD ", "EVAL_THRESHOLD ", "COVERAGE_THRESHOLD ", "DO_MERGE "]
#DEFAULT_VALUES = [INPUT_DIR, INPUT_PROTS_DIR, OUTPUT_FILENAME, "OUTPUT_SIF_UNIQUE_CONNECTIONS_FILENAME", OUTPUT_PROTEINS_FILENAME, DELIM, COLUMN_NUM, IDENTITY_THRESHOLD, EVAL_THRESHOLD, COVERAGE_THRESHOLD, DO_MERGE]

def initialyze(argv):
	global INPUT_DIR, INPUT_PROTS_DIR, OUTPUT_FILENAME, OUTPUT_SIF_UNIQUE_CONNECTIONS_FILENAME, OUTPUT_PROTEINS_FILENAME, DELIM, IDENTITY_THRESHOLD, EVAL_THRESHOLD, COVERAGE_THRESHOLD, DO_MERGE, BEST_HIT, UNIQUE_NETWORK
	try:
		opts, args = getopt.getopt(argv[1:],"hi:n:o:f:p:d:c:t:e:v:m:b:u:",["inputDir=", "inputProtDir", "outputFileName=", "outputFileNameUniqueSIF=", "outputFileNameProts=", "delimeter=", "identity=", "eValue=", "coverage=", "merge=", "bestHit=", "uniqueNetwork="])
	except getopt.GetoptError:
		print (USAGE + " Error")
		sys.exit(2)
	for opt, arg in opts:
		if opt == '-h':
			print (USAGE)
			sys.exit()
		elif opt in ("-i", "--inputDir"):
			INPUT_DIR = arg.strip()
		elif opt in ("-n", "--inputProtDir"):
			INPUT_PROTS_DIR = arg.strip()	
		elif opt in ("-o", "--outputFileName"):
			OUTPUT_FILENAME = str(arg).strip()	
		elif opt in ("-f", "--outputFileNameUniqueSIF"):
			OUTPUT_SIF_UNIQUE_CONNECTIONS_FILENAME = str(arg).strip()	
		elif opt in ("-p","--outputFileNameProts"):
			OUTPUT_PROTEINS_FILENAME = str(arg).strip()	
		elif opt in ("-d", "--delimeter"):
			DELIM = arg
		elif opt in ("-t", "--identity"):
			IDENTITY_THRESHOLD = float(arg)
		elif opt in ("-e", "--eValue"):
			EVAL_THRESHOLD = float(arg)
		elif opt in ("-v", "--coverage"):
			COVERAGE_THRESHOLD = float(arg)
		elif opt in ("-m", "--merge"):
			if arg == "no":
				DO_MERGE = False
		elif opt in ("-b", "--bestHit"):
			if arg =="no":
				BEST_HIT = False
		elif opt in ("-u","--uniqueNetwork"):
			if arg =="no":
				UNIQUE_NETWORK = False



#process input data and generate sets of unique proteins with eval <= eval threshold for each input genome in comparison one vs one
def processInputData():
	os.chdir(INPUT_DIR)
	for inputFile in os.listdir(os.getcwd()):
		numOfProteinsInGenome = set()
		try:
			for record in fileinput.input(inputFile):
				recordSpl = record.split("\t")
				firstProtFull = recordSpl[0]
				firstProtFullSplt = firstProtFull.split(DELIM)
				firstProt = firstProtFullSplt[1]
				secondProtFull = recordSpl[1]
				secondProtFullSplt = secondProtFull.split(DELIM)
				secondProt = secondProtFullSplt[1]
				# firstProtFullSplt[0] and secondProtFullSplt[0] are unique identifiers of files with proteins
				# importanrt if protein sets of each genome were provided in sepratae files
				if "[" in firstProtFullSplt[1]:
					firstOrganismName = firstProtFullSplt[0] + firstProt.split("[")[1].replace("]", "")
				else:
					firstOrganismName = firstProtFullSplt[0]
				if "[" in firstProtFullSplt[1]:
					secondOrganismName = secondProtFullSplt[0] + secondProt.split("[")[1].replace("]", "")
				else:
					secondOrganismName = secondProtFullSplt[0]

				if firstOrganismName != secondOrganismName:
					eVal = recordSpl[10]
					coverage = recordSpl[12]
					identity = recordSpl[2]

					GENOME_SET.add(firstOrganismName)
					numOfProteinsInGenome.add(firstProt)

					oneGenomeNmToAnotherGenomeNm = firstOrganismName + "_vs_" + secondOrganismName
					if not (secondOrganismName + "_vs_" + firstOrganismName) in GENOME_PAIRS and not oneGenomeNmToAnotherGenomeNm in GENOME_PAIRS:
						GENOME_PAIRS.append(oneGenomeNmToAnotherGenomeNm)
					#GENOMENM_TO_NUM_OF_PROTS[firstGenomeNm] = len(numOfProteinsInGenome)
					if oneGenomeNmToAnotherGenomeNm in GENOMENM_TO_PROT_TOPROT_LIST_MAP:
						checkAndAddProteins(GENOMENM_TO_PROT_TOPROT_LIST_MAP[oneGenomeNmToAnotherGenomeNm], eVal, coverage, identity, firstProt, secondProt)
					else:
						GENOMENM_TO_PROT_TOPROT_LIST_MAP[oneGenomeNmToAnotherGenomeNm] = DefaultOrderedDict(list)
						checkAndAddProteins(GENOMENM_TO_PROT_TOPROT_LIST_MAP[oneGenomeNmToAnotherGenomeNm], eVal, coverage, identity, firstProt, secondProt)
		except Exception:
			print ("Problem happened: ")
			print (traceback.print_exc())
		finally:
			fileinput.close()
	print ("Number of compared genomes: " +  str(len(GENOME_SET)))


def checkAndAddProteins(firstGenomeToSecGenome, eVal, coverage, identity, firstProt, secondProt):
	if float(eVal) <= EVAL_THRESHOLD and float(coverage) >= COVERAGE_THRESHOLD and float(identity) >= IDENTITY_THRESHOLD:
		firstGenomeToSecGenome[firstProt].append(secondProt)



#Create COGs
def createCOGs():
	print ("GENOME_PAIRS ", str(GENOME_PAIRS))
	clusterName = 0
	for everyGenomePair in GENOME_PAIRS:

		frstGenNmToSecGenNm = everyGenomePair
		secGenNmToFrstGenNm = everyGenomePair.split("_vs_")[1] + "_vs_" + everyGenomePair.split("_vs_")[0]

		firstGenomeToSecGenome = GENOMENM_TO_PROT_TOPROT_LIST_MAP[frstGenNmToSecGenNm]
		secondGenomeToFirstGenome = GENOMENM_TO_PROT_TOPROT_LIST_MAP[secGenNmToFrstGenNm]

		if BEST_HIT:
			if len(list(firstGenomeToSecGenome)):
				firstGenomeProt = list(firstGenomeToSecGenome)[0]
				protHitInSecondGenomeFromFirstGenome = firstGenomeToSecGenome[firstGenomeProt][0]
				if protHitInSecondGenomeFromFirstGenome in secondGenomeToFirstGenome and len(secondGenomeToFirstGenome[protHitInSecondGenomeFromFirstGenome]):
					protHitInFirstGenomeFromSecondGenome = secondGenomeToFirstGenome[protHitInSecondGenomeFromFirstGenome][0]
					if firstGenomeProt == protHitInFirstGenomeFromSecondGenome:
						# Adding each pair (1,2) to [(1,2),(1,2)...]
						network.append(firstGenomeProt)
						network.append(protHitInSecondGenomeFromFirstGenome)
						if DO_MERGE:
							clusterName = processIfDoMerge(firstGenomeProt, protHitInSecondGenomeFromFirstGenome, clusterName)
						else:
							clusterName = processIfNotMerge(firstGenomeProt, protHitInSecondGenomeFromFirstGenome, clusterName)
		else:						
			for firstGenomeProt in firstGenomeToSecGenome:
		 		listOfProtsInSecGenome_ForProtInFrstGenome = firstGenomeToSecGenome[firstGenomeProt]
				for protHitInSecondGenomeFromFirstGenome in listOfProtsInSecGenome_ForProtInFrstGenome:
		 			if protHitInSecondGenomeFromFirstGenome in secondGenomeToFirstGenome:
		 				listOfProtsInFrstGenome_ForProtInSecGenome = secondGenomeToFirstGenome[protHitInSecondGenomeFromFirstGenome]
						if listOfProtsInFrstGenome_ForProtInSecGenome[0] == firstGenomeProt:
							network.append(firstGenomeProt)
							network.append(protHitInSecondGenomeFromFirstGenome)
		 					if DO_MERGE:
		 						clusterName = processIfDoMerge(firstGenomeProt, protHitInSecondGenomeFromFirstGenome, clusterName)
		 					else:
		 						clusterName = processIfNotMerge(firstGenomeProt, protHitInSecondGenomeFromFirstGenome, clusterName)



def processIfNotMerge(firstGenomeProt, protHitInSecondGenomeFromFirstGenome, clusterName):
	if firstGenomeProt not in PROT_TO_CLUSTERSET:
		if protHitInSecondGenomeFromFirstGenome not in PROT_TO_CLUSTERSET:
			PROT_TO_CLUSTERSET[firstGenomeProt].add(clusterName)
			CLUSTER_TO_PROTSET[clusterName].add(firstGenomeProt)
			PROT_TO_CLUSTERSET[protHitInSecondGenomeFromFirstGenome].add(clusterName)
			CLUSTER_TO_PROTSET[clusterName].add(protHitInSecondGenomeFromFirstGenome)
			clusterName+=1
		else:
			usedClusterNames = PROT_TO_CLUSTERSET[protHitInSecondGenomeFromFirstGenome]
			for clstName in usedClusterNames:
				PROT_TO_CLUSTERSET[firstGenomeProt].add(clstName)
				CLUSTER_TO_PROTSET[clstName].add(firstGenomeProt)
	else:
		if protHitInSecondGenomeFromFirstGenome not in PROT_TO_CLUSTERSET:
			usedClusterNames = PROT_TO_CLUSTERSET[firstGenomeProt]
			for clstName in usedClusterNames:
				PROT_TO_CLUSTERSET[protHitInSecondGenomeFromFirstGenome].add(clstName)
				CLUSTER_TO_PROTSET[clstName].add(protHitInSecondGenomeFromFirstGenome)
		else:
			usedClusterNames_ofFirstGenomeFirstProt = PROT_TO_CLUSTERSET[firstGenomeProt]
			usedClusterNames_ofFirstGenomeSecondProt = PROT_TO_CLUSTERSET[protHitInSecondGenomeFromFirstGenome]

			PROT_TO_CLUSTERSET[firstGenomeProt] = PROT_TO_CLUSTERSET[firstGenomeProt].union(usedClusterNames_ofFirstGenomeSecondProt)
			PROT_TO_CLUSTERSET[protHitInSecondGenomeFromFirstGenome] = PROT_TO_CLUSTERSET[protHitInSecondGenomeFromFirstGenome].unoin(usedClusterNames_ofFirstGenomeFirstProt)

			for clstName1 in usedClusterNames_ofFirstGenomeFirstProt:
				CLUSTER_TO_PROTSET[clstName1].add(protHitInSecondGenomeFromFirstGenome)

			for clstName2 in usedClusterNames_ofFirstGenomeSecondProt:
				CLUSTER_TO_PROTSET[clstName2].add(firstGenomeProt)
	return clusterName


def processIfDoMerge(firstGenomeProt, protHitInSecondGenomeFromFirstGenome, clusterName):
	if firstGenomeProt not in PROT_TO_CLUSTER:
		if protHitInSecondGenomeFromFirstGenome not in PROT_TO_CLUSTER:
			PROT_TO_CLUSTER[firstGenomeProt] = clusterName
			CLUSTER_TO_PROTSET[clusterName].add(firstGenomeProt)
			PROT_TO_CLUSTER[protHitInSecondGenomeFromFirstGenome] = clusterName
			CLUSTER_TO_PROTSET[clusterName].add(protHitInSecondGenomeFromFirstGenome)
			clusterName+=1
		else:
			usedClusterName = PROT_TO_CLUSTER[protHitInSecondGenomeFromFirstGenome]
			PROT_TO_CLUSTER[firstGenomeProt] = usedClusterName
			CLUSTER_TO_PROTSET[usedClusterName].add(firstGenomeProt)
	else:
		if protHitInSecondGenomeFromFirstGenome not in PROT_TO_CLUSTER:
			usedClusterName = PROT_TO_CLUSTER[firstGenomeProt]
			PROT_TO_CLUSTER[protHitInSecondGenomeFromFirstGenome] = usedClusterName
			CLUSTER_TO_PROTSET[usedClusterName].add(protHitInSecondGenomeFromFirstGenome)
		else:
			usedClusterName = PROT_TO_CLUSTER[firstGenomeProt]
			clusterNameToChange = PROT_TO_CLUSTER[protHitInSecondGenomeFromFirstGenome]
			if usedClusterName != clusterNameToChange:
				protsToChangeClstBelonging = CLUSTER_TO_PROTSET[clusterNameToChange]
				for prot in protsToChangeClstBelonging:
					PROT_TO_CLUSTER[prot] = usedClusterName
				CLUSTER_TO_PROTSET[usedClusterName] = CLUSTER_TO_PROTSET[usedClusterName].union(protsToChangeClstBelonging)
				del CLUSTER_TO_PROTSET[clusterNameToChange]
	return clusterName

def printData():
	os.chdir("..")
	with open(OUTPUT_FILENAME, "w") as outFile:
		sortedProts = sorted(CLUSTER_TO_PROTSET.values(), key=len)
		clstName = 1
		for proteins in sortedProts:
			outFile.write(str(clstName) + ":" + "\n")
			clstName+=1
			for prot in proteins:
				outFile.write(prot + "\n")		

def printProtData():
	# correct directory
	os.chdir(INPUT_PROTS_DIR)
	
	proteinFilesAll = os.listdir('.')
	proteinFilesUsed = []
	# getting only fa files and not .fa
	for item in proteinFilesAll:
		if item.endswith(".fa") and item != ".fa":
			proteinFilesUsed.append(item)

	for protFile in proteinFilesUsed:
		with open(protFile,"r") as fileOpened:
			linesReadUnsplit = fileOpened.read()
			linesRead = linesReadUnsplit.split(">")
			for protSequence in linesRead:
				possibleProtein = protSequence[protSequence.find("@") + 1:protSequence.find(".") + 2]
				if not UNIQUE_NETWORK:
					for prot in network:
						if possibleProtein == prot[:prot.find(".") + 2] and protSequence not in listOfProtsUsed:
							listOfProtsUsed.append(protSequence)
				else:
					for key in UNIQUE_CONNECTIONS_NETWORK:
						keyItem = key[key.find("_") + 1 : len(key)]
						if possibleProtein == keyItem and protSequence not in listOfProtsUsed:
							listOfProtsUsed.append(protSequence)			
		
	os.chdir("..")			
	with open(OUTPUT_PROTEINS_FILENAME,"w") as outProtFile:
		for i in range(0,len(listOfProtsUsed)):	
			outProtFile.write(listOfProtsUsed[i] + "\n")					
			
					


def indexFirstOrganism(network,variable):
	# Indexing to scientific organism name
	organism1 = network[variable][network[variable].find("["):network[variable].find("]")]
	# Indexing to protein identifier 
	identifier1 = network[variable][:network[variable].find(".") + 2]
	# Correct name assignment
	connection1 = (organism1[organism1.find("[") + 1:organism1.find("[") + 4] + '.' 
	+ organism1[organism1.find("_") + 1: organism1.find("_") + 4] + '_'  + identifier1)
	return connection1

def indexSecondOrganism(network,variable):
	organism2 = network[variable + 1][network[variable + 1].find("["):network[variable + 1].find("]")]
	# Indexing to protein identifier 
	identifier2 = network[variable + 1][:network[variable + 1].find(".") + 2]
	# Correct name assignment
	connection2 =  (organism2[organism2.find("[") + 
	1:organism2.find("[") + 4] + '.' + organism2[organism2.find("_") + 1: organism2.find("_") + 4] + '_' + identifier2)
	return connection2

def makeOrIncrementConnections(networkDictionary,connection1,connection2):
		if connection1 in networkDictionary.keys():
				networkDictionary[connection1] = networkDictionary[connection1] + 1
		else:
				networkDictionary[connection1] = 1
		if connection2 in networkDictionary.keys():
				networkDictionary[connection2] = networkDictionary[connection2] + 1
		else:
				networkDictionary[connection2] = 1

				
def printSifAllConnections(network):
	removedOrganisms = {'initial'}
	CONNECTIONS_NETWORK = dict()



	# Number of connections for COGs non-unique dictionary
	i = 0 
	while i < len(network):
		connection1 = indexFirstOrganism(network,i)
		connection2 = indexSecondOrganism(network,i)
		# Getting number of connections
		makeOrIncrementConnections(CONNECTIONS_NETWORK,connection1,connection2)
		i = i + 2
	if not UNIQUE_NETWORK: 
		# SIF Output for Non-unique COGs
		with open(OUTPUT_SIF_FILENAME, "w") as outSifFile:
			m = 0	
			while m < len(network):
				connection1 = indexFirstOrganism(network,m)
				connection2 = indexSecondOrganism(network,m)
				# Writing them appropriately ALL CONNECTIONS
				outSifFile.write(connection1 + '(' + str(CONNECTIONS_NETWORK[connection1]) + ')' +  '	pp	' +
				connection2  + '(' + str(CONNECTIONS_NETWORK[connection2]) + ')\n')
				m = m + 2

	# Getting organisms we do not want to display into a set
	
	for key in CONNECTIONS_NETWORK:
		comparedOrganism = key
		for otherOrganism in CONNECTIONS_NETWORK:
			if comparedOrganism[:6] == otherOrganism[:6]:
				if CONNECTIONS_NETWORK.get(comparedOrganism) < CONNECTIONS_NETWORK.get(otherOrganism):
					removedOrganisms.add(comparedOrganism)
    

	# Connections for Unique COG dictionary
	x = 0 
	while x < len(network):	
		connection1 = indexFirstOrganism(network,x)
		connection2 = indexSecondOrganism(network,x)
		if connection1 not in removedOrganisms and connection2 not in removedOrganisms:
			makeOrIncrementConnections(UNIQUE_CONNECTIONS_NETWORK,connection1,connection2)
		x = x + 2		
	
	# Output for Unique COG dictionary
	if BEST_HIT:		
		with open(OUTPUT_SIF_UNIQUE_CONNECTIONS_FILENAME, "w") as outSifUniqueFile:
			j = 0
			while j < len(network):
				connection1 = indexFirstOrganism(network,j)
				connection2 = indexSecondOrganism(network,j)
				# Writing the appropriately Unique Organisms
				if connection1 not in removedOrganisms and connection2 not in removedOrganisms:
					outSifUniqueFile.write(connection1 + ' (' + str(UNIQUE_CONNECTIONS_NETWORK[connection1]) + ')' +  '	pp	' +
					connection2  + ' (' + str(UNIQUE_CONNECTIONS_NETWORK[connection2]) + ')\n')
				j = j + 2
		

			
class DefaultOrderedDict(collections.OrderedDict):
    # Source: http://stackoverflow.com/a/6190500/562769
    def __init__(self, default_factory=None, *a, **kw):
        if (default_factory is not None and
           not isinstance(default_factory, collections.Callable)):
            raise TypeError('first argument must be callable')
        collections.OrderedDict.__init__(self, *a, **kw)
        self.default_factory = default_factory

    def __getitem__(self, key):
        try:
            return collections.OrderedDict.__getitem__(self, key)
        except KeyError:
            return self.__missing__(key)

    def __missing__(self, key):
        if self.default_factory is None:
            raise KeyError(key)
        self[key] = value = self.default_factory()
        return value

    def __reduce__(self):
        if self.default_factory is None:
            args = tuple()
        else:
            args = self.default_factory,
        return type(self), args, None, None, self.items()

    def copy(self):
        return self.__copy__()

    def __copy__(self):
        return type(self)(self.default_factory, self)

    def __deepcopy__(self, memo):
        import copy
        return type(self)(self.default_factory,
                          copy.deepcopy(self.items()))

    def __repr__(self):
        return 'OrderedDefaultDict(%s, %s)' % (self.default_factory,
                                               collections.OrderedDict.__repr__(self))

def main(argv):
	initialyze(argv)
	processInputData()
	createCOGs()
	printData()
	printSifAllConnections(network)
	printProtData()
	

if __name__ == "__main__":
	main(sys.argv)




