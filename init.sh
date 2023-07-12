#!/bin/bash
if [ -d "./mongodb_data" ]; then
	echo "Data dir is exists";
else
	echo "Data dir is not exists";
	mkdir "mongodb_data";
fi
