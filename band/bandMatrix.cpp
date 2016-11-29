// Stuart Wyse
// CSE 465 - HW5

#include "bandMatrix.h"

band::band(int size) {
	size = size;
	size1 = (3 * size) - 2;
	bandMatrix.resize(size1);
}

band::band(const band &mat) {
	this->size1 = mat.size1;
	this->size = mat.size1;
	for (int i = 0; i < mat.size1; i++)
	this->bandMatrix[i] = mat.bandMatrix[i];
}

band::band(band& mat2) {
	size = mat2.size;
	size1 = mat2.size1;
	for (int i = 0; i < size1; i++) {
		bandMatrix[i] = mat2.bandMatrix[i];
		mat2.bandMatrix[i] = 0;
	}
	mat2.size = 0;
	mat2.size1 = 0;
	for (int i = 0; i < size1; i++)
	mat2.bandMatrix.pop_back();
}

// destructer
band::~band() {
	
}

// set the value of the location passed in as row, col pair
void band::set(int row, int col, double num) {
	if (row <= size|| col <= size) {
		// set the main diagonal
		if (row == col) {
			bandMatrix[row] = num;
		}
		// set the diagonal above the main
		if (row == col - 1) {
			bandMatrix[size + row] = num;
		}
		// set the diagonal below the main
		if (col == row - 1) {
			bandMatrix[(size * 2 - 1) + col] = num;
		}
	} else {
		throw std::runtime_error("Index out of bounds.");
	}
}

// get value at passed in row, col pair
double band::get(int row, int col) {
	// the main diagonal
	if (row == col) {
		return bandMatrix[row];
	}
	// diagonal above the main
	if (row == col - 1) {
		return bandMatrix[size + row];
	}
	// diagonal below the main
	if (col == row - 1) {
		return bandMatrix[(size * 2 - 1) + col];
	} if (row > size || col > size) {
		throw std::runtime_error("Index out of bounds.");
	}
	return 0;
}
