// Ryan Schlachter
// 2017-03-27
// HW5 - Modification of HW4, using a class, ctor/dtor, and const class object overloading.

#include "U.h"
#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <sstream>
#include <set>
using namespace std;
typedef unsigned long long int ull;

//return values:
// 0 for invalid utf-8 byte
// 1 for 0xxxxxxxx
// 2 for 110xxxxx
// 3 for 1110xxxx
// 4 for 11110xxx
// 5 for 10xxxxxx
int checkByte(unsigned char byte) {
	if ((byte >> 7) == 0x0) { 		// byte mask = 0xxxxxxx
		return 1;
	} else if((byte >> 5) == 0x6) { // byte mask = 110xxxxx
		return 2;
	} else if((byte >> 4) == 0xE) {	// byte mask = 1110xxxx
		return 3;
	} else if((byte >> 3) == 0x1E) {// byte mask = 11110xxx
		return 4;
	} else if((byte >> 6) == 0x2) {	// byte mask = 10xxxxxx
		return 5;
	} else {
		throw string("improperly formatted byte.");
		return 0;					// byte mask DNE
	}
}

string U::utf8encode (ull value) {
	vector<unsigned char> convertedBytes;

	// convert each byte
	// push_back converted char byte into a vector
	// loop through vector at end and concatenate chars into string
	// return encoded utf-8 string

	// 0xxxxxxx
	if(value < 0x80) { // less than 0x80: 1000 0000
		convertedBytes.push_back((char)(value));
		//cout << "value: " << hex << value << '\n'; 
	}
	// 110xxxxx 10xxxxxx
	// will be expressed as 11 bits, in two bytes. shift -> 6 to fill first byte, 
	// OR first byte with 110xxxxx to get masked value
	// AND second byte (6 remaining bits) with 0011 1111 get rid of two MSB's
	// OR second byte with 10xx xxxx to apply mask
	else if(value < 0x800) { // Unicode 0x800 = 1000 0000 0000 value is <= 11 bits long
		convertedBytes.push_back((char)(0xC0 | (value >> 6)));		// 0xC0 = 1100 0000
		convertedBytes.push_back((char)(0x80 | (value & 0x3F)));	// 0x3F = 0011 1111
		//cout << "value: " << hex << value << '\n'; 
	}
	// 1110xxxx 10xxxxxx 10xxxxxx
	// will be expressed as 16 bits, 4 + 6 + 6 = 16
	// -> shift 12 to get first 4 bits, OR with mask 1110 0000
	// -> shift 6 to get second 6 bits, AND with 0x3F to get zero's out front, followed by 6 bits I want
	//	  OR it with mask to append mask bits out front
	else if(value < 0x10000) { // Unicode 0x10000 = 0001 0000 0000 0000 0000 value is <= 16 bits long
		convertedBytes.push_back((char)(0xE0 | (value >> 12))); 	  // (11100000 | (value >> 12))	
		convertedBytes.push_back((char)(0x80 | (value >> 6 & 0x3F))); // (10000000 | (value >> 6 & 00111111))
		convertedBytes.push_back((char)(0x80 | (value & 0x3F)));	  // (10000000 | (value & 00111111))
		//cout << "value: " << hex << value << '\n'; 
	}
	// 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
	// will be expressed as 21 bits: 3 + 6 + 6 + 6 = 21
	// -> shift 18 to get first 3; OR with mask
	// 12 to get second 6, AND with 0011 1111 to get 0's in first two bits 
	// OR with 1000 0000 to apply mask
	// -> 6 to get third 6, do same thing
	// do last 6
	else if(value < 0x200000) { // Unicode 0x200000 = 0010 0000 0000 0000 0000 0000 value is <= 21 bits
		convertedBytes.push_back((char)(0xF0 | (value >> 18)));			// (11110xxx | (value >> 18))
		convertedBytes.push_back((char)(0x80 | (value >> 12 & 0x3F)));	// (10xxxxxx | (value >> 12 & 00111111))
		convertedBytes.push_back((char)(0x80 | (value >> 6 & 0x3F)));	// (10xxxxxx | (value >> 6 & 00111111))
		convertedBytes.push_back((char)(0x80 | (value & 0x3F)));		// (10xxxxxx | (value & 00111111))
	}

	string returnString("");

	for(unsigned int i = 0; i < convertedBytes.size(); i++) {
		returnString += convertedBytes[i];
	}

	return returnString;
} 

void fillcmap(string key, map<string, int> &cmap) {

	if(cmap.count(key) == 0) { 		// not found in countMap
		// not found, add to countMap
		cmap.insert(pair<string,int> (key, 0));
	}
}

