#!/usr/bin/python
import sys

for line in sys.stdin:
	for token in line.strip().split(" "):
		if token:
			sys.stderr.write("reporter:counter:Tokens,Total,1\n") 
			print token[0] + '\t1'