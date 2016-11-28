// Stuart Wyse
// CSE 465 - Term Project
// Z+- in C++

#include <string>
#include <fstream>
#include <iostream>
#include <cstring>
#inlcude "zpm.h"

int count = 0;

// Constructor
zpm::zpm() {

}

// Destructor
zpm::~zpm() {
  deletePointers();
}

// Finds the correct funtion to send the line to intepret it
void zpm::parseStatement(std::string line) {
  // if PRINT is not on a line, we know it's an assignment statement
  if(line.find("PRINT") == -1) {
    analyzeAssignment(line);
  } else {
    analyzePrint(line);
  }
}

void zpm::parseFile(char* file) {
  std::ifstream zpmFile;
  zpmFile.open(file);

  // parse each statement in the file
  std::string line = "";
  while(getline(zpmFile, line)) {
    lineNumber++;
    parseStatement(line);
  }
}

void zpm::analyzeAssignment(std::string line) {
  // name of variable
  std::string variable = nextToken(&line);
  // assignment operator
  std::string operator1 = nextToken(&line);
  // value on right side of statement
  std::string val = nextToken(&line);
  Data value;

  Type variableType = findType(val);

  switch(variableType) {
    // Type variable
    case typeVar: {
      // if it doesn't have value, throw error
      if(varTable.find(stringVal) == varTable.end()) {
        runtimeError();
      } else {
        value = varTable[stringVal];
      }
      break;
    }

    // Type string
    case typeString: {
      value.typeFlag = typeString;
      stringVal = trimQuotes(stringVal);
      value.s = new char[stringVal.length() + 1];
      std::strcpy(value.s, stringVal.c_str());
      break;
    }

    // Type int
    case typeInt: {
      value.typeFlag = typeInt;
      value.i = std::stoi(stringVal);
      break;
    }
    // If not one of those types, throw error
    default:
    runtimeError();
  }

  // Make sure the operator is valid, depending on the variableType
  if(operator1 != "=") {
    if(varTable[variable].typeFlag != value.typeFlag) {
      runtimeError();
    }
    if((operator1 == "*=" || operator1 == "-=") && value.typeFlag != typeInt) {
      runtimeError();
    }
  }

  // Assign based on the type of assignment
  if(operator1 == "=") {
    varTable[variable] = value;
  } else if(operator1 == "+=") {
    if(value.typeFlag == typeInt) {
      varTable[variable].typeFlag = typeInt;
      varTable[variable].i = varTable[variable].i + value.i;
    } else {
      varTable[variable].typeFlag = typeString;
      int newLength = strlen(varTable[variable].s) + strlen(value.s) + 1;
      char* temp = new char[newLength];

      strcpy(temp, varTable[variable].s);
      strcat(temp, value.s);
      strcpy(varTable[variable].s, temp);
      delete temp;
      temp = NULL;
    }
  } else if(operator1 == "*=") {
    varTable[variable].typeFlag = typeInt;
    varTable[variable].i = varTable[variable].i * value.i;
  } else {
    varTable[variable].typeFlag = typeInt;
    varTable[variable].i = varTable[variable].i - value.i;
  }
}

// Print variable name and value of variable
void zpm::analyzePrint(std::string line) {
  nextToken(&line);
  std::string varName = nextToken(&line);

  if(varTable.find(varName) == varTable.end()) {
    runtimeError();
  } else {
    // Print out variable name and value
    Data value = varTable[varName];
    switch(value.typeFlag) {
      case typeString:
        std::cout << varName << "=" << value.s << std::endl;
        break;

      case typeInt:
        std::cout << varName << "=" << value.i << std::endl;
        break;

      default:
      // Should never get here
      runtimeError();
    }
  }
}

// Determines type of string
// Options are: string, int, or variable
Type zpm::findType(std::string token) {
  if(token[0] == '\"') {
    return typeString;
  } else if(isdigit(token[0])) {
    return typeInt;
  } else {
    return typeVar;
  }
}

// Gets first token in the string and removes it from the line
std::string zpm::nextToken(std::string* line) {
  // find the first space
  int delimiterIndex = line->find_first_of(" ");
  std::string token = line->substr(0, delimiterIndex);
  token = trim(&token);

  *line = line->substr(delimiterIndex, (line->length() - delimiterIndex));
  *line = trim(line);

  return token;
}


// Deletes the varTable pointers
// This is used in the destructor and runtimeError function
void zpm::deletePointers() {
  typedef std::map<std::string, Data>::iterator it_type;
  for(it_type iterator = varTable.begin(); iterator != varTable.end(); iterator++) {
    delete iterator->second.s;
    iterator->second.s = NULL;
  }
}

// If there is leading or trailing whitespace on a string, this trims that out
// Got some help from StackOverflow to do this
std::string zpm::trimWhitespace(std::string* str) {
  int start = str->find_first_not_of(" ");
  int end = str->find_last_not_of(" ");
  return str->substr(begin, (end-begin+1));
}

// Takes in a string and returns that string, but without the quotes ("") around it
std::string zpm::trimQuotes(std::string str) {
  return str.substr(1, str.length() - 2);
}

// Throw runtime error with line number of error
void zpm::runtimeError() {
  std:cerr <<"Runtime error on line: " <<lineNumber <<std::endl;
  deletePointers();
  exit(1);
}

int main(int argc, char* argv[]) {
  if(argc <= 1) {
    std::cerr << "ERROR: File name missing.\n" << std::endl;
    return -1;
  }

  char* zpmFileName = argv[1];
  zpm* interpreter = new zpm();

  interpreter->parseFile(zpmFileName);

  delete interpreter;
  interpreter = NULL;

  return 0;
}
