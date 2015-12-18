# GCHQSolver - part 2

Part 2 of the puzzle was a series of 6 multiple choice questions, each with 6 possible answers. The questions appeared to be rather cryptic and had seemingly impossible solutions. After clicking A for each question I reached the end page with the message "sorry you did not answer all of the questions correctly" but I couldn't help but notice the URL:  url=http://s3-eu-west-1.amazonaws.com/puzzleinabucket/AAAAAA.html

Clearly there was a duplicate HTML page bring hosted on Amazon S3 for EVERY SINGLE possible combination of answers (46,656). Apart from being a great showcase of what Amazon S3 can do, this was clearly another hint to me that the questions themselves are nonsense and a brute force attack was the idea here.

Hence I wrote this node.js script to generate every possible URL permutation and then execute a get request for each one. The logic to determine if th epage is the solution page is rather crude, it sinmply looks for the presence of the string "you did not get all the questions correc". The crudity did not matter however, it found the correct url in just a few minutes:


http://s3-eu-west-1.amazonaws.com/puzzleinabucket/DEFACE.html