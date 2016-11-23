--BookJoin.pig
books = load '/training/exercises/pig/books.txt' using PigStorage(',') 
	as (id:long,title:chararray,date:chararray);

purchases = load '/training/exercises/pig/book-purchases.txt'
	as (id:long,buyer:chararray,date:chararray);

result = join books by id, purchases by id;

store result INTO '/training/exercises/pig/bookPurchases';
