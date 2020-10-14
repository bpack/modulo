function() {
    var Base64 = Java.type('java.util.Base64');
    var encoded = Base64.getEncoder().encodeToString('user:password'.bytes);
    return 'Basic ' + encoded;
}