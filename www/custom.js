// function ShellExec() {
//   this.exec = function(cmd, callback) {
//     return cordova.exec(callback, function(err) {
//       callback({exitStatus: 100, output: err});
//     }, "ShellExec", "exec", [cmd]);

//   };
// }

// window.ShellExec = new ShellExec()

var exec = require('cordova/exec');

exports.enableUSBDebugging = function(success, error) {
    exec(success, error, "Custom", "enableUSBDebugging", []);
};

exports.disableUSBDebugging = function(success, error) {
    exec(success, error, "Custom", "disableUSBDebugging", []);
};

exports.getDebuggingStatus = function(success, error) {
    exec(success, error, "Custom", "getDebuggingStatus", []);
};