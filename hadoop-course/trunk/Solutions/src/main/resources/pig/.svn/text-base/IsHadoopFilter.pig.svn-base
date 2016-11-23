--IsHadoopFilter.pig
REGISTER Solutions.jar
DEFINE isHadoop pig.advanced.IsHadoop();
books = load '/training/exercises/pig/books.txt' using PigStorage(',') 
	as (id:long,title:chararray,date:chararray);

onlyHadoopInTitle = FILTER books BY isHadoop(title); 

dump onlyHadoopInTitle;
