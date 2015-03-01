var StrapKit = require('strapkit');

// Show splash screen while waiting for data
var splashPage = StrapKit.UI.Page();
var sessionsPage = StrapKit.UI.Page();
var optionsPage = StrapKit.UI.Page();

// // Text element to inform user
// var txt = StrapKit.UI.TextView({
//     position: 'center',
//     text: 'StrapClick!'
// });

// // Add to splashPage and show
// splashPage.addView(txt);

var card = StrapKit.UI.Card({
  title: "StrapClick!",
  body: "Join a session."
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





splashPage.addView(card);

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
        var sessionList = [];
        for (i = 0; i < data.sessions.length; i++) {
        //     var sessItem = {
        //         title: data.sessions[i],
        //         subtitle: 'Session',
        //         data: {info: "Session"}
        //     }
        //     sessionList.push(sessItem);
        // }
        // var sessionMenu = StrapKit.UI.ListView({
        //     items: sessionList
        }
        sessionsPage.addView(resultsMenu);
        sessionsPage.show();
    }
    , console.log("unsuccessful"));
});


