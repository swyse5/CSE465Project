/**
* So far, this only returns the total sum of the list
*/

summation([H|_]) :- H = 0.
summation([_|T]) :- summation(T).
summation([], 0).
summation([H|T], S) :- summation(T, S2), S is H + S2.