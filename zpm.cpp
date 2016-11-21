// Stuart Wyse
// CSE 465 - Term Project
// Z+- in C++

#include <string>
#include <fstream>
#include <iostream>

using namespace std;

int count = 0;

void parseStatement(string line) {
  
}

int main(int argc, char* argv[]) {
  if(argc <= 1) {
    cout << "ERROR: File name missing.\n";
    return -1;
  }

  ifstream infile(argv[1]); // open the file

  if(infile.is_open() && infile.good()) {
    string line = "";
    while(getLine(infile, line)) {
      count++;
      parseStatement(line);
    }
  }

  return 0;
}
