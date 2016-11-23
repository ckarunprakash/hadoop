--BookCogroup.pig
books = load '/training/exercises/pig/books.txt' using PigStorage(',') 
	as (id:long,title:chararray,date:chararray);

purchases = load '/training/exercises/pig/book-purchases.txt'
	as (id:long,buyer:chararray,date:chararray);

result = COGROUP books by id INNER, purchases by id INNER;

store result INTO '/training/exercises/pig/bookPurchases';
