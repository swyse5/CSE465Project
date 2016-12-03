/**
* Stuart Wyse
* CSE 465 - Term Project
* summation.pl
*/

summation(List, Target) :-  sublst(List, X), sum(X, Target), !.

sublst([], []).
sublst([E|Tail], [E|NTail]) :- sublst(Tail, NTail).
sublst([_|Tail], NTail) :- sublst(Tail, NTail).

sum([N], N) :- number(N).
sum([H|T], N) :- number(H), sum(T, Y), N is H + Y.
