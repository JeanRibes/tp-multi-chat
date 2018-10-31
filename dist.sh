rm -rf dist
mkdir -p dist
cp src/BoilerPlateChat.java dist
cp src/ChatConnector.java dist
cp src/TerminalChat.java dist
unzip lib/Java-WebSocket-1.3.8.jar -d dist
zip -r chat-builder.zip dist/