void searchMap(string key, map<string, string> &pmap, map<string, int> &cmap) {
	string mapKey;

	if(pmap.count(key) == 0) { 		// not found in property map
		//cout << "searchMap: didn't find: " << key << '\n';
		//throw string("character doesn't exist in properties file.");								
	} else {
		//cout << "byteCount: " << byteCount << '\n';
		//cout << "byteConcat: " << byteConcat << '\n';
		mapKey = pmap.find(key)->second;		// IS found, get property value
		//cout << key << "   <->   " << mapKey << '\n';
		cmap.find(mapKey)->second++;				// find property value in countMap and increment
		//cout << "found: " << cmap.find(mapKey)->second << '\n';
	}
}

//Default constructor
//Accumulated string is initially empty. No properties so far.
U::U() {
	map<string, string> propertyMap;
	map<string,int> countMap;
	string accumulated;
	string readfirst;
}


//Copy constructor
//Takes another U, and copies the information.
U::U(const U& copy) {
	accumulated = copy.accumulated;
	propertyMap = copy.propertyMap;
	countMap = copy.countMap;
}

//Test Constructor
//Takes two std::string arguments: a properties filename, and some literal data. It parses the properties file as if propfile were called, then processes the literal data as if it were read from a file. Throws a std::string error message upon error.
U::U(string filename, string literal) {

	propfile(filename);
	ifstream in(literal);

	if(propertyMap.size() == 0) {
		throw string("no properties specified to count. need properties file.");
	}
	if(in.fail()) {
		readstring(literal);
	} else {
		readfile(literal);
	}
}

//Assignment operator
//Takes another U, and copies the information.
U& U::operator=(const U& copy) {
	accumulated = copy.accumulated;
	//cout << "accumulated: " << accumulated << '\n';
	propertyMap = copy.propertyMap;
	countMap = copy.countMap;
	return *this;
}

//Destructor
//Destroys
U::~U() {

}

void U::readstring(string literal) {
	//cout << "got to readstring." << '\n';
	char nextChar;
	string byteConcat;
	int whileCount = 0;
	bool firstChar;


	for(unsigned int i = 0; i <= literal.length(); i++) {
		if(whileCount == 0) {
			firstChar = true;
		}

		nextChar = literal[i];

		//cout << "byteConcat: " << byteConcat << '\n';
		unsigned char byte = nextChar;
		int byteCount = checkByte(byte);

		//cout << "byteCount: " << byteCount << '\n';
		if(byteCount == 0) {
			throw string("invalid unicode byte read.");
		} else if(byteCount == 1) {	// mask: 0xxxxxxx = 1 byte long
			//cout << "byteConcat: " << byteConcat << '\n';
			//cout << "searching for: " << byteConcat << '\n';
			searchMap(byteConcat, propertyMap, countMap);
			accumulated += byteConcat;
			byteConcat.clear();	
			byteConcat += (char)byte;
		} else if(byteCount == 2 || byteCount == 3 || byteCount == 4) {	
			if(!firstChar) {	// first character of file
				byteConcat += (char)byte;
				whileCount++;
				//cout << "byteConcat: " << byteConcat << '\n';
			} else { // NOT first character, data stored in byteConcat
				//cout << "whileCount: not 0" << '\n';
				//cout << "searching for: " << byteConcat << '\n';
				searchMap(byteConcat, propertyMap, countMap);
				accumulated += byteConcat;
				byteConcat.clear();
				byteConcat += (char)byte;
				//cout << "byteConcat: " << byteConcat << '\n';
			}
		} else if(byteCount == 5) {	// continuation: 10xxxxxx
			byteConcat += (char)byte;
			//cout << "byteConcat: " << byteConcat << '\n';
		} 
	}

	// newline at end of file
	if(nextChar == '\n') {
		//cout << "searching for: " << byteConcat << '\n';
		searchMap(byteConcat, propertyMap, countMap);
		accumulated += byteConcat;
	}
}

