// Stuart Wyse
// CSE 465 - Term Project

#include <stdlib.h>
#include <map>
#include <iostream>

// string, int, or variable
enum Type {typeString, typeInt, typeVar};

struct Data {
	Type typeFlag;
	union {
		char* s;
		int i;
	};
};

class zpm {
	private:
		// Keeps track of variables
		std::map<std::string, Data> varTable;
		int lineNumber;

		// Trim leading and trailing whitespace
		std::string trimWhitespace(std::string* str);

		// Gives the next token in a line and removes it from string
		std::string nextToken(std::string* str);

		// Determines the type of a token
		Type findType(std::string token);

		// Removes the quotes around a token
		std::string trimQuotes(std::string token);

    // To throm runtime errors
		void runtimeError();

		// Deletes the char* pointers in varTable
		void deletePointers();

	public:
		zpm();
		~zpm();

		void parseFile(char* fileName);
		void parseStatement(std::string line);
		void analyzeAssignment(std::string line);
		void analyzePrint(std::string line);
};
