/*
App for Project Echo
Zach Perry - perryz@purdue.edu
Matthew Tracy - tracy1@purdue.edu
HackIllinois 2015 - 2-27-15
*/

var StrapKit = require('strapkit');

function(data) {

    var menuItems = ['A', 'B', 'C']; //answer options. can be changed/more can be added

    StrapKit.Metrics.logEvent("MenuItems", menuItems); //console log

    var resultsPage = StrapKit.UI.Page();
        // Construct Menu to show to user
    var resultsMenu = StrapKit.UI.ListView({
        items: menuItems //creates results menu base on the given menu items
    });

    // Add an action for SELECT_BUTTON
    resultsMenu.setOnItemClick(function(e) {
    
        var info = e.item.data; //store info of button licked

        StrapKit.HttpClient({
            url: 'http://localhost?button='+info,   //sends the HTTP request for answer
            type: 'json'

        },function(){

        //detail page to confirm that the users' answer has been submitted
        var detailPage = StrapKit.UI.Page();
            // Create the Card for detailed view
            var detailCard = StrapKit.UI.Card({
                title: 'Your response has been submitted!',
                body: ''
            });
            detailPage.addView(detailCard);
            detailPage.show();

        });

    });

    // Show the Menu, hide the splash
    resultsPage.addView(resultsMenu);
    resultsPage.show();

    StrapKit.Metrics.logEvent("show/resultsPage");

    },
    //Error catching
    function(error) {
        console.log(error);
}
