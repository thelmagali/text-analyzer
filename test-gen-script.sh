#!/bin/bash
# Basic until loop
counter=1
until [ $counter -gt 100000 ]
do
echo $(uuidgen | head -c 7) >> texts.txt
((counter++))
done
echo All done
