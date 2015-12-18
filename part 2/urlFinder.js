var http = require('http');
var chars = ['A', 'B', 'C', 'D', 'E', 'F'];
var x = 0;

var baseUrl = "http://s3-eu-west-1.amazonaws.com/puzzleinabucket/";//AAAAAA.html"
var failureText = "you did not get all the questions correct";
var urlsToTry = [];



var maxConnections = 10;
var currentConnections = 0;
var foundSolution = false;

execute();

function execute(){

	console.log("START");	
	buildUrls();
	console.log("URLS BUILT");
	shuffle(urlsToTry);
	console.log("STARTING SEARCH");
	
	for (i = 0; i < maxConnections; i++) { 
		console.log("STARTING THREAD");
	    tryUrl();
	}
	
}

function tryUrl(){

	console.log("urlsToTry " + urlsToTry.length);	

	if(!foundSolution && urlsToTry.length > 0){	
		
		//foundSolution = true;
		
		if(currentConnections < maxConnections){
			currentConnections++;
			var urlToTry = urlsToTry.pop();
			console.log("TRYING URL: " + urlToTry);				
		
			var req = http.get(urlToTry , function(res) {
					
					var body = '';
					
					res.on('data', function (chunk) {						
						body += chunk;					
				  	});
					
					res.on('end', function () {
					   // console.log('BODY: ' + body);	
						
						var index = body.indexOf(failureText);						
						
						if(index == -1){		
							console.log("FOUND IT, url=" + urlToTry);	
							console.log(index);
							console.log(body);
							foundSolution = true;							
						}else{
							console.log("FAIL");
						}								
						
						currentConnections--;
						tryUrl();
						
					  });				
										
				});		
				
				req.end;	
		}					
	}	
}

function buildUrls(){
	for (c1 of chars) {
		for (c2 of chars) {
			for (c3 of chars) {
				for (c4 of chars) {
					for (c5 of chars) {
						for (c6 of chars) {
							x++;
							var word = c1 + c2 + c3 + c4 + c5 + c6;						
							var url = baseUrl + word + ".html";							
							urlsToTry.push(url);
							
							/*console.log(x + " url: " + url);							
							var res = request('GET', url);
							var body = res.getBody().toString();
							//console.log(body);
							var index = body.indexOf(failureText);

							if(index == -1){		
								console.log("FOUND IT, url=" + url);	
								return;
							}else{
								console.log("FAIL");
							}			*/					
						}
					}
				}
			}
		}	
	}

}

function shuffle(o){
    for(var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
    return o;
}