//Takes a std::string filename, and reads the UTF-8 characters from it. Throws a std::string error message upon error. If called again, the data accumulates.
void U::readfile(string filename) {
	//cout << "got to readfile." << '\n';

	ifstream inputFile(filename);
	char nextChar;

	if(inputFile.fail()) {
		throw string("bad read file provided as argument.");
	} 

	// PROPERTIES MAP DNE - STORE IN STRING
	if(propertyMap.size() == 0) {
		while(inputFile.get(nextChar)) {
			readfirst += nextChar;
		}
		//cout << "readfirst: " << readfirst << '\n';

	}
	else {

		string byteConcat;
		int whileCount = 0;
		bool firstChar;

		while(inputFile.get(nextChar)) {
			if(whileCount == 0) {
				firstChar = true;
			}

			//cout << "byteConcat: " << byteConcat << '\n';
			unsigned char byte = nextChar;
			int byteCount = checkByte(byte);

			//cout << "byteCount: " << byteCount << '\n';
			if(byteCount == 0) {
				throw string("invalid unicode byte read.");
			} else if(byteCount == 1) {	// mask: 0xxxxxxx = 1 byte long
				//cout << "byteConcat: " << byteConcat << '\n';
				//cout << "searching for: " << byteConcat << '\n';
				searchMap(byteConcat, propertyMap, countMap);
				accumulated += byteConcat;
				byteConcat.clear();	
				byteConcat += (char)byte;
			} else if(byteCount == 2 || byteCount == 3 || byteCount == 4) {	
				if(!firstChar) {	// first character of file
					//cout << "whileCount: 0" << '\n';
					byteConcat += (char)byte;
					whileCount++;
					//cout << "byteConcat: " << byteConcat << '\n';
				} else { // NOT first character, data stored in byteConcat
					//cout << "whileCount: not 0" << '\n';
					//cout << "searching for: " << byteConcat << '\n';
					searchMap(byteConcat, propertyMap, countMap);
					accumulated += byteConcat;
					byteConcat.clear();
					byteConcat += (char)byte;
					//cout << "byteConcat: " << byteConcat << '\n';
				}
			} else if(byteCount == 5) {	// continuation: 10xxxxxx
				byteConcat += (char)byte;
				//cout << "byteConcat: " << byteConcat << '\n';
			} 
		}

		// newline at end of file
		if(nextChar == '\n') {
			searchMap(byteConcat, propertyMap, countMap);
			accumulated += byteConcat;
		}
	}
}	

//Takes a std::string properties filename. Parses that file. Throws a std::string error message upon error.
void U::propfile(string propfile) {
	//cout << "got to propfile." << '\n';
	ifstream in(propfile);
	string uniString, hexString, propVal, inputLine, secondField, discard, key;

	// case 1: bad property file
	if(in.fail()) {
		throw string("bad property file given as argument.");
	} 

	int whileCount = 0;
	while(!in.eof()) {
		getline(in, uniString, ';') && getline(in, secondField, ';') && getline(in, propVal, ';') && getline(in, discard); 
		//cout << "size: " << uniString.size() <<'\n';
		//cout << "uniString: " << uniString <<'\n';
		if(uniString.length() == 0 && whileCount == 0) {
			throw string("empty property file given as argument.");
		}

		//cout << hex << value << '\n';
		ull value = strtol(uniString.c_str(), NULL, 16);
		propertyMap.insert(pair <string, string> (utf8encode(value), propVal));

		if(countMap.count(propVal) == 0) { 		// not found in countMap
		// not found, add to countMap
			countMap.insert(pair<string,int> (propVal, 0));
		}

		whileCount++;	
	}

	// readfile was already called and stored into readfirst
	if(readfirst != "") {
		readstring(readfirst);
		readfirst.clear();
	}

	//cout << "countMap: " << '\n';
	//for(const auto &p : countMap) {
	//	cout << p.first << ": " << p.second << '\n';
	//}
}

//Returns total number of UTF-8 characters read, as an int.
int U::size() {
	int totalSize = 0;
	//cout << "accumulated: " << accumulated << '\n';

	for(const auto &p : countMap) {
		totalSize += p.second;
	}
	//cout << "totalSize: " << totalSize << '\n';
	return totalSize;
}

//Takes no arguments, and returns all the accumulated UTF-8 characters as one big std::string.
string U::get() {
	//cout << "returnString: " << accumulated << '\n';
	return accumulated;
}

//Takes an int index, and returns a std::string containing the UTF-8 character at that point. The first UTF-8 character is at index zero. Throws a std::string error for an invalid index.
string U::get(int index) {

	string byteConcat;

	//cout << "byteCount: " << byteCount << '\n';
	for(int i = index; i != 0; i--) {
		unsigned char byte = accumulated[i];
		int byteCount = checkByte(byte);
		//cout << "index: " << i << '\n';
		//cout << "byteCount: " << byteCount << '\n';
		if(byteCount == 0) {
			throw string("invalid unicode byte read.");
		}
		else if(byteCount == 1) {	// is ASCII, return char
			byteConcat = accumulated[index];
			break;
		}
		else if(byteCount == 2 || byteCount == 3 || byteCount == 4) {	// is leading byte
			for(int j = i; j < i+byteCount; j++) {
				//cout << "index: " << j << '\n';
				//cout << "index+byteCount: " << i+byteCount << '\n';
				byteConcat += accumulated[j];
			}
			//cout << "byteConcat: " << byteConcat << '\n';
			break;
		}
		else if(byteCount == 5) {	// continuation, decriment and find first byte
			continue;
		}	
	}

	return byteConcat;
}

