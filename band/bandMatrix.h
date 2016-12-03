// Stuart Wyse
// CSE 465 - Term Project

#include <vector>
#include <iostream>

#pragma once

class band {
public:
	band(int);
	// copy constructor
	band(const band &matrix);
	// move constructor
	band(band& other);
	~band();
	void set(int, int, double);
	double get(int, int);

	int size;
	std::vector<double> bandMatrix;
	int size1;

	band &operator+(band &matrix1) {
		if (matrix1.size == this->size) {
			band newBand(matrix1.size);
			for (int i = 0; i < matrix1.size1; i++) {
				newBand.bandMatrix[i] = matrix1.bandMatrix[i] + this->bandMatrix[i];
			}
			return newBand;
		}
		else {
			throw std::runtime_error("Matrices sizes are not equal.");
		}
	}

	band &operator=(band &matrix) {
		band newBand(matrix.size);
		newBand.size1 = matrix.size1;
		for (int i = 0; i < matrix.size1; i++) {
			newBand.bandMatrix[i] = matrix.bandMatrix[i];
		}
	}
};
