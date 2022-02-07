# TaxiClusters

TaxiClustering program written by Easton Smith

This program makes use of the DBScan algorithm in order to take
taxi trip data and determine the best possible waiting points for taxi's 
based on where past trips were clustered.

Inside the src folder there is all the java classes used (Main, Cluster, TripRecord, GPScoord)
as well as a UML class diagram of the program.

Can find the compiled class files in (out/production/TaxiClusters/)

The File int (out) labeled 'Sample Output.csv' is the provided sample output and was not created by my program.

NOTE: This program reads csv files from the (data) folder, i.e. the yello_tripdata_2009-01-15.csv data is in the (data) folder.
Trying to read a csv from outside the data folder will result in the program failing.

File output will be in the (out) folder, and will always be titled in the format

{ TaxiClusterOut-*eps*-*minPts* }

where eps and minPts are the actual values the user inputted at the beginning of the program