//Like get, but takes two ints, starting and ending positions. They represent a half-open interval: get(7,9) returns a std::string containing two UTF-8 characters, the one at 7, and the one at 8. Throws a std::string error for an invalid argument.
string U::get(int first, int last) {
	string returnString("");
	//cout << "accumulated: " << accumulated << '\n'; 
	//cout << "first: " << accumulated[first+1] << "last: " << accumulated[last+1] << '\n';

	if(first < 0) {
		throw string("invalid start index.");
	}
	else if(last > size()) {
		//cout << size() << '\n';
		throw string("invalid last index.");
	}
	else {
		for(int i = first+1; i <= last+1; i++) {
			returnString += accumulated[i];
		}
	}	

	//cout << "returnString: " << returnString << '\n';
	return returnString;

}

//Takes a std::string which is a property name (e.g., Lu) and returns how many times characters with that property have been encountered.
int U::propcount(string propname) {
	if(countMap.count(propname) == 0) {
		throw string("bad property specified to count.");
		return 0;
	} else {
		return countMap.find(propname)->second;
	}
}

void U::print() {
	//cout << "propertyMap: " << '\n'; 
	//for(const auto &p : propertyMap) {
	//	cout << p.first << ": " << p.second << '\n';
	//}
	cout << "countMap: " << '\n';
	for(const auto &q : countMap) {
		cout << q.first << ": " << q.second << '\n';
	}
	cout << "accumulated: " << accumulated << '\n';
}

//Takes no arguments; returns a std::set<std::string> of the names of all the possible properties, as read from the properties file.
set<string> U::props() {
	set <string> returnset;

	for(const auto &p : countMap) {
		returnset.insert(p.first);
	}
	//for(const auto &p : returnset) {
	//	cout << p  << " ";
	//}

	//cout << '\n';

	//cout << everyProp << '\n';
	return returnset;
}



/////////////////////////////////////////////////////////////////////
// 					    BEGIN CONST FUNCTIONS                      //
/////////////////////////////////////////////////////////////////////



//Returns total number of UTF-8 characters read, as an int.
int U::size() const {
	return size();
}

//Takes no arguments, and returns all the accumulated UTF-8 characters as one big std::string.
string U::get() const {
	U temp;
	temp.accumulated = accumulated;
	return accumulated;
}

//Takes an int index, and returns a std::string containing the UTF-8 character at that point. The first UTF-8 character is at index zero. Throws a std::string error for an invalid index.
string U::get(int index) const {
	U temp;
	temp.accumulated = accumulated;
	return temp.get(index);	
}

//Like get, but takes two ints, starting and ending positions. They represent a half-open interval: get(7,9) returns a std::string containing two UTF-8 characters, the one at 7, and the one at 8. Throws a std::string error for an invalid argument.
string U::get(int first, int last) const {
	U temp;
	temp.accumulated = accumulated;
	unsigned int f = first;
	unsigned int l = last;

	string returnString("");
	//cout << "temp.accumulated: " << temp.accumulated << '\n'; 
	//cout << "temp.accumulated.size(): " << temp.accumulated.size() << '\n'; 
	//cout << "first: " << accumulated[first+1] << "last: " << accumulated[last+1] << '\n';

	if(f < 0) {
		throw string("invalid start index.");
	}
	else if(l > temp.accumulated.size()) {
		//cout << size() << '\n';
		throw string("invalid last index.");
	}
	else {
		for(unsigned int i = f+1; i <= l+1; i++) {
			returnString += temp.accumulated[i];
		}
	}	

	//cout << "returnString: " << returnString << '\n';
	return returnString;
}

//Takes a std::string which is a property name (e.g., Lu) and returns how many times characters with that property have been encountered.
int U::propcount(string propname) const {
	U temp;
	temp.accumulated = accumulated;
	temp.countMap = countMap;
	temp.propertyMap = propertyMap;
	return temp.propcount(propname);
}


//Takes no arguments; returns a std::set<std::string> of the names of all the possible properties, as read from the properties file.
set<string> U::props() const{
	U temp;
	temp.returnset = returnset;
	temp.countMap = countMap;
	temp.propertyMap = propertyMap;
	return temp.props();
}