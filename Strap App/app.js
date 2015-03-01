var StrapKit = require('strapkit');

// Show splash screen while waiting for data
var splashPage = StrapKit.UI.Page();
var sessionsPage = StrapKit.UI.Page();
var optionsPage = StrapKit.UI.Page();
var confirmPage = StrapKit.UI.Page();

// // Text element to inform user
// var txt = StrapKit.UI.TextView({
//     position: 'center',
//     text: 'StrapClick!'
// });

// // Add to splashPage and show
// splashPage.addView(txt);
var masterSession = "";

var card = StrapKit.UI.Card({
  title: "StrapClick!",
  body: "Join a session."
});

var card2 = StrapKit.UI.Card({
  title: "Sent!",
  body: "Go back to answer next question."
});

var mItem = {
    title: '',
    subtitle: '',
    data: {info: ""}
}

var menuItems = [
    {
        title: 'A',
        subtitle: 'Option A',
        data: {info: "A"}
    },
    {
        title: 'B',
        subtitle: 'Option B',
        data: {info: "B"}
    },
    {
        title: 'C',
        subtitle: 'Option C',
        data: {info: "C"}
    },
    {
        title: 'D',
        subtitle: 'Option D',
        data: {info: "D"}
    },
    {
        title: 'E',
        subtitle: 'Option E',
        data: {info: "E"}
    }];

var resultsMenu = StrapKit.UI.ListView({
    items: menuItems
});

var sessionMenu = StrapKit.UI.ListView({
    items: menuItems
});





splashPage.addView(card);
confirmPage.addView(card2);
//optionsPage.addView(resultsMenu);


splashPage.show();

card.setOnClick(function() {
    StrapKit.HttpClient({
        url: 'http://6d6ba094.ngrok.com',
        method: 'POST',
        data: { 'session': 'clientStart', 'requestType': 'client'},
        headers: { 'content-type': 'application/json'},
    },
    function (data) {
        //var sessionList = data.sessions;

        var sessMenuItems = [
        {
            title: "ngrok",
            subtitle: 'Session 1',
            data: {info: "ngrok"}
        },
        {
            title: "cs252",
            subtitle: 'Session 2',
            data: {info: "E"}
        },
        {
            title: "Soccer Club Callout",
            subtitle: 'Session 3',
            data: {info: "E"}
        },
        {
            title: "Food Survey",
            subtitle: 'Session 4',
            data: {info: "E"}
        },
        {
            title: "CS Conference",
            subtitle: 'Session 5',
            data: {info: "E"}
        }];

        sessionMenu = StrapKit.UI.ListView({
             items: sessMenuItems
        });
        
        sessionsPage.addView(sessionMenu);
        sessionsPage.show();

        sessionMenu.setOnItemClick(function(e) {
        var session = "";
        switch (e.itemIndex) {
            case 0:
                masterSession = "ngrok";
                break;
            case 1:
                masterSession = "cs252";
                break;
            case 2:
                masterSession = "Soccer Club Callout";
                break;
            case 3:
                masterSession = "Food Survey";
                break;
            case 4:
                masterSession = "CS Conference";
                break;
        }
        optionsPage.addView(resultsMenu);
        optionsPage.show();
        });


    }
    , console.log("unsuccessful"));
});


resultsMenu.setOnItemClick(function(e) {
    var answer = "";
    switch (e.itemIndex) {
        case 0:
            answer = "A";
            break;
        case 1:
            answer = "B";
            break;
        case 2:
            answer = "C";
            break;
        case 3:
            answer = "D";
            break;
        case 4:
            answer = "E";
            break;
    }
    StrapKit.HttpClient({
        url: 'http://6d6ba094.ngrok.com',
        method: 'POST',
        data: { 'session': masterSession, 'requestType': 'client', 'answer': answer},
        headers: { 'content-type': 'application/json'},
    },
    function (data) {
        
        
        confirmPage.show();
    }
    , console.log("unsuccessful"));


});

