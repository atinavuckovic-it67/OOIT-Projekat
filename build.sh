#!/bin/bash
set -e

# Find javac and java
if [ -f "/Users/katarinavuckovic/.gemini/antigravity-ide/scratch/jdk/jdk-17.0.14+7/Contents/Home/bin/javac" ]; then
    JAVAC_CMD="/Users/katarinavuckovic/.gemini/antigravity-ide/scratch/jdk/jdk-17.0.14+7/Contents/Home/bin/javac"
    JAVA_CMD="/Users/katarinavuckovic/.gemini/antigravity-ide/scratch/jdk/jdk-17.0.14+7/Contents/Home/bin/java"
elif find /Users/katarinavuckovic/.gemini/antigravity-ide/scratch/jdk -name javac 2>/dev/null | grep -q javac; then
    JAVAC_CMD="$(find /Users/katarinavuckovic/.gemini/antigravity-ide/scratch/jdk -name javac | head -n 1)"
    JAVA_CMD="$(find /Users/katarinavuckovic/.gemini/antigravity-ide/scratch/jdk -name java | head -n 1)"
elif which javac > /dev/null 2>&1; then
    JAVAC_CMD="javac"
    JAVA_CMD="java"
elif [ -f "/usr/local/opt/openjdk/bin/javac" ]; then
    JAVAC_CMD="/usr/local/opt/openjdk/bin/javac"
    JAVA_CMD="/usr/local/opt/openjdk/bin/java"
elif [ -f "/opt/homebrew/opt/openjdk/bin/javac" ]; then
    JAVAC_CMD="/opt/homebrew/opt/openjdk/bin/javac"
    JAVA_CMD="/opt/homebrew/opt/openjdk/bin/java"
else
    JAVAC_CMD="$(find /usr/local /opt/homebrew /Library/Java -name javac 2>/dev/null | head -n 1)"
    JAVA_CMD="$(find /usr/local /opt/homebrew /Library/Java -name java 2>/dev/null | head -n 1)"
fi

echo "Using javac: $JAVAC_CMD"
echo "Using java:  $JAVA_CMD"

mkdir -p bin

echo "Compiling Java sources..."
$JAVAC_CMD -d bin $(find src -name "*.java")

echo "Compilation successful!"

if [ "$1" == "stack" ]; then
    echo "Running Stack App..."
    $JAVA_CMD -cp bin stack.FrmStack
elif [ "$1" == "sort" ]; then
    echo "Running Sort App..."
    $JAVA_CMD -cp bin sort.FrmSort
elif [ "$1" == "drawing" ]; then
    echo "Running Drawing App..."
    $JAVA_CMD -cp bin drawing.FrmDrawing
else
    echo "Running Main Launcher App..."
    $JAVA_CMD -cp bin MainLauncher
fi
