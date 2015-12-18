var request = require('sync-request');
var chars = ['A', 'B', 'C', 'D', 'E', 'F'];
var x = 0;

var baseUrl = "http://s3-eu-west-1.amazonaws.com/puzzleinabucket/";//AAAAAA.html"
var failureText = "you did not get all the questions correct";

execute();
console.log("END");

function execute(){
	for (c1 of chars) {
		for (c2 of chars) {
			for (c3 of chars) {
				for (c4 of chars) {
					for (c5 of chars) {
						for (c6 of chars) {
							x++;
							var word = c1 + c2 + c3 + c4 + c5 + c6;

						
							var url = baseUrl + word + ".html";
							console.log(x + " url: " + url);							
							var res = request('GET', url);
							var body = res.getBody().toString();
							//console.log(body);
							var index = body.indexOf(failureText);

							if(index == -1){		
								console.log("FOUND IT, url=" + url);	
								return;
							}else{
								console.log("FAIL");
							}								
						}
					}
				}
			}
		}	
	}
}

