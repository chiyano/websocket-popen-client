websocket-popen-client
======================

websocket-popen-client is a client for websocket-popen-server. 
See https://github.com/chiyano/websocket-popen-server.

## Requirement

Java 1.6 later

## Getting Started

Download the jar file from:

https://github.com/chiyano/websocket-popen-client/raw/master/bin/websocket-popen-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar

In now, websocket-popen-server is running in localhost:9999, 
do the following.

    % cat <<EOF | java -jar bin/websocket-popen-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar ws://localhost:9999/ws/wc+-l
    foo
    bar
    baz
    EOF
           3

The above example executes wc -l. + is the command delimiter character.

## License

Copyright (c) 2013 Chiyano

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
