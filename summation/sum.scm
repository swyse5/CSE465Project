; Stuart Wyse
; CSE 465 - Term Project
; sum.scm

(define count 0)

(define (SumListH sum L target count)
  (set! count (+ 1 count)) ; add one to count
  (if (null? L) ; is the list null?
      (if (= target sum) ; if yes, check for target
          (if (< count 2) #f ; if target = sum & count < 2, return #f
              #t ; if target = sum & count !< 2, return #t
          )
          #f ; list is null and target != sum, return #f
      )
      (if (= target sum) ; list is not null, so check for target
          (if (> count 2) #t) ; target = sum, so check for count > 2 - if yes, return #t
          (SumListH (+ sum (car L)) (cdr L) target count) ; target != sum, so call function again
      )
  )
)

(define (SumList L target)
  (if (memq target L) #t ; if target is in list, return #t
      (SumListH 0 L target 0)
  )
)

(SumList '(1 2 3 4) 10)
(SumList '(1 2 3 4) 14)
(SumList '(1 2 3 4) 0)
(SumList '(0 1 2 3 4) 0)
(SumList '(1 2 3 -4) -3)
(SumList '(1 2 8 2) 5)
