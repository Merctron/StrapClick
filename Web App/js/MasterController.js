var app = angular.module("strapClick", []);

app.service('sharedVars', function () {
        var sessionName = 'Name';

        return {
            getProperty: function () {
                return sessionName;
            },
            setProperty: function(value) {
                sessionName = value;
            }
        };
    });

app.controller("LandingPageController", function($scope, sharedVars, $http) {
	$scope.startSession = function() {
		window.location = "dashboard.html";
	};
});

app.controller("SessionPageController", function($scope, sharedVars, $http) {

	$scope.questionVals = []; 
	$scope.ansoptionVals = [];
	$scope.qindex = 0;
	$scope.qdindex = 0;
	$scope.qToEdit = "";
	$scope.qToDisplay = "";

	$scope.aCountNum = 0;
	$scope.bCountNum = 0;
	$scope.cCountNum = 0;
	$scope.dCountNum = 0;
	$scope.eCountNum = 0;

	$scope.aCount = "";
	$scope.bCount = "";
	$scope.cCount = "";
	$scope.dCount = "";
	$scope.eCount = "";


	var intervalID = 0;


	$scope.addQuestion = function() {
		$scope.questionVals.push($scope.questionValue);
		$scope.ansoptionVals.push([]);
		console.log($scope.questionVals);
		console.log($scope.ansoptionVals);
		console.log($scope.qindex);
	}
	$scope.clearQuestions = function() {
		$scope.questionVals = [];
		$scope.ansoptionVals = [];
		console.log($scope.questionVals);
		console.log($scope.ansoptionVals);
		console.log($scope.qindex);
	}
	$scope.showOptions = function(q) {
		for (i = 0; i < $scope.questionVals.length; i++) {
			if ($scope.questionVals[i] == q) 
				$scope.qindex = i;
		}
		$scope.qToEdit = $scope.questionVals[$scope.qindex]; 
		console.log($scope.questionVals);
		console.log($scope.ansoptionVals);
		console.log($scope.qindex);
	}
	$scope.addOption = function() {
		if ($scope.questionVals.length > 0)
			$scope.ansoptionVals[$scope.qindex].push($scope.optionValue);
		console.log($scope.questionVals);
		console.log($scope.ansoptionVals);
		console.log($scope.qindex);
	}

	$scope.startDisplaySession = function() {
		$scope.qdindex = 0;
		$scope.qToDisplay = $scope.questionVals[$scope.qdindex];
		$("#display").css("visibility","visible");
	}

	$scope.stopDisplaySession = function() {
		$("#display").css("visibility","hidden");
	}

	$scope.incrementQ = function() {
		if ($scope.qdindex < $scope.questionVals.length - 1) 
			$scope.qdindex++;
		$scope.qToDisplay = $scope.questionVals[$scope.qdindex];

		$scope.aCountNum = 0;
		$scope.bCountNum = 0;
		$scope.cCountNum = 0;
		$scope.dCountNum = 0;
		$scope.eCountNum = 0;

		$scope.aCount = "";
		$scope.bCount = "";
		$scope.cCount = "";
		$scope.dCount = "";
		$scope.eCount = "";
	}

	$scope.decrementQ = function() {
		if ($scope.qdindex > 0)
			$scope.qdindex--;
		$scope.qToDisplay = $scope.questionVals[$scope.qdindex];

		$scope.aCountNum = 0;
		$scope.bCountNum = 0;
		$scope.cCountNum = 0;
		$scope.dCountNum = 0;
		$scope.eCountNum = 0;

		$scope.aCount = "";
		$scope.bCount = "";
		$scope.cCount = "";
		$scope.dCount = "";
		$scope.eCount = "";
	}

	$scope.askQ = function() {

		intervalID = setInterval(function() {
			console.log("Text");
			var urlToSend = "http://6d6ba094.ngrok.com";
			var dataObj = {
				"requestType": "webApp",
          		"session": $scope.sessName,
          		"question": $scope.questionVals[$scope.qdindex],
          		"options": 5,
        	};

        	console.log(dataObj);
        	$.post(urlToSend, dataObj)
         	.done(function( data ) {
            	console.log(data.vals);
            	for (i = 0; i < data.vals.length; i++) {
            		if (data.vals[i] == "A") {
            			$scope.aCountNum++;
            		}
            		else if (data.vals[i] == "B") {
            			$scope.bCountNum++;
            		}
            		else if (data.vals[i] == "C") {
            			$scope.cCountNum++;
            		}
            		else if (data.vals[i] == "D") {
            			$scope.dCountNum++;
            		}
            		else if (data.vals[i] == "E") {
            			$scope.eCountNum++;
            		}
            	}

				$scope.aCount = $scope.aCountNum.toString();
				$scope.bCount = $scope.bCountNum.toString();
				$scope.cCount = $scope.cCountNum.toString();
				$scope.dCount = $scope.dCountNum.toString();
				$scope.eCount = $scope.eCountNum.toString();

				$("#opa").html("Option A: " + $scope.aCount);
				$("#opb").html("Option B: " + $scope.bCount);
				$("#opc").html("Option C: " + $scope.cCount);
				$("#opd").html("Option D: " + $scope.dCount);
				$("#ope").html("Option E: " + $scope.eCount);

            	console.log("Option A: " + $scope.aCount);
            	console.log("Option B: " + $scope.bCount);
            	console.log("Option C: " + $scope.cCount);
            	console.log("Option D: " + $scope.dCount);
            	console.log("Option E: " + $scope.eCount);
        	});
		}, 3000);
	}


	$scope.stopA = function() {
		clearInterval(intervalID);
	}
});