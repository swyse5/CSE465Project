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
