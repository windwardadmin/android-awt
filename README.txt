android-awt
=============

This project replaces java.awt and javax.imagio since we can't use 
these classes on Android.  This GitHub project is created to maintain any minor modifications we may need to make from the original code from appengine-awt.


=========
CHANGELOG
=========

 1.0.0 - initial branch from appengine-awt and project set-up


============
DEPENDENCIES
============
	
IMPORTANT: You need to add the included jars to the local Maven repository in
		   order to build android-awt.  To do this, run the .bat script
		   located in the dep directory.

 -  Maven
	In order to build sfntly and android-awt, you need Maven (it was already
	set up well for both projects) -- Maven will basically generate all the
	jars without a second thought.

 -  sfntly (jar included in dep)
    Located at: https://code.google.com/p/sfntly/
	

========
BUILDING
========

 -  This project uses Maven (I didn't want to set up ant and Maven worked
    perfectly well and was already set up).  You should check the Dependencies
	section before building to make sure dependencies are satisfied!!

		mvn clean - clean up generated files
		mvn compile - compile
		mvn test - run tests (haha j/k; there are none)
		mvn package - generate a jar <-- This is most likely what you want
		mvn install - install to the local Maven repository

 -  any output files generated are usually placed in target\ such as the jar and
    class files.

 -  The version number can be updated in pom.xml


=====
ABOUT
=====