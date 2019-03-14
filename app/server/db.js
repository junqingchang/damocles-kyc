const nano = require('nano');

// require('dotenv').load();
// let username = process.env.cloudant_username || "nodejs";
// let password = process.env.cloudant_password;
// let url = "https://"+username+":"+password+"@"+username+".cloudant.com"
// let cloudant = nano("https://"+username+".cloudant.com");

/* cloudant.auth(username, password, function (err, body, headers) {
  if (!err) {
    cookies[username] = headers['set-cookie'];
    cloudant = nano({
      url: "https://"+username+".cloudant.com",
      cookie: cookies[username]
    });
    
    // ping to ensure we're logged in
    cloudant.request({
      path: 'test_porter'
    }, function (err, body, headers) {
      if (!err) {
        console.log(body, headers);
      }
      else {
        console.log("Could not connect to server.")
      }
    });
  }
}); */

let url = '<db-url>';

let account = nano(url);
let db = account.use('user_details');
let dbm = account.use('model');


exports.getDatabase = function() {
  return db; 

}
exports.getModelDatabase =  function() {
  return dbm;
}
