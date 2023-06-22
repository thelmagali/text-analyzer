#!/bin/bash
# Basic until loop
counter=1
until [ $counter -gt 100000 ]
do
uuidgen >> texts.txt
((counter++))
done
echo All done
