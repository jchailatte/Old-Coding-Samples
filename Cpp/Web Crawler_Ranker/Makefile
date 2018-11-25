CFLAGS = -g -Wall -Wextra -Wconversion -std=c++11

all: search crawler

search: search.o searchfunc.o datafunc.o pagerank.o
	g++ $(CFLAGS) search.o searchfunc.o datafunc.o pagerank.o -o search

search.o: search.cpp
	g++ $(CFLAGS) -c search.cpp -o search.o

searchfunc.o: searchfunc.cpp
	g++ $(CFLAGS) -c searchfunc.cpp -o searchfunc.o

datafunc.o: datafunc.cpp
	g++ $(CFLAGS) -c datafunc.cpp -o datafunc.o

pagerank.o: pagerank.cpp
	g++ $(CFLAGS) -c pagerank.cpp -o pagerank.o


crawler: crawler.o
	g++ $(CFLAGS) crawler.o -o crawler

crawler.o: crawler.cpp
	g++ $(CFLAGS) -c crawler.cpp -o crawler.o

clean: 
	rm -rf *o search crawler

