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